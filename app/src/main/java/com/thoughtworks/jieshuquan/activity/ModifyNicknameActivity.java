package com.thoughtworks.jieshuquan.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.AuthService;
import com.thoughtworks.jieshuquan.utils.ShowUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ModifyNicknameActivity extends ToolbarBaseActivity {

    @InjectView(R.id.modify_nickname)
    protected Button modifyNickNameBtn;

    @InjectView(R.id.new_nickname)
    protected EditText nicknameEt;

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

        ButterKnife.inject(this);
    }

    @OnClick(R.id.modify_nickname)
    protected void onClick(View view) {
        showLoadingScreen(getString(R.string.please_wait), getString(R.string.updating_info));
        String nickName = nicknameEt.getText().toString();
        if(!TextUtils.isEmpty(nickName)) {
            AuthService.getInstance().updateNickName(nickName, new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e == null) {
                        ShowUtils.showShortToast(getString(R.string.update_info_success));
                        finish();
                    } else {
                        ShowUtils.showShortToast(getString(R.string.update_info_fail));
                    }
                    hideLoadingScreen();
                }
            });
        } else {
            ShowUtils.showShortToast(getString(R.string.content_is_empty));
        }
    }
}
