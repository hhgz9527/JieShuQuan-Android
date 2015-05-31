package com.thoughtworks.jieshuquan_android.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.service.AuthService;

import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "login button click");

                EditText nameText = (EditText) findViewById(R.id.nameText);
                String nameString = nameText.getText().toString();
                if (nameString.length() == 0) {
                    LoginActivity.this.showErrorToast(getString(R.string.error_msg_account_name));
                    return;
                }

                EditText pwdText = (EditText) findViewById(R.id.pwdText);
                String pwdString = pwdText.getText().toString();
                if (pwdString.length() == 0) {
                    LoginActivity.this.showErrorToast("please input password");
                    return;
                }

                AuthService auther = AuthService.getInstance();
                auther.login(nameString, pwdString, new LogInCallback() {
                    public void done(AVUser user, AVException e) {
                        if (user != null) {
                            // 登录成功
                            LoginActivity.this.showErrorToast("login success");
                            finish();
                        } else {
                            // 登录失败
                            LoginActivity.this.showErrorToast(e.toString());
                        }
                    }
                });

            }

        });

    }

    public void showErrorToast(String errorMessage) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, errorMessage, duration);
        toast.show();
    }

    public void showForgetPwdView(View v) {
        Log.d(TAG, "forgetPwd  button click");
        Intent showForgetActivity = new Intent(LoginActivity.this, ForgetPwdActivity.class);
        startActivity(showForgetActivity);
        overridePendingTransition(R.anim.pg_push_left_in,android.R.anim.fade_out);
    }

    public void showRegisterView(View v) {
        Log.v(TAG, "register button click");
        Intent showRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(showRegisterActivity);
        overridePendingTransition(R.anim.pg_push_left_in,android.R.anim.fade_out);
    }
}
