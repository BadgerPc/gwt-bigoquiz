package com.murrayc.bigoquiz.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.murrayc.bigoquiz.shared.Question;
import com.murrayc.bigoquiz.shared.db.UserQuestionHistory;
import com.murrayc.bigoquiz.shared.db.UserStats;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by murrayc on 7/20/17.
 */
public class UserStatsTest {
    @Test
    public void jsonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Extra checks:
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, true);
        //objectMapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);

        UserStats objToWrite = new UserStats();

        // Marked with @JsonIgnore:
        // final String USER_ID = "useridfoo";
        // objToWrite.setUserId(USER_ID);

        final String QUIZ_ID = "quizfoobar";
        objToWrite.setQuizId(QUIZ_ID);

        final String SECTION_ID = "sectionidfoo";
        objToWrite.setSectionId(SECTION_ID);

        final int ANSWERED = 123;
        objToWrite.setAnswered(ANSWERED);

        final int ANSWERED_ONCE = 234;
        objToWrite.setAnsweredOnce(ANSWERED_ONCE);

        final int CORRECT = 345;
        objToWrite.setCorrect(CORRECT);

        final int CORRECT_ONCE = 345;
        objToWrite.setCorrectOnce(CORRECT_ONCE);

        final UserQuestionHistory QUESTION_HISTORY_1 = new UserQuestionHistory(
                new Question("questionid1", "sectionid", "subsectionid", new Question.Text("text", false), null, null, null, null, null));
        QUESTION_HISTORY_1.setCountAnsweredWrong(4); // So it is one of the problem questions.
        final UserQuestionHistory QUESTION_HISTORY_2 = new UserQuestionHistory(
                new Question("questionid2", "sectionid", "subsectionid", new Question.Text("text", false), null, null, null, null, null));
        QUESTION_HISTORY_2.setCountAnsweredWrong(3); // So it is one of the problem questions.

        final Map<String, UserQuestionHistory> QUESTION_HISTORIES = new HashMap<>();
        QUESTION_HISTORIES.put(QUESTION_HISTORY_1.getQuestionId(), QUESTION_HISTORY_1);
        QUESTION_HISTORIES.put(QUESTION_HISTORY_2.getQuestionId(), QUESTION_HISTORY_2);
        objToWrite.setQuestionHistories(QUESTION_HISTORIES);

        final int PROBLEM_QUESTIONS_HISTORIES_COUNT = QUESTION_HISTORIES.size();
        objToWrite.setProblemQuestionHistoriesCount(PROBLEM_QUESTIONS_HISTORIES_COUNT);

        final List<UserQuestionHistory> TOP_PROBLEM_QUESTION_HISTORIES = new ArrayList<>();
        TOP_PROBLEM_QUESTION_HISTORIES.add(QUESTION_HISTORY_1);
        TOP_PROBLEM_QUESTION_HISTORIES.add(QUESTION_HISTORY_2);
        objToWrite.setTopProblemQuestionHistories(TOP_PROBLEM_QUESTION_HISTORIES);

        // Create JSON from the object:
        final String json = objectMapper.writeValueAsString(objToWrite);
        assertNotNull(json);
        assertFalse(json.isEmpty());

        // Create an object from the JSON:
        UserStats obj = objectMapper.readValue(json, UserStats.class);
        assertNotNull(obj);

        // Marked with @JsonIgnore: assertEquals(USER_ID, obj.getUserId());
        assertEquals(QUIZ_ID, obj.getQuizId());
        assertEquals(SECTION_ID, obj.getSectionId());
        assertEquals(ANSWERED, obj.getAnswered());
        assertEquals(ANSWERED_ONCE, obj.getAnsweredOnce());
        assertEquals(CORRECT, obj.getCorrect());
        assertEquals(CORRECT_ONCE, obj.getCorrectOnce());
        assertEquals(PROBLEM_QUESTIONS_HISTORIES_COUNT, obj.getProblemQuestionHistoriesCount());

        final List<UserQuestionHistory> objTopProblems = obj.getTopProblemQuestionHistories();
        assertNotNull(objTopProblems);
        assertEquals(TOP_PROBLEM_QUESTION_HISTORIES.size(), objTopProblems.size());
    }
}
