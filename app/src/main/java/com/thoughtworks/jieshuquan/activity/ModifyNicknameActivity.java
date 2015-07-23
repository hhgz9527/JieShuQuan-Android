package com.thoughtworks.jieshuquan.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.AuthService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ModifyNicknameActivity extends ToolbarBaseActivity {

    @InjectView(R.id.new_nickname)
    EditText nickNameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.modify_nickname;
    }

    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup rootView) {
        inflater.inflate(R.layout.activity_modify_nickname, rootView, true);
    }

    @OnClick (R.id.complete_nickname)
    public void pressCompleteButton(){
        if (nickNameText.getText().length() > 0){
            AuthService.getInstance().updateNickName(nickNameText.getText(), new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null){
                        Toast.makeText(getApplicationContext(), R.string.modify_nickname_success, Toast.LENGTH_SHORT).show();
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else {
            Toast.makeText(this,R.string.modify_nickname_empty, Toast.LENGTH_SHORT).show();

        }
    }

}
