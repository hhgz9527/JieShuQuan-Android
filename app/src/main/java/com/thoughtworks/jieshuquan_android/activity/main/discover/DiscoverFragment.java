package com.thoughtworks.jieshuquan_android.activity.main.discover;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.activity.main.OnFragmentInteractionListener;
import com.thoughtworks.jieshuquan_android.adapter.DiscoverAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DiscoverFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private DiscoverAdapter mAdapter;
    public List mDiscoverList;

    @InjectView(R.id.discoverlist)
    ListView mListView;

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    public DiscoverFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.inject(this, view);
        mAdapter = new DiscoverAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        this.getAllDiscoverMessage();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), mAdapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getAllDiscoverMessage() {
        final AVQuery<AVObject> query = new AVQuery<>("Find");
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null && list.size() > 0) {
                    mDiscoverList = list;
                    mAdapter.setList(mDiscoverList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }


}
