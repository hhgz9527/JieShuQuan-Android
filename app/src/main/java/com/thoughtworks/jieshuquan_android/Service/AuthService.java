package com.thoughtworks.jieshuquan_android.Service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

/**
 * Created by leihuang on 5/27/15.
 */
public class AuthService {

    private static AuthService instance;

    private AuthService() {
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public void signUp(String email, String password) {
        AVUser user = new AVUser();
        user.setUsername(this.getName(email));
        user.setEmail(email);
        user.setPassword(password);

        // 其他属性可以像其他AVObject对象一样使用put方法添加

        user.signUpInBackground(new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // successfully
                } else {
                    // failed
                }
            }
        });
    }

    public String getName(String email) {

        return email.substring(0, email.lastIndexOf("@"));
    }
}

