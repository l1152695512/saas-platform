package com.jikezhiji.survey.processor;

import com.jikezhiji.survey.domain.Survey;
import com.jikezhiji.survey.domain.SurveySetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liusizuo on 2017/5/27.
 */
@Component
public class SurveySettingResourceProcessor implements ResourceProcessor<Resource<SurveySetting>> {

    private RepositoryEntityLinks rel;

    @Autowired
    public SurveySettingResourceProcessor(RepositoryEntityLinks rel) {
        this.rel = rel;
    }

    @Override
    public Resource<SurveySetting> process(Resource<SurveySetting> resource) {
//        Long id = resource.getContent().getSurveyId();
//        List<Link> links = resource.getLinks().stream()
//                .map(link -> rel.linkForSingleResource(Survey.class,id).slash("setting").withRel(link.getRel()))
//                .collect(Collectors.toList());
//        return new Resource<>(resource.getContent(),links);
        return resource;
    }
}
