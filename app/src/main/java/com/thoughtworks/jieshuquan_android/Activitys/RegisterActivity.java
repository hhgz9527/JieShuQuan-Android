package com.thoughtworks.jieshuquan_android.Activitys;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.Service.AuthService;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void pressReisterButton(View v){

        EditText emailText = (EditText) findViewById(R.id.nameText);
        if (emailText.getText().toString().length() == 0){
            this.showErrorToast(getString(R.string.error_inputName));
            return;
        }

        EditText pwdText = (EditText) findViewById(R.id.pwdText);
        if (pwdText.getText().toString().length() == 0){
            this.showErrorToast(getString(R.string.error_inputpwd));
            return;
        }
        EditText confirmPwdText = (EditText) findViewById(R.id.confirmpwdText);
        if (confirmPwdText.getText().toString().length() == 0){
            this.showErrorToast(getString(R.string.error_inputpwd));
            return;
        }
        if(!pwdText.getText().toString().equals(confirmPwdText.getText().toString())){
            this.showErrorToast("please confirm the password is same!");
            return;
        }

        AuthService service = AuthService.getInstance();
        service.signUp(emailText.getText().toString(),pwdText.getText().toString(),new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // successfully
                    finish();
                } else {
                    // failed
                    RegisterActivity.this.showErrorToast(e.toString());
                }
            }});
    }

    public void showErrorToast(String errorMessage) {
        Context context = getApplicationContext();
        CharSequence text = errorMessage;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
