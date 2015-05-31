package com.thoughtworks.jieshuquan_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.activity.login.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "click again will exit App", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            finish();
            System.exit(0);
        }
    }

    @OnClick(R.id.main_login_btn)
    void Login() {
//        if (AVUser.getCurrentUser() != null) {
//            //need login
//        }
        Intent showLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(showLoginIntent);
    }

}
