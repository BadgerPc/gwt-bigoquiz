package com.murrayc.bigoquiz.client.application.userhistory;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import org.jetbrains.annotations.NotNull;

/**
 * Created by murrayc on 1/25/16.
 */
public class UserHistoryResetSectionsEvent extends GwtEvent<UserHistoryResetSectionsEvent.EventHandler> {

    public UserHistoryResetSectionsEvent() {
    }

    public interface EventHandler extends com.google.gwt.event.shared.EventHandler {
        void onUserProfileResetSections(UserHistoryResetSectionsEvent event);
    }

    public static final Type<EventHandler> TYPE = new Type<>();

    public static void fire(@NotNull final HasHandlers source) {
        if (TYPE != null) {
            source.fireEvent(new UserHistoryResetSectionsEvent());
        }
    }

    /*
    public static Type<EventHandler> getType() {
        return TYPE;
    }
    */

    @NotNull
    @Override
    public Type<EventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(@NotNull final EventHandler handler) {
        handler.onUserProfileResetSections(this);
    }
}
