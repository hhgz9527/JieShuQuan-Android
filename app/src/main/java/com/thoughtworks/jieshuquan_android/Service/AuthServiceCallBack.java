package com.thoughtworks.jieshuquan_android.Service;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;

/**
 * Created by leihuang on 5/27/15.
 */
public abstract class AuthServiceCallBack extends AVCallback<Void> {

    public AuthServiceCallBack() {
    }

    public abstract void done(AVException var1);

    protected final void internalDone0(Void t, AVException parseException) {
        this.done(parseException);
    }
}
