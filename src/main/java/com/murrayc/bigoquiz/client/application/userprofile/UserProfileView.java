package com.murrayc.bigoquiz.client.application.userprofile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.murrayc.bigoquiz.client.BigOQuizMessages;
import com.murrayc.bigoquiz.client.Log;
import com.murrayc.bigoquiz.client.LoginInfo;
import com.murrayc.bigoquiz.client.application.Utils;
import com.murrayc.bigoquiz.client.ui.BigOQuizConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by murrayc on 1/21/16.
 */
public class UserProfileView extends ViewWithUiHandlers<UserProfileUserEditUiHandlers>
        implements UserProfilePresenter.MyView {
    // BigOQuizConstants.java is generated in the target/ directory,
    // from BigOQuizConstants.properties
    // by the gwt-maven-plugin's i18n (mvn:i18n) goal.
    private final BigOQuizConstants constants = GWT.create(BigOQuizConstants.class);
    private final BigOQuizMessages messages = GWT.create(BigOQuizMessages.class);

    private final Label usernameLabel = new InlineLabel();
    private final Anchor logoutLabel = new Anchor(constants.logOut());
    private final Label labelError = Utils.createServerErrorLabel(constants);
    private final Button buttonResetSections = new Button(constants.buttonResetSections());

    private Panel loginParagraph = null;
    private final InlineHTML loginLabel = new InlineHTML();

    UserProfileView() {
        @NotNull final FlowPanel mainPanel = new FlowPanel();
        mainPanel.addStyleName("content-panel");

        Utils.addHeaderToPanel(2, mainPanel, constants.profileTitle());

        mainPanel.add(labelError);

        //This is only visible when necessary:
        loginParagraph = Utils.addParagraphWithChild(mainPanel, loginLabel);
        loginParagraph.setVisible(false);

        Utils.addParagraphWithText(mainPanel, constants.username(), "username-title-label");

        mainPanel.add(logoutLabel);
        logoutLabel.addStyleName("logout-label");

        Utils.addParagraphWithChild(mainPanel, buttonResetSections);
        buttonResetSections.addStyleName("button-reset-sections");
        buttonResetSections.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                onResetSectionsButton();
            }
        });


        //Show the sections (user recent history):
        //Put it in a div with a specific class, so we can hide in on wider
        //screens where it is already visible in the sidebar:
        @NotNull final SimplePanel userHistoryParent = new SimplePanel();
        userHistoryParent.addStyleName("user-profile-user-history-panel");
        mainPanel.add(userHistoryParent);

        @NotNull final SimplePanel userHistoryRecentPanel = new SimplePanel();
        bindSlot(UserProfilePresenter.SLOT_USER_HISTORY_RECENT, userHistoryRecentPanel);
        userHistoryParent.add(userHistoryRecentPanel);

        initWidget(mainPanel);
    }

    @Override
    public void setUserStatusFailed() {
        labelError.setVisible(true);
    }

    @Override
    public void setLoginInfo(@NotNull final LoginInfo loginInfo) {
        //Defaults:
        usernameLabel.setVisible(false);
        logoutLabel.setVisible(false);
        buttonResetSections.setVisible(false);
        loginParagraph.setVisible(false);

        if (loginInfo == null) {
            Log.error("setLoginInfo(): loginInfo is null.");
            return;
        }

        if (loginInfo.isLoggedIn()) {
            usernameLabel.setVisible(true);
            logoutLabel.setVisible(true);
            buttonResetSections.setVisible(true);

            usernameLabel.setText(loginInfo.getNickname());
            logoutLabel.setHref(loginInfo.getLogoutUrl());
        } else {
            loginLabel.setHTML(messages.pleaseSignIn(loginInfo.getLoginUrl()));
            loginParagraph.setVisible(true);
        }
    }

    private void onResetSectionsButton() {
        //TODO: Do this in the presenter?
        @NotNull final DialogBox dialog = new DialogBox();
        dialog.setText(constants.dialogResetSectionsTitle());

        @NotNull final Button buttonOK = new Button(constants.dialogResetSectionsOkButton());
        buttonOK.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dialog.hide();
                getUiHandlers().onResetSections();
            }
        });

        @NotNull final Button buttonCancel = new Button(constants.dialogResetSectionsCancelButton());
        buttonCancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });

        @NotNull final Panel panelDialog = new FlowPanel();
        Utils.addParagraphWithText(panelDialog, constants.dialogResetSectionsText(),
                "reset-sections-confirm-dialog-text");
        @NotNull final Panel panelButtons = new FlowPanel();
        panelButtons.addStyleName("reset-sections-confirm-dialog-buttons-panel");
        panelButtons.add(buttonCancel);
        buttonCancel.addStyleName("reset-sections-confirm-dialog-cancel-button");
        panelButtons.add(buttonOK);
        buttonOK.addStyleName("reset-sections-confirm-dialog-ok-button");
        panelDialog.add(panelButtons);
        dialog.setWidget(panelDialog);

        dialog.show();
    }
}
