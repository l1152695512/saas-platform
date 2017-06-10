package com.jikezhiji.survey.persistence.repository.ext;

import com.jikezhiji.survey.domain.Survey;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

/**
 * Created by liusizuo on 2017/5/27.
 */
@Repository
@Transactional
public class SurveyRepositoryCustomImpl implements SurveyRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional(readOnly = true)
    public Survey findOne(Long surveyId) {
        CriteriaQuery<Survey> query = em.getCriteriaBuilder().createQuery(Survey.class);
        Root<Survey> root = query.from(Survey.class);
        root.fetch("setting", JoinType.LEFT);
        query.where(em.getCriteriaBuilder().equal(root.get("id"),surveyId));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    public Survey save(Survey entity) {
        if(entity.getQuestions() == null) {
            entity.setQuestionCount(entity.getQuestions().size());
        } else {
            entity.setQuestionCount(entity.getQuestions().size());
        }
        if(entity.isNew()) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        return entity;
    }


}
