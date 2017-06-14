package com.jikezhiji.survey.rest.value;

import com.jikezhiji.survey.domain.Survey;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by liusizuo on 2017/5/27.
 */
@Projection(name = "basic", types = { Survey.class })
public interface SurveyBasicInfo {
    String getServiceId();
    String getUserId();
    String getTitle();
    String getDescription();
    String getWelcomeText();
    String getterminationText();
    String getEndText();
    String getEndUrl();
    String getEndUrlDescription();
    boolean isActive();
    Date getStartTime();
    Date getExpiryTime();
    Date getCreateTime();
    Date getUpdateTime();
}
