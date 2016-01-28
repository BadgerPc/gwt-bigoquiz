package com.murrayc.bigoquiz.client.application.userprofile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.murrayc.bigoquiz.client.LoginInfo;
import com.murrayc.bigoquiz.client.ui.BigOQuizConstants;

/**
 * Created by murrayc on 1/21/16.
 */
public class UserProfileView extends ViewWithUiHandlers<UserProfileUserEditUiHandlers>
        implements UserProfilePresenter.MyView {
    // OnlineGlomConstants.java is generated in the target/ directory,
    // from OnlineGlomConstants.properties
    // by the gwt-maven-plugin's i18n (mvn:i18n) goal.
    private final BigOQuizConstants constants = GWT.create(BigOQuizConstants.class);

    private Label usernameTitleLabel = new Label(constants.username());
    private Label usernameLabel = new Label();
    private final Anchor logoutLabel = new Anchor(constants.logOut());

    private SimplePanel userHistoryRecentPanel = new SimplePanel();

    UserProfileView() {
        final FlowPanel mainPanel = new FlowPanel();
        mainPanel.addStyleName("content-panel");

        final Label titleLabel = new Label("Profile");
        titleLabel.addStyleName("page-title-label");
        mainPanel.add(titleLabel);

        mainPanel.add(usernameTitleLabel);
        usernameTitleLabel.addStyleName("username-title-label");
        mainPanel.add(usernameLabel);
        usernameLabel.addStyleName("username-label");

        mainPanel.add(logoutLabel);
        logoutLabel.addStyleName("logout-label");

        //TODO: Why doesn't this appear?
        bindSlot(UserProfilePresenter.SLOT_USER_HISTORY_RECENT, userHistoryRecentPanel);
        mainPanel.add(userHistoryRecentPanel);
        //userHistoryRecentPanel.addStyleName("debug-userhistory");

        initWidget(mainPanel);
    }


    @Override
    public void setUserStatusFailed() {

    }

    @Override
    public void setLoginInfo(final LoginInfo loginInfo) {
        String username = null;
        String logoutLink = null;
        if (loginInfo != null) {
            username = loginInfo.getNickname();
            logoutLink = loginInfo.getLogoutUrl();
        }

        usernameLabel.setText(username);
        logoutLabel.setHref(logoutLink);
    }
}
