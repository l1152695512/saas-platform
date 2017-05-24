package com.jikezhiji.survey.domain.service.impl;

import com.jikezhiji.survey.domain.Question;
import com.jikezhiji.survey.domain.Survey;
import com.jikezhiji.survey.domain.SurveySetting;
import com.jikezhiji.survey.domain.repository.QuestionRepository;
import com.jikezhiji.survey.domain.repository.SurveyRepository;
import com.jikezhiji.survey.domain.repository.SurveySettingRepository;
import com.jikezhiji.survey.domain.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by E355 on 2017/1/1.
 */
@Service
@Transactional
public class SurveyServiceImpl implements SurveyService{
    @Autowired
    private SurveyRepository repository;

    @Autowired
    private SurveySettingRepository settingRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Survey createSurvey(Survey survey){
        return repository.save(survey);
    }

    public Survey updateSurvey(Survey survey) {
        Survey old = repository.findOne(survey.getId());
        if(!old.isActive()){
            old = repository.save(survey);
        }
        return old;
    }

    public Survey activeSurvey(Long surveyId){
        Survey old = repository.findOne(surveyId);
        if(!old.isActive()){
            old.setActive(true);
            old.setStartTime(new Date());
        }
        return old;
    }

    public Survey closeSurvey(Long surveyId) {
        Survey old = repository.findOne(surveyId);
        if(old.isActive()) {
            old.setExpiryTime(new Date());
        }
        return old;
    }

    @Override
    public Survey settingSurvey(SurveySetting setting) {
        Survey old = repository.findOne(setting.getSurveyId());
        if(!old.isActive()) {
            old.setSetting(setting);
            settingRepository.delete(setting.getSurveyId());
            settingRepository.save(setting);
        }
        return old;
    }

    @Override
    public Question addQuestion(Question question) {
        Survey old = repository.findOne(question.getSurveyId());
        if(!old.isActive()) {
            questionRepository.save(question);
            old.setQuestionAmount(old.getQuestionAmount() + 1);
        }
        return question;
    }

    @Override
    public Question updateQuestion(Question question) {
        Survey old = repository.findOne(question.getSurveyId());
        if(!old.isActive()) {
            questionRepository.save(question);
        }
        return question;
    }

    @Override
    public Question adjustQuestionOrder(Long questionId, int sequence) {
        Question question = questionRepository.findOne(questionId);
        if(question.getSequence() < sequence){
            Example<Question> questionExample = Example.of(new Question(),
                    ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.DEFAULT));
            questionRepository.findAll(questionExample);

        }
        return null;
    }

    @Override
    public void deleteQuestion(Long questionId) {
        Question question = questionRepository.findOne(questionId);
        Survey old = repository.findOne(question.getSurveyId());
        if(!old.isActive()) {
            questionRepository.delete(question);
        }
    }
}
