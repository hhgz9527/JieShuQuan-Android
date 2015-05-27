package com.thoughtworks.jieshuquan_android.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.Service.AuthService;


public class ForgetPwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forget_pwd, menu);
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

    public void pressResetPwdButton (View v){
        EditText nameText = (EditText) findViewById(R.id.nameText);
        String nameString = nameText.getText().toString();
        if (nameString.length() == 0){
            this.showErrorToast(getString(R.string.error_inputName));
            return;
        }
        AuthService auther = AuthService.getInstance();
        auther.resetPassword(nameString, new RequestPasswordResetCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 已发送一份重置密码的指令到用户的邮箱
                    ForgetPwdActivity.this.showErrorToast("reset request is success");
                    finish();
                } else {
                    // 重置密码出错。
                    ForgetPwdActivity.this.showErrorToast("reset request is failure");
                }
            }
        });
    }

    public void showErrorToast(String errorMessage) {
        Context context = getApplicationContext();
        CharSequence text = errorMessage;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
