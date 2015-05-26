package com.thoughtworks.jieshuquan_android;

import android.app.AlertDialog;
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


public class loginActivity extends ActionBarActivity {
    public static String TAG = "loginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "login button click");

                EditText nameText = (EditText) findViewById(R.id.nameText);
                String nameString = nameText.getText().toString();
                if (nameString.length() == 0){
                    loginActivity.this.showErrorToast(getString(R.string.error_inputName));
                    return;
                }

                EditText pwdText = (EditText) findViewById(R.id.pwdText);
                String pwdString = pwdText.getText().toString();
                if (pwdString.length() == 0){
                    loginActivity.this.showErrorToast("please input password");
                    return;
                }
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

    public void  showErrorToast(String errorMessage){
        Context context = getApplicationContext();
        CharSequence text = errorMessage;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public  void showForgetPwdView (View v){

        Log.d(TAG, "forgetPwd  button click");
        Intent showForgetActivity = new Intent(this,forgetPwdActivity.class);
        startActivity(showForgetActivity);
    }

    public void showRegisterView (View v){

        Log.v(TAG,"register button click");
        Intent showRegisterActivity = new Intent(this,registerActivity.class);
        startActivity(showRegisterActivity);
    }
}
