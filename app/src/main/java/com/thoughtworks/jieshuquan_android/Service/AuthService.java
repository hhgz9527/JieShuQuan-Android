package com.thoughtworks.jieshuquan_android.service;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
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

    // Register
    public void signUp(String email, String password, SignUpCallback callback) {
        AVUser user = new AVUser();
        user.setUsername(this.getName(email));
        user.setEmail(email);
        user.setPassword(password);
        user.signUpInBackground(callback);
    }

    //Login
    public void login(String email, String password, LogInCallback callback) {

        AVUser.logInInBackground(this.getName(email), password, callback);
    }

    //Reset Password
    public void resetPassword (String email, RequestPasswordResetCallback callback){
        AVUser.requestPasswordResetInBackground(email,callback);
    }

    public void logout() {
        AVUser.logOut();
    }

    public String getName(String email) {

        return email.substring(0, email.lastIndexOf("@"));
    }
}

