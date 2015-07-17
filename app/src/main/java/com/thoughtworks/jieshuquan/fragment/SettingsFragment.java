package com.thoughtworks.jieshuquan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thoughtworks.jieshuquan.BuildConfig;
import com.thoughtworks.jieshuquan.JieShuQuanApplication;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.MainActivity;
import com.thoughtworks.jieshuquan.login.LoginActivity;
import com.thoughtworks.jieshuquan.service.AuthService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.logout)
    public void onLogoutClick() {
        AuthService.getInstance().logout();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }
}
