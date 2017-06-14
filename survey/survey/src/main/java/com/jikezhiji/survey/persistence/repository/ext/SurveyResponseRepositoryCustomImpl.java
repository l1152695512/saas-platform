package com.jikezhiji.survey.persistence.repository.ext;

import com.jikezhiji.survey.domain.SurveyAccessToken;
import com.jikezhiji.survey.domain.SurveyResponse;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by liusizuo on 2017/6/10.
 */
@Repository
@Transactional
public class SurveyResponseRepositoryCustomImpl implements SurveyResponseRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public SurveyResponse save(SurveyResponse entity) {
        if(!StringUtils.isEmpty(entity.getAccessToken())) {
            SurveyAccessToken token = em.find(SurveyAccessToken.class,entity.getAccessToken());
            token.setAvailableLimit(token.getAvailableLimit() - 1);
            if(token.getAvailableLimit() == 0) {
                token.setCompleted(true);
            }
            em.merge(token);
        }
        if(entity.isNew()) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        return entity;
    }
}
