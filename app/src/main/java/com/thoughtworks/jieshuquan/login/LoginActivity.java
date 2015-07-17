package com.thoughtworks.jieshuquan.login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thoughtworks.jieshuquan.R;

import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements LoginActivityListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        showLoginFragment();
    }

    @Override
    public void showLoginFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.login_container, new LoginFragment())
                .commit();

    }

    @Override
    public void showRegisterFragment() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                        R.animator.slide_in_from_left, R.animator.slide_out_to_right)
                .replace(R.id.login_container, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showForgetPwdFragment() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                        R.animator.slide_in_from_left, R.animator.slide_out_to_right)
                .replace(R.id.login_container, new ForgetPwdFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

}
