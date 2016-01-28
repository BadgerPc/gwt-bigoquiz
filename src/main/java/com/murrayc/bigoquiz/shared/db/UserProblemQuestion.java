package com.murrayc.bigoquiz.shared.db;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.murrayc.bigoquiz.shared.Question;

/**
 * Created by murrayc on 1/21/16.
 */
@Entity
public class UserProblemQuestion implements IsSerializable {
    //TODO: Remove this?
    @Id
    private Long id;

    //TODO: I would rather use a Ref<UserProfile> here,
    //but that doesn't seem to GWT-compile for the client side.b
    @Index
    private String userId;

    @Index
    private String questionId;

    @Index
    private String sectionId;

    //TODO: Internationalization.
    @Ignore
    private String questionTitle;

    public int getCountAnsweredWrong() {
        return countAnsweredWrong;
    }

    //Decrements once for each time the user answers it correctly.
    //Increments once for each time the user answers it wrongly.
    @Index
    private int countAnsweredWrong;

    public UserProblemQuestion() {
    }

    public UserProblemQuestion(final String userId, final Question question) {
        this.userId = userId;
        this.questionId = question.getId();
        this.questionTitle = question.getText();
        this.sectionId = question.getSectionId();
        this.countAnsweredWrong = 0;
    }

    public UserProblemQuestion(final String userId, final String questionId, final String questionTitle, final String sectionId) {
        this.userId = userId;
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.sectionId = sectionId;
        this.countAnsweredWrong = 1;
    }

    public String getUserId() {
        return userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(final String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void adjustCount(boolean result) {
        if (result) {
            countAnsweredWrong -= 1;
        } else {
            countAnsweredWrong += 1;
        }
    }
}