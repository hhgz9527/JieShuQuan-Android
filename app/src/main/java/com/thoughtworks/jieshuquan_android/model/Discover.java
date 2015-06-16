package com.thoughtworks.jieshuquan_android.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Created by leihuang on 6/16/15.
 */
@AVClassName("Find")

public class Discover extends AVObject {
    public String getBook() {
        return getString("book");
    }

    public void setBook(String book) {
        put("book", book);
    }

    public String getTwitter() {
        return getString("twitter");
    }

    public void setTwitter(String twitter) {
        put("twitter", twitter);
    }

    public AVUser getUser() {
        return getAVObject("user");
    }

    public void setUser(AVUser user) {
        put("user", user);
    }
}
