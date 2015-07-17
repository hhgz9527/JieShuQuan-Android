package com.thoughtworks.jieshuquan.utils;

import android.widget.Toast;

import com.thoughtworks.jieshuquan.JieShuQuanApplication;


public class ShowUtils {
    public static void showShortToast(String message) {
        Toast.makeText(JieShuQuanApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

}
