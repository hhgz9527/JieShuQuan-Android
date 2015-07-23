package com.thoughtworks.jieshuquan.service;

import android.text.Editable;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.thoughtworks.jieshuquan.Constants;

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
    public void signUp(String email, String password,String office, SignUpCallback callback) {
        AVUser user = new AVUser();
        user.setUsername(this.getName(email));
        user.setEmail(email);
        user.setPassword(password);
        user.put(Constants.KUSER_OFFICE,office);
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

    //Modify Password
    public void  updatePassword(String oldPwd, String newPwd, UpdatePasswordCallback callback){
        AVUser.getCurrentUser().updatePasswordInBackground(oldPwd,newPwd,callback);
    }

    //Modify NickName
    public void updateNickName(Editable name, SaveCallback callback){
        AVUser.getCurrentUser().put("nickname",name);
        AVUser.getCurrentUser().saveEventually(callback);
    }

    public void logout() {
        AVUser.logOut();
    }

    public String getName(String email) {

        return email.substring(0, email.lastIndexOf("@"));
    }
}

