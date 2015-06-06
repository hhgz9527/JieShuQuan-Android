package com.thoughtworks.jieshuquan_android.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thoughtworks.jieshuquan_android.R;

import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements Callback {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        showLoginFragment();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() <= 1) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void showLoginFragment() {
        getFragmentManager().popBackStack(LoginFragment.TAG, 0);
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.login_container, new LoginFragment())
                    .addToBackStack(LoginFragment.TAG).commit();
        }
    }

    @Override
    public void showRegisterFragment() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        android.R.animator.fade_in, android.R.animator.fade_out,
                        android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.login_container, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showForgetPwdFragment() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_from_right,R.animator.slide_out_to_left,
                        R.animator.slide_in_from_left, R.animator.slide_out_to_right)
                .replace(R.id.login_container, new ForgetPwdFragment())
                .addToBackStack(null)
                .commit();
    }
}