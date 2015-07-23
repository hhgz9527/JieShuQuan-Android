package com.thoughtworks.jieshuquan.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVUser;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.MainActivity;

/**
 * Created by pchen on 7/24/15.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, 1000);
    }

    protected void init() {
        Intent intent;
        if (AVUser.getCurrentUser() == null) {
            //need login
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        this.finish();
    }
}
