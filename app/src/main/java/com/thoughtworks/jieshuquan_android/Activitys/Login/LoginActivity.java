package com.thoughtworks.jieshuquan_android.Activitys.Login;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import com.thoughtworks.jieshuquan_android.Service.AuthService;


public class LoginActivity extends ActionBarActivity {
    public static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "login button click");

                EditText nameText = (EditText) findViewById(R.id.nameText);
                String nameString = nameText.getText().toString();
                if (nameString.length() == 0) {
                    LoginActivity.this.showErrorToast(getString(R.string.error_inputName));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void showErrorToast(String errorMessage) {
        Context context = getApplicationContext();
        CharSequence text = errorMessage;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
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
