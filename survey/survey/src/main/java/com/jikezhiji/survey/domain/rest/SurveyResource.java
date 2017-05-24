package com.jikezhiji.survey.domain.rest;

import com.jikezhiji.survey.domain.Survey;
import com.jikezhiji.survey.domain.SurveySetting;
import com.jikezhiji.survey.domain.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by E355 on 2017/1/1.
 */
@RepositoryRestController
@RequestMapping("surveys")
public class SurveyResource {

    private SurveyRepository repository;

    @Autowired
    public SurveyResource(SurveyRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Survey createSurvey(Survey survey){
        return repository.save(survey);
    }


    @RequestMapping(value = "{surveyId}", method = RequestMethod.PATCH)
    public Survey updateSurveySetting(@PathVariable("surveyId")Long surveyId, SurveySetting setting) {
        setting.setSurveyId(surveyId);
        return null;
    }

}
