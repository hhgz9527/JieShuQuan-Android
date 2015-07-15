package com.thoughtworks.jieshuquan.utils;

import android.content.res.Resources;

public class CommonUtils {

    public static int dipToPx(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
