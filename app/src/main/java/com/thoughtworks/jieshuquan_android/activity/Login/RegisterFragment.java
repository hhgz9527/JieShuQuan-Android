package com.thoughtworks.jieshuquan_android.activity.login;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.service.AuthService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterFragment extends Fragment {

    @InjectView(R.id.account_name)
    EditText accountName;
    @InjectView(R.id.account_pwd)
    EditText accountPwd;
    @InjectView(R.id.account_pwd_confirm)
    EditText accountPwdConfirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.register_btn)
    void register() {
        if (accountName.getText().toString().length() == 0) {
            accountName.setError(getString(R.string.msg_error_account_name));
            return;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(accountName.getText().toString()).matches()) {
            accountName.setError(getString(R.string.msg_error_account_name));
            return;
        }

        if (accountPwd.getText().toString().length() == 0) {
            accountPwd.setError(getString(R.string.msg_error_account_pwd));
            return;
        }
        if (accountPwdConfirm.getText().toString().length() == 0) {
            accountPwdConfirm.setError(getString(R.string.msg_error_account_pwd));
            return;
        }
        if (!accountPwd.getText().toString().equals(accountPwdConfirm.getText().toString())) {
            this.showErrorToast(getString(R.string.msg_error_password_should_same));
            return;
        }

        AuthService service = AuthService.getInstance();
        service.signUp(accountName.getText().toString(), accountPwd.getText().toString(), new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    RegisterFragment.this.showErrorToast(getString(R.string.msg_register_success));
                    AVUser.getCurrentUser().logOut();
                    getFragmentManager().popBackStack();

                } else {
                    RegisterFragment.this.showErrorToast(e.toString());
                }
            }
        });
    }

    public void showErrorToast(String errorMessage) {

        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }


}
