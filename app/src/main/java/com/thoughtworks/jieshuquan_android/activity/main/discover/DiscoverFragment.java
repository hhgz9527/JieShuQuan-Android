package com.thoughtworks.jieshuquan_android.activity.main.discover;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.activity.main.OnFragmentInteractionListener;
import com.thoughtworks.jieshuquan_android.model.Discover;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DiscoverFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListAdapter adapter;
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
        this.adapter = new MyAdapter();
        mListView.setAdapter(adapter);

        this.getAllDiscoverMessage();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
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
        final AVQuery<AVObject> query = new AVQuery<AVObject>("Find");
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null && list.size() > 0) {
                    DiscoverFragment.this.mDiscoverList = list;
                    ((BaseAdapter) adapter).notifyDataSetChanged();
                }
            }
        });
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDiscoverList == null ? 0 : mDiscoverList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_list_item, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }
            Discover discover = (Discover) DiscoverFragment.this.mDiscoverList.get(position);
            ((ViewHolder) convertView.getTag()).populate(discover);
            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.discoverItemIcon)
            ImageView iconImageView;
            @InjectView(R.id.discoverItemName)
            TextView nameTextView;

            @InjectView(R.id.discoverItemTime)
            TextView timeTextView;

            @InjectView(R.id.discoverItemContent)
            TextView contentTextView;

            public ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }

            public void populate(Discover discover) {

                final AVQuery<AVUser> query = AVUser.getQuery();
                query.whereEqualTo("objectId", discover.getUser().getObjectId());
                query.findInBackground(new FindCallback<AVUser>() {
                    @Override
                    public void done(List<AVUser> list, AVException e) {
                        if (e == null && list.size() > 0) {
                            AVUser user = list.get(0);
                            nameTextView.setText(user.getUsername());
                            AVFile file = user.getAVFile("avatar");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, AVException e) {
                                    if (e == null){
                                        Bitmap icon =  BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                        iconImageView.setImageBitmap(icon);
                                    }
                                }
                            });

                        }
                    }
                });
                timeTextView.setText(new PrettyTime(new Date()).format(discover.getCreatedAt()));
                if (discover.getBook() != null && discover.getBook().length() > 0) {
                    String content = "我添加了一本新书《" + discover.getBook() + "》。";
                    contentTextView.setText(content);
                } else {
                    contentTextView.setText(discover.getTwitter());
                }
            }
        }
    }

}
