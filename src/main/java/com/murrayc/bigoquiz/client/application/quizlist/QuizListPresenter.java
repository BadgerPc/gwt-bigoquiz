package com.murrayc.bigoquiz.client.application.quizlist;


import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.murrayc.bigoquiz.client.Log;
import com.murrayc.bigoquiz.client.NameTokens;
import com.murrayc.bigoquiz.client.application.ApplicationPresenter;
import com.murrayc.bigoquiz.client.application.ContentView;
import com.murrayc.bigoquiz.client.application.PlaceUtils;
import com.murrayc.bigoquiz.client.application.quiz.BigOQuizPresenter;
import com.murrayc.bigoquiz.client.application.quiz.QuizClient;
import com.murrayc.bigoquiz.shared.Quiz;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by murrayc on 1/21/16.
 */
public class QuizListPresenter extends BigOQuizPresenter<QuizListPresenter.MyView, QuizListPresenter.MyProxy>
        implements QuizListUserEditUiHandlers {
    private final PlaceManager placeManager;

    interface MyView extends ContentView, HasUiHandlers<QuizListUserEditUiHandlers> {
        void setQuizList(final List<Quiz> quizList);

        void setServerFailed();
    }

    @ProxyStandard
    @NameToken(NameTokens.QUIZ_LIST)
    interface MyProxy extends ProxyPlace<QuizListPresenter> {
    }

    @Inject
    QuizListPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_CONTENT);

        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(@NotNull final PlaceRequest request) {
        super.prepareFromRequest(request);

        getAndUseQuizList();
    }

    private void getAndUseQuizList() {
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
        QuizClient client = GWT.create(QuizClient.class);

        @NotNull final MethodCallback<List<Quiz>> callback = new MethodCallback<List<Quiz>>() {
            @Override
            public void onFailure(final Method method, @NotNull final Throwable caught) {
                getView().setLoadingLabelVisible(false);

                try {
                    throw caught;
                } catch (final IllegalArgumentException ex) {
                    //One of the parameters (quizID, questionId, etc) must be invalid,
                    //TODO: Handle this properly.
                    Log.error("AsyncCallback Failed with IllegalArgumentException: getQuizList()", ex);
                    getView().setServerFailed();
                } catch (final Throwable ex) {
                    Log.error("AsyncCallback Failed: getQuizList()", ex);
                    getView().setServerFailed();
                }
            }

            @Override
            public void onSuccess(final Method method, final List<Quiz> result) {
                getView().setLoadingLabelVisible(false);

                getView().setQuizList(result);
            }
        };

        getView().setLoadingLabelVisible(true);
        client.getQuiz(true /* listOnly */, callback);
    }

    public void onAnswerQuestions(final String quizId) {
        @NotNull final PlaceRequest placeRequest = PlaceUtils.getPlaceRequestForQuizQuestion(quizId);
        placeManager.revealPlace(placeRequest);
    }

    public void onHistory(final String quizId) {
        @NotNull final PlaceRequest placeRequest = PlaceUtils.getPlaceRequestForQuizHistory(quizId);
        placeManager.revealPlace(placeRequest);
    }
}