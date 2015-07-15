package com.thoughtworks.jieshuquan.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.listener.MainActivityListener;
import com.thoughtworks.jieshuquan.activity.MineBookActivity;
import com.thoughtworks.jieshuquan.activity.SettingsActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MineFragment extends Fragment {

    private static final String ARG_USERNAME = "user_name";
    private static final String ARG_PARAM2 = "param2";

    @InjectView(R.id.head_borrow)
    TextView mBadgeBorrow;
    @InjectView(R.id.head_return)
    TextView mBadgeReturn;
    @InjectView(R.id.head)
    RoundedImageView mHeadView;

    private String mUserName;
    private String mParam2;

    private MainActivityListener mListener;

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (MainActivityListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainActivityListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_USERNAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.inject(this, view);
        initHead();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void initHead() {
        int borrowCount = 1;
        int returnCount = 29;

        setBadgeView(borrowCount, mBadgeBorrow);
        setBadgeView(returnCount, mBadgeReturn);
        mHeadView.setImageResource(R.drawable.ic_tab_books);
    }

    private void setBadgeView(int borrowCount, TextView badgeView) {
        if (borrowCount <= 0) {
            badgeView.setVisibility(View.GONE);
        } else {
            badgeView.setText(String.valueOf(borrowCount));
            badgeView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.list_my_book)
    void showMyBook() {
        Intent intent = new Intent(getActivity(), MineBookActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.list_my_rent)
    void showMyRent() {
        Toast.makeText(getActivity(), "My Rent", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.list_my_setting)
    void showSetting() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

}
