package com.thoughtworks.jieshuquan.login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.AuthService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ForgetPwdFragment extends Fragment {

    @InjectView(R.id.account_name)
    EditText accountName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_pwd, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.send_mail_btn)
    public void pressResetPwdButton(View v) {
        String nameString = accountName.getText().toString();
        if (TextUtils.isEmpty(nameString)) {
            accountName.setError(getString(R.string.msg_error_account_name));
            return;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(accountName.getText().toString()).matches()) {
            accountName.setError(getString(R.string.msg_error_account_name));
            return;
        }

        AuthService auther = AuthService.getInstance();
        auther.resetPassword(nameString, new RequestPasswordResetCallback() {
            public void done(AVException e) {
                if (e == null) {
                    showErrorToast(getString(R.string.msg_reset_success));
                    getFragmentManager().popBackStack();
                } else {
                    showErrorToast(getString(R.string.msg_reset_failure));
                }
            }
        });
    }

    public void showErrorToast(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
