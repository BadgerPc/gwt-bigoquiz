package com.murrayc.bigoquiz.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.murrayc.bigoquiz.client.LoginInfo;

/**
 * Created by murrayc on 1/18/16.
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
    public LoginInfo login(final String requestUri);
}
