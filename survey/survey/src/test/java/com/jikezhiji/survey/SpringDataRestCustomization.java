package com.jikezhiji.survey;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Created by E355 on 2017/1/1.
 */
public class SpringDataRestCustomization extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

//        config.withCustomEntityLookup().
//                forRepository(SurveyRepository.class, Survey::getTitle, SurveyRepository::findByTitle);
    }
}
