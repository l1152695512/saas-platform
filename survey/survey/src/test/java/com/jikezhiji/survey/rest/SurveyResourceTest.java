package com.jikezhiji.survey.rest;

import com.jikezhiji.survey.domain.Question;
import com.jikezhiji.survey.domain.embedded.QuestionType;
import org.apache.commons.collections4.ListUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liusizuo on 2017/6/8.
 */
public class SurveyResourceTest {
    @Test
    public void testGetGroupQuestions(){
        long index = 1;
        List<Question> questions = Arrays.asList(
                new Question(index++, QuestionType.ADDRESS),
                new Question(index++, QuestionType.ADDRESS),
                new Question(index++, QuestionType.ADDRESS),
                new Question(index++, QuestionType.SECTION),
                new Question(index++, QuestionType.ADDRESS),
                new Question(index++, QuestionType.SECTION),
                new Question(index++, QuestionType.ADDRESS),//7
                new Question(index++, QuestionType.ADDRESS),
                new Question(index++, QuestionType.SECTION),
                new Question(index, QuestionType.ADDRESS));
        Assert.assertTrue(ListUtils.isEqualList( SurveyResource.getGroupQuestions(questions,10l),
                Lists.newArrayList(new Question(index, QuestionType.ADDRESS))));
        Assert.assertTrue(ListUtils.isEqualList( SurveyResource.getGroupQuestions(questions,1l),
                Lists.newArrayList(new Question(1L, QuestionType.ADDRESS),new Question(2L, QuestionType.ADDRESS),
                        new Question(3L, QuestionType.ADDRESS),new Question(4L, QuestionType.ADDRESS))));
    }
}
