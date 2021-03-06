package com.murrayc.bigoquiz.client.application;

import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.murrayc.bigoquiz.client.NameTokens;
import com.murrayc.bigoquiz.shared.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by murrayc on 1/24/16.
 */
public class PlaceUtils {
    public static PlaceRequest getPlaceRequestForQuiz(final String quizId) {

        @NotNull PlaceRequest.Builder builder = new PlaceRequest.Builder()
                .nameToken(NameTokens.QUIZ);

        if (!StringUtils.isEmpty(quizId)) {
            builder = builder.with(NameTokens.PARAM_QUIZ_ID, quizId);
        }

        return builder.build();
    }

    public static PlaceRequest getPlaceRequestForQuizQuestion(final String quizId) {

        @NotNull PlaceRequest.Builder builder = new PlaceRequest.Builder()
                .nameToken(NameTokens.QUESTION);

        if (!StringUtils.isEmpty(quizId)) {
            builder = builder.with(NameTokens.PARAM_QUIZ_ID, quizId);
        }

        return builder.build();
    }

    public static PlaceRequest getPlaceRequestForQuizHistory(final String quizId) {

        @NotNull PlaceRequest.Builder builder = new PlaceRequest.Builder()
                .nameToken(NameTokens.USER_HISTORY);

        if (!StringUtils.isEmpty(quizId)) {
            builder = builder.with(NameTokens.PARAM_QUIZ_ID, quizId);
        }

        return builder.build();
    }

    public static PlaceRequest getPlaceRequestForQuestion(final String quizId, final String questionId, final String sectionId) {

        @NotNull PlaceRequest.Builder builder = new PlaceRequest.Builder()
                .nameToken(NameTokens.QUESTION);

        if (!StringUtils.isEmpty(quizId)) {
            builder = builder.with(NameTokens.PARAM_QUIZ_ID, quizId);
        }

        if (!StringUtils.isEmpty(questionId)) {
            builder = builder.with(NameTokens.QUESTION_PARAM_QUESTION_ID, questionId);
        }

        if (!StringUtils.isEmpty(sectionId)) {
            builder = builder.with(NameTokens.QUESTION_PARAM_NEXT_QUESTION_SECTION_ID, sectionId);
        }

        return builder.build();
    }

    /**
     * Get a PlaceRequest to show the next question from a particular section,
     * or from any section if no section is specified.
     *
     * @param nextQuestionSectionId
     * @return
     */
    public static PlaceRequest getPlaceRequestForSection(final String quizId, final String nextQuestionSectionId) {
        PlaceRequest.Builder builder = new PlaceRequest.Builder()
                .nameToken(NameTokens.QUESTION)
                .with(NameTokens.PARAM_QUIZ_ID, quizId)
                .with(NameTokens.QUESTION_PARAM_NEXT_QUESTION_SECTION_ID, nextQuestionSectionId);

        return builder.build();
    }
}
