package com.murrayc.bigoquiz.client.application.userhistory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.murrayc.bigoquiz.client.BigOQuizMessages;
import com.murrayc.bigoquiz.client.UserHistory;
import com.murrayc.bigoquiz.client.application.ContentViewWithUIHandlers;
import com.murrayc.bigoquiz.client.application.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by murrayc on 1/21/16.
 */
public class UserHistoryView extends ContentViewWithUIHandlers<UserHistoryUserEditUiHandlers>
        implements UserHistoryPresenter.MyView {
    private final BigOQuizMessages messages = GWT.create(BigOQuizMessages.class);

    private final Panel panelContent = new FlowPanel();
    private final PlaceManager placeManager;

    @Inject
    UserHistoryView(PlaceManager placeManager) {
        this.placeManager = placeManager;

        setTitle(constants.historyTitle());

        final Button buttonResetSections = new Button(constants.buttonResetSections());
        buttonResetSections.addStyleName("button-reset-sections");
        buttonResetSections.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                onResetSectionsButton();
            }
        });
        mainPanel.add(buttonResetSections);

        //Show the userhistorysections (user recent history):
        @NotNull final SimplePanel userHistoryParent = new SimplePanel();
        //userHistoryParent.addStyleName("user-history-sections-panel");
        mainPanel.add(userHistoryParent);

        @NotNull final SimplePanel userHistoryRecentPanel = new SimplePanel();
        bindSlot(UserHistoryPresenter.SLOT_USER_HISTORY_RECENT, userHistoryRecentPanel);
        userHistoryParent.add(userHistoryRecentPanel);
    }

    private void onResetSectionsButton() {
        //TODO: Do this in the presenter?
        Utils.showResetConfirmationDialog(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                getUiHandlers().onResetSections();
            }
        });
    }

    @Override
    public void setServerFailed() {
        setErrorLabelVisible(true);
    }
}
