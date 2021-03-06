package com.murrayc.bigoquiz.client.application.quizlist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.murrayc.bigoquiz.client.BigOQuizMessages;
import com.murrayc.bigoquiz.client.Log;
import com.murrayc.bigoquiz.client.application.ContentViewWithUIHandlers;
import com.murrayc.bigoquiz.client.application.PlaceUtils;
import com.murrayc.bigoquiz.shared.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by murrayc on 1/21/16.
 */
public class QuizListView extends ContentViewWithUIHandlers<QuizListUserEditUiHandlers>
        implements QuizListPresenter.MyView {
    private final BigOQuizMessages messages = GWT.create(BigOQuizMessages.class);

    private final Panel panelList = new FlowPanel();
    private final PlaceManager placeManager;

    @Inject
    QuizListView(PlaceManager placeManager) {
        this.placeManager = placeManager;

        setTitle(constants.quizzesTitle());

        mainPanel.add(panelList);
        panelList.addStyleName("quiz-list-panel");
    }

    @Override
    public void setQuizList(@NotNull final List<Quiz> quizList) {
        setErrorLabelVisible(false);

        panelList.clear();

        if (quizList == null) {
            Log.error("setQuizList(): quizList is null.");
            return;
        }

        for(final Quiz quiz : quizList) {
            final Quiz.QuizDetails details = quiz.getDetails();
            if (details == null) {
                Log.error("QuizListView: details is null.");
                continue;
            }

            if (details.getIsPrivate()) {
                continue;
            }

            final Panel rowPanel = new FlowPanel();
            rowPanel.addStyleName("quiz-list-row");
            rowPanel.addStyleName("clearfix");
            panelList.add(rowPanel);

            @NotNull final PlaceRequest placeRequest = PlaceUtils.getPlaceRequestForQuiz(details.getId());
            final String historyToken = placeManager.buildHistoryToken(placeRequest);
            @NotNull final Hyperlink link = new InlineHyperlink(details.getTitle(), historyToken);
            rowPanel.add(link);

            final Panel buttonsPanel = new FlowPanel();
            rowPanel.add(buttonsPanel);
            buttonsPanel.addStyleName("quiz-list-row-buttons-panel");


            final Button buttonPlay = new Button(constants.buttonAnswerQuestions());
            buttonPlay.addStyleName("button-answer-questions");
            buttonPlay.addClickHandler(event -> getUiHandlers().onAnswerQuestions(details.getId()));
            buttonsPanel.add(buttonPlay);

            final Button buttonHistory = new Button(constants.buttonHistory());
            buttonHistory.addStyleName("button-history");
            buttonHistory.addClickHandler(event -> getUiHandlers().onHistory(details.getId()));
            buttonsPanel.add(buttonHistory);
        }
    }
}
