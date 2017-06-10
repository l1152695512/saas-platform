package com.jikezhiji.survey.rest;

import com.jikezhiji.survey.domain.*;
import com.jikezhiji.survey.domain.embedded.QuestionType;
import com.jikezhiji.survey.domain.embedded.SurveyFormat;
import com.jikezhiji.survey.persistence.repository.QuotaRepository;
import com.jikezhiji.survey.persistence.repository.SurveyRepository;
import com.jikezhiji.survey.persistence.repository.SurveyResponseRepository;
import com.jikezhiji.survey.rest.value.QuestionAndAnswer;
import com.jikezhiji.survey.rest.value.SurveyEndHint;
import com.jikezhiji.survey.util.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by E355 on 2017/1/1.
 */
@RepositoryRestController
public class SurveyResource {

    private SurveyRepository repository;
    private SurveyResponseRepository responseRepository;
    private QuotaRepository quotaRepository;
    private ProjectionFactory projectionFactory;

    @Autowired
    public SurveyResource(SurveyRepository repository,SurveyResponseRepository responseRepository,
                          QuotaRepository quotaRepository,ProjectionFactory projectionFactory) {
        this.repository = repository;
        this.responseRepository = responseRepository;
        this.quotaRepository = quotaRepository;
        this.projectionFactory = projectionFactory;
    }


    /**
     * 新建一份答卷
     * @param surveyId
     * @return
     */
    @PostMapping(value = "/surveys/{surveyId}/responses")
    public Resource<Survey> addResponse(@PathVariable("surveyId")Long surveyId) {
        Survey survey = validateSurvey(repository.findOne(surveyId));
        SurveySetting setting = survey.getSetting();
        long responseCount = responseRepository.count(Example.of(new SurveyResponse(surveyId)));
        if(setting.getResponseLimit() <= responseCount) {
            throw new IllegalStateException("问卷的参与份数已经足够");
        }
        switch (setting.getAccessRule()){
            case ONCE_PER_USER:
                break;
            case ONCE_PER_IP:
                break;
            case ONCE_PER_DEVICE:
                break;
            case TOKEN:
                break;
        }
        return null;
    }

    private Survey validateSurvey(Survey survey){
        //1.验证状态和是否处于可以回答的时间区间
        if(!survey.isActive()) {
            throw new IllegalStateException("问卷还未激活");
        }
        if(survey.getStartTime().getTime() > System.currentTimeMillis() // 开始时间大于当前时间 说明问卷还没有开始
                || System.currentTimeMillis() > survey.getExpiryTime().getTime()) { //当前时间大于过期时间 说明问卷已经过期
            throw new IllegalStateException("问卷不在可回答的时间区间内");
        }
        return survey;
    }

    /**
     * 获取某份答卷待回答的下一批问题
     * @return
     */
    @RequestMapping(value = "/surveys/{surveyId}/responses/{responseId}", method = RequestMethod.GET)
    public Resources<QuestionAndAnswer> getResponseNextUnfilledQuestions(@PathVariable("surveyId")Long surveyId,
                                                                         @PathVariable("responseId")Long responseId,
                                                                         Principal principal){
        SurveyResponse response = validateResponse(surveyId,responseId,principal);

        /**
         * 这里本来三行可以简化为一行的，但是写在一起的话在意图表达方面又不够直观。
         */
        Survey survey = repository.findOne(surveyId);
        validateSurvey(survey);
        SurveySetting setting = survey.getSetting();
        List<QuestionAndAnswer> content = new ArrayList<>();
        List<Question> questions = survey.getQuestions();
        switch (setting.getFormat()) {
            case QUESTION_BY_QUESTION:
                Question question = survey.getQuestion(response.getLastQuestionId());
                Long targetQuestionId = question.runJumpLogic(response.lastItem().getCode(),survey.getNextQuestionByIndex(response.getLastQuestionId()));
                content = transform(Collections.singletonList(survey.getQuestion(targetQuestionId)),response);
                break;
            case GROUP_BY_GROUP:
                content = transform(getGroupQuestions(questions,response.getLastQuestionId()),response);
                break;
            case ALL_IN_ONE:
                content = transform(questions,response);
                break;
        }
        Link link = linkTo(methodOn(SurveyResource.class).getResponseNextUnfilledQuestions(surveyId,responseId,principal)).withRel("response");
        return new Resources<>(content, link);
    }

    private SurveyResponse validateResponse(Long surveyId,Long responseId,Principal principal){
        SurveyResponse response = responseRepository.findOne(responseId);
        if(! response.getSurveyId().equals(surveyId)) {
            throw new ResourceNotFoundException("答卷不存在");
        }
        if(! response.getUserId().equals(principal.getName())) {
            throw new AccessDeniedException("你不能访问别人的答卷");
        }
        return response;
    }

    private static List<QuestionAndAnswer> transform(List<Question> questions,SurveyResponse response){
        return questions.stream().map(question -> new QuestionAndAnswer(question, response.getItem(question.getId())))
                .collect(Collectors.toList());
    }

    static List<Question> getGroupQuestions(List<Question> questions,Long questionId){
        int index = questions.indexOf(new Question(questionId));
        if(index == questions.size() - 1) {
            return Collections.singletonList(questions.get(index));
        }

        List<Question>[] parts = Lists.split(questions,index);
        List<Question> part1 = parts[0];
        List<Question> part2 = parts[1];
        int start = 0;
        int end = questions.size();
        for (int i = part1.size()-1; i >=0; i--) {
            if(part1.get(i).getType() == QuestionType.SECTION) {
                start = i + 1;
                break;
            }
        }

        for (int i = 0; i < part2.size(); i++) {
            if(part2.get(i).getType() == QuestionType.SECTION) {
                end = part1.size() + i + 1;
                break;
            }
        }
        if(start == 0 && end == questions.size()) {
            return questions;
        } else {
            return questions.subList(start,end);
        }
    }


    /**
     * 添加一个答案
     * @param surveyId 问卷id
     * @param responseId 答卷id
     * @param principal 用户的身份
     * @param questionId 问题id
     * @param code 答案
     * @return
     */
    @PostMapping(value = "/surveys/{surveyId}/responses/{responseId}/items/{questionId}")
    public ResponseEntity<?> addResponseItem(@PathVariable("surveyId")Long surveyId,
                                          @PathVariable("responseId")Long responseId,
                                          @PathVariable("questionId")Long questionId,
                                          @RequestParam("code") String code, Principal principal) {

        SurveyResponse sr = validateResponse(surveyId,responseId,principal);
        Survey survey = validateSurvey(repository.findOne(surveyId));
        Question question = survey.getQuestion(questionId);
        if(question == null) {
            throw new ResourceNotFoundException("问卷的问题不存在");
        }
        question.validateAnswer(code);

        //检查是否允许完成答题后修改
        ResponseItem item = sr.getItem(questionId);
        if(item != null) {
            if(sr.isSubmitted() ) {
                if(!survey.getSetting().isAllowEditAfterCompletion()) {
                    throw new IllegalStateException("禁止修改已经完成了答卷的答案！");
                }
            } else if(! survey.getSetting().isAllowPrev() ){
                if (survey.getSetting().getFormat() == SurveyFormat.QUESTION_BY_QUESTION ||
                        //如果当前提交的问题和最后答题的问题不属于同一组问题
                        survey.getSetting().getFormat() == SurveyFormat.GROUP_BY_GROUP &&
                        getGroupQuestions(survey.getQuestions(),sr.getLastQuestionId()).stream().noneMatch(q->q.getId().equals(questionId)))
                throw new IllegalStateException("禁止回到上一步修改已经提交的答案");
            }
        } else{
            item = new ResponseItem();
        }
        item.setCode(code);
        item.setInterviewTime(0L); //这里计算可能会有问题
        item.setSubmitTime(new Date());
        item.setQuestionId(questionId);
        item.setResponseId(responseId);
        sr.addItems(item);

        List<Quota> quotas = quotaRepository.findAll(Example.of(new Quota(surveyId)));
        List<SurveyResponse> responses = responseRepository.findAll(Example.of(new SurveyResponse(surveyId)));

        quotas.stream().filter(Quota::isActive).forEach(quota -> {
            long total = quota.getMembers().stream().map(mq ->
                    responses.stream().filter(response-> response.getItems().stream().anyMatch(i ->
                            i.getQuestionId().equals(mq.getQuestionId()) && i.getCode().equals(mq.getCode()))).count())
                    .reduce((sum,num)->sum + num).orElse(0l);
            if(quota.getQuantity() <= total) {
                throw new IllegalStateException(quota.getMessage());
            }
        });


        responseRepository.save(sr);
        return ResponseEntity.ok().build();
    }

    /**
     * 批量添加答案
     * @param surveyId
     * @param responseId
     * @param answers
     * @param principal
     * @return
     */
    @PostMapping(value = "/surveys/{surveyId}/responses/{responseId}/items")
    public ResponseEntity<?> addResponseItems(@PathVariable("surveyId")Long surveyId,
                                             @PathVariable("responseId")Long responseId,
                                             @RequestBody Map<String,String> answers,
                                              Principal principal) {
        SurveyResponse sr = validateResponse(surveyId,responseId,principal);
        Survey survey = validateSurvey(repository.findOne(surveyId));
        answers.forEach((key,value)->{
            Question question = survey.getQuestion(Long.parseLong(key));
            if(question == null) {
                throw new ResourceNotFoundException("问卷的问题不存在");
            }
            question.validateAnswer(value);

        });
        List<Quota> quotas = quotaRepository.findAll(Example.of(new Quota(surveyId)));
        List<SurveyResponse> responses = responseRepository.findAll(Example.of(new SurveyResponse(surveyId)));

        quotas.stream().filter(Quota::isActive).forEach(quota -> {
            long total = quota.getMembers().stream().map(mq ->
                    responses.stream().filter(response-> response.getItems().stream().anyMatch(item ->
                            item.getQuestionId().equals(mq.getQuestionId()) && item.getCode().equals(mq.getCode()))).count())
                    .reduce((sum,num)->sum + num).orElse(0l);
            if(quota.getQuantity() <= total) {
                throw new IllegalStateException(quota.getMessage());
            }
        });
        return ResponseEntity.ok().build();
    }


    /**
     * 提交答卷
     * @param surveyId 问卷id
     * @param responseId 答卷id
     * @param principal 身份信息
     * @return
     */
    @PutMapping(value = "/surveys/{surveyId}/responses/{responseId}")
    public Resource<SurveyEndHint> submitResponse(@PathVariable("surveyId")Long surveyId,
                                           @PathVariable("responseId")Long responseId,
                                           Principal principal) {
        SurveyResponse response = validateResponse(surveyId,responseId,principal);

        Survey survey = validateSurvey(repository.findOne(surveyId));
        //检查必答问题
        survey.getQuestions().stream().filter(Question::isMandatory).forEach(q->{
            ResponseItem item = response.getItem(q.getId());
            if(item == null || StringUtils.isEmpty(item.getCode())){
                if(!StringUtils.isEmpty(q.getDefaultAnswer())) {
                    if(item == null) {
                        item = new ResponseItem();
                        item.setResponseId(responseId);
                        item.setInterviewTime(0L);
                        item.setSubmitTime(new Date());
                        item.setQuestionId(q.getId());
                    }
                    item.setCode(q.getDefaultAnswer());
                    responseRepository.findOne(responseId).addItems(item);
                } else {
                    throw new IllegalStateException("必须填写答案的问题没有回答");
                }
            }
        });
        //检查必答问题
        response.setSubmitted(true);
        response.setSubmitTime(new Date());
        response.setInterviewTime((response.getStartTime().getTime() - response.getSubmitTime().getTime()) / 1000);
        responseRepository.save(response);

        Link link = linkTo(methodOn(SurveyResource.class)
                .submitResponse(surveyId,responseId,principal)).withRel("survey");
        SurveyEndHint hint = projectionFactory.createProjection(SurveyEndHint.class,survey);
        return new Resource<>(hint, link);
    }
}
