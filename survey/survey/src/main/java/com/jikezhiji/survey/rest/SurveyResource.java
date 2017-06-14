package com.jikezhiji.survey.rest;

import com.jikezhiji.survey.domain.*;
import com.jikezhiji.survey.domain.embedded.AccessRule;
import com.jikezhiji.survey.domain.embedded.QuestionType;
import com.jikezhiji.survey.domain.embedded.SurveyFormat;
import com.jikezhiji.survey.persistence.repository.QuotaRepository;
import com.jikezhiji.survey.persistence.repository.SurveyAccessTokenRepository;
import com.jikezhiji.survey.persistence.repository.SurveyRepository;
import com.jikezhiji.survey.persistence.repository.SurveyResponseRepository;
import com.jikezhiji.survey.rest.value.Answer;
import com.jikezhiji.survey.rest.value.SurveyEndHint;
import com.jikezhiji.survey.security.UserIdPrincipal;
import com.jikezhiji.survey.util.Lists;
import com.jikezhiji.survey.util.Requests;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    private SurveyAccessTokenRepository tokenRepository;

    @Autowired
    public SurveyResource(SurveyRepository repository,SurveyResponseRepository responseRepository,
                          QuotaRepository quotaRepository,ProjectionFactory projectionFactory,
                          SurveyAccessTokenRepository tokenRepository) {
        this.repository = repository;
        this.responseRepository = responseRepository;
        this.quotaRepository = quotaRepository;
        this.projectionFactory = projectionFactory;
        this.tokenRepository = tokenRepository;
    }


    /**
     * 新建一份答卷
     * @param surveyId
     * @return
     */
    @PostMapping(value = "/surveys/{surveyId}/responses")
    public Resource<SurveyResponse> addResponse(@PathVariable("surveyId")Long surveyId, Principal principal, HttpServletRequest request) {
        Survey survey = validateSurvey(repository.findOne(surveyId));
        SurveySetting setting = survey.getSetting();

        long responseCount = responseRepository.count(Example.of(new SurveyResponse(surveyId,true)));
        if(setting.getResponseLimit() <= responseCount) {
            throw new IllegalStateException("问卷的参与份数已经足够");
        }

        SurveyResponse newResponse = new SurveyResponse();
        newResponse.setUserId(principal.getName());
        newResponse.setSubmitted(false);
        newResponse.setSurveyId(surveyId);
        newResponse.setServiceId(survey.getServiceId());
        newResponse.setStartTime(new Date());
        newResponse.setDeviceId(request.getHeader("deviceId"));
        newResponse.setIpAddress(Requests.getIpAddr(request));

        if(setting.getAccessRule() == AccessRule.ONCE_PER_USER) {
            if(principal instanceof UserIdPrincipal) {
                UserIdPrincipal up = (UserIdPrincipal) principal;
                if(up.getServiceId().equals(survey.getServiceId())) {
                    throw new AccessDeniedException("你没有权限回答当前问卷，请更换账号");
                }
                SurveyResponse response = new SurveyResponse(surveyId);
                response.setUserId(principal.getName());
                SurveyResponse unsubmittedResponse = responseRepository.findOne(Example.of(response));
                if(unsubmittedResponse != null ) {
                    if(unsubmittedResponse.isSubmitted()) {
                        throw new AccessDeniedException("当前问卷每个用户限填一次");
                    } else {
                        newResponse = unsubmittedResponse;
                    }
                }
            } else {
                throw new AccessDeniedException("当前问卷只允许特定用户回答，请先登录");
            }
        } else if(setting.getAccessRule() == AccessRule.ONCE_PER_IP) {
            SurveyResponse response = new SurveyResponse(surveyId);
            response.setIpAddress(newResponse.getIpAddress());
            SurveyResponse unsubmittedResponse = responseRepository.findOne(Example.of(response));
            if(unsubmittedResponse != null ) {
                if(unsubmittedResponse.isSubmitted()) {
                    throw new AccessDeniedException("当前问卷每个IP限填一次");
                } else {
                    newResponse = unsubmittedResponse;
                }
            }
        } else if(setting.getAccessRule() == AccessRule.ONCE_PER_DEVICE) {
            SurveyResponse response = new SurveyResponse(surveyId);
            response.setDeviceId(newResponse.getDeviceId());
            SurveyResponse unsubmittedResponse = responseRepository.findOne(Example.of(response));
            if(unsubmittedResponse != null ) {
                if(unsubmittedResponse.isSubmitted()) {
                    throw new AccessDeniedException("当前问卷每个IP限填一次");
                } else {
                    newResponse = unsubmittedResponse;
                }
            }
        } else if(setting.getAccessRule() == AccessRule.TOKEN){
            SurveyAccessToken token = tokenRepository.findOne(request.getParameter("token"));
            if(token == null || !token.getSurveyId().equals(surveyId) || token.isEffective()) {
                throw new AccessDeniedException("您使用的token无效");
            }
            SurveyResponse response = new SurveyResponse(surveyId);
            response.setSubmitted(false);
            response.setUserId(principal.getName());

            SurveyResponse unsubmittedResponse = responseRepository.findOne(Example.of(response));
            if(unsubmittedResponse != null) {
                newResponse = unsubmittedResponse;
            }
            newResponse.setAccessToken(token.getTokenId());
        } else {
            SurveyResponse response = new SurveyResponse(surveyId);
            response.setSubmitted(false);
            response.setUserId(principal.getName());

            SurveyResponse unsubmittedResponse = responseRepository.findOne(Example.of(response));
            if(unsubmittedResponse != null) {
                newResponse = unsubmittedResponse;
            }
        }
        responseRepository.save(newResponse);
        Link link = linkTo(methodOn(SurveyResource.class).addResponse(surveyId,principal,request)).withRel("response");
        return new Resource<>(newResponse,link);
    }


    /**
     * 获取某份答卷待回答的下一批问题
     * @return
     */
    @GetMapping(value = "/surveys/{surveyId}/responses/{responseId}")
    public Resources<Question> getResponseNextUnfilledQuestions(@PathVariable("surveyId")Long surveyId,
                                                                         @PathVariable("responseId")Long responseId,
                                                                         Principal principal){
        SurveyResponse response = validateResponse(surveyId,responseId,principal);

        /**
         * 这里本来三行可以简化为一行的，但是写在一起的话在意图表达方面又不够直观。
         */
        Survey survey = repository.findOne(surveyId);
        validateSurvey(survey);
        SurveySetting setting = survey.getSetting();
        List<Question> questions = survey.getQuestions();
        switch (setting.getFormat()) {
            case QUESTION_BY_QUESTION:
                Question question = survey.getQuestion(response.getLastQuestionId());
                Long targetQuestionId = question.runJumpLogic(response.lastItem().getCode(),survey.getNextQuestionByIndex(response.getLastQuestionId()));
                questions = Collections.singletonList(survey.getQuestion(targetQuestionId));
                break;
            case GROUP_BY_GROUP:
                questions = getGroupQuestions(questions,response.getLastQuestionId());
                break;
            case ALL_IN_ONE:
                break;
        }
        Link link = linkTo(methodOn(SurveyResource.class).getResponseNextUnfilledQuestions(surveyId,responseId,principal)).withRel("unfilled_questions");
        return new Resources<>(questions, link);
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
                                          @RequestParam("interviewSecs") Long interviewSecs,
                                          @RequestParam("code") String code, Principal principal) {

        SurveyResponse sr = validateResponse(surveyId,responseId,principal);
        Survey survey = validateSurvey(repository.findOne(surveyId));
        Question question = survey.getQuestion(questionId);
        if(question == null) {
            throw new ResourceNotFoundException("问卷的问题不存在");
        }
        question.validateAnswer(code);

        ResponseItem item = sr.getItem(questionId);
        if(item != null) {
            //如果之前已经填写过这个问题的答案，验证重新编辑答案逻辑
            validateReeditAnswer(sr,survey,questionId);
        } else{
            item = new ResponseItem();
        }
        item.setCode(code);
        item.setInterviewTime(interviewSecs); //这里计算可能会有问题
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
     * @param surveyId 问卷ID
     * @param responseId 答卷ID
     * @param answers 问题和答案
     * @param principal 用户标识
     * @return
     */
    @PostMapping(value = "/surveys/{surveyId}/responses/{responseId}/items")
    public ResponseEntity<?> addResponseItems(@PathVariable("surveyId")Long surveyId,
                                             @PathVariable("responseId")Long responseId,
                                             @RequestBody List<Answer> answers,
                                              Principal principal) {
        SurveyResponse sr = validateResponse(surveyId,responseId,principal);
        Survey survey = validateSurvey(repository.findOne(surveyId));
        answers.forEach(answer->{
            Question question = survey.getQuestion(answer.getQuestionId());
            if(question == null) {
                throw new ResourceNotFoundException("问卷的问题不存在");
            }
            question.validateAnswer(answer.getValue());//验证答案本身
            ResponseItem item = sr.getItem(question.getId());
            if(item != null) {
                //如果之前已经填写过这个问题的答案，验证重新编辑答案逻辑
                validateReeditAnswer(sr,survey,question.getId());
            } else{
                item = new ResponseItem();
            }
            item.setCode(answer.getValue());
            item.setInterviewTime(answer.getInterviewSecs());
            item.setSubmitTime(new Date());
            item.setQuestionId(question.getId());
            item.setResponseId(responseId);
            sr.addItems(item);
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
        responseRepository.save(sr);
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

    private void validateReeditAnswer(SurveyResponse sr, Survey survey, Long questionId) {
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
}
