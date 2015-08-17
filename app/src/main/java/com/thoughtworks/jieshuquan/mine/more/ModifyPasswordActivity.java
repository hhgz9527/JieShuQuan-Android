package com.thoughtworks.jieshuquan.mine.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.ToolbarBaseActivity;
import com.thoughtworks.jieshuquan.service.AuthService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ModifyPasswordActivity extends ToolbarBaseActivity {
    @InjectView(R.id.current_password)
    EditText currentPwdText;
    @InjectView(R.id.new_password)
    EditText newPwdText;
    @InjectView(R.id.repeat_password)
    EditText repeatPwdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.modify_password;
    }

    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup rootView) {
        inflater.inflate(R.layout.activity_modify_password, rootView, true);
    }

    @OnClick(R.id.modify_password_button)
    public void pressModifyButton() {
        if (currentPwdText.getText().toString().length() == 0){
            Toast.makeText(this,R.string.modify_password_current_password_empty,Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPwdText.getText().toString().length() == 0 || !repeatPwdText.getText().toString().equals(newPwdText.getText().toString())){
            Toast.makeText(this,R.string.modify_password_newpassword_error,Toast.LENGTH_SHORT).show();
            return;
        }

        AuthService.getInstance().updatePassword(currentPwdText.getText().toString(), newPwdText.getText().toString(), new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if (e == null){
                    Toast.makeText(getApplicationContext(),R.string.modify_password_success,Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
