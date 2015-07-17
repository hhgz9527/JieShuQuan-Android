package com.thoughtworks.jieshuquan.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.MainActivity;
import com.thoughtworks.jieshuquan.service.AuthService;
import com.thoughtworks.jieshuquan.utils.ShowUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    @InjectView(R.id.account_name)
    EditText accountName;

    @InjectView(R.id.account_pwd)
    EditText accountPwd;

    private LoginActivityListener mCallback;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof LoginActivityListener)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallback = (LoginActivityListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.login_btn)
    void login() {
        String nameString = accountName.getText().toString();
        if (TextUtils.isEmpty(nameString) || !android.util.Patterns.EMAIL_ADDRESS.matcher(nameString).matches()) {
            accountName.setError(getString(R.string.msg_error_account_name));
            return;
        }

        String pwdString = accountPwd.getText().toString();
        if (TextUtils.isEmpty(pwdString)) {
            accountPwd.setError(getString(R.string.msg_error_account_pwd));
            return;
        }

        AuthService.getInstance().login(nameString, pwdString, new LogInCallback() {
            public void done(AVUser user, AVException e) {
                if (user != null) {
                    ShowUtils.showShortToast(getString(R.string.msg_login_success));
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    ShowUtils.showShortToast(e.toString());
                }
            }
        });
    }

    @OnClick(R.id.forget_btn)
    void showForgetPwdFragment() {
        if(mCallback != null) {
            mCallback.showForgetPwdFragment();
        }
    }

    @OnClick(R.id.register_btn)
    void showRegisterFragment() {
        if(mCallback != null) {
            mCallback.showRegisterFragment();
        }
    }


}
