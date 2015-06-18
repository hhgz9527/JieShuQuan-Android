package com.thoughtworks.jieshuquan_android.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.thoughtworks.jieshuquan_android.Constants;

/**
 * Created by leihuang on 6/16/15.
 */
@AVClassName("Find")

public class Discover extends AVObject {

    public String getBook() {
        return getString(Constants.KBOOK);
    }

    public void setBook(String book) {
        put(Constants.KBOOK, book);
    }

    public String getTwitter() {
        return getString(Constants.KTWITTER);
    }

    public void setTwitter(String twitter) {
        put(Constants.KTWITTER, twitter);
    }

    public AVUser getUser() {
        return getAVObject(Constants.KUSER);
    }

    public void setUser(AVUser user) {
        put(Constants.KUSER, user);
    }

    public int getType(){
        return getInt(Constants.KTYPE);
    }

    public void setType(int type){
        put(Constants.KTYPE,type);
    }
}
