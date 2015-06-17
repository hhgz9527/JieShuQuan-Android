package com.thoughtworks.jieshuquan_android.activity.main.borrow;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.activity.main.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BorrowFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public static BorrowFragment newInstance() {
        BorrowFragment fragment = new BorrowFragment();
        return fragment;
    }

    public BorrowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_borrow, container, false);

        GridView gridview = (GridView) inflate.findViewById(R.id.gridview);
        final ImageAdapter imageAdapter = new ImageAdapter(this.getActivity());
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(parent.getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount>=totalItemCount){
                    Integer[] books = {R.drawable.s1495029,R.drawable.s1106934,R.drawable.s1086045};
                    Random random = new Random();
                    int nextInt = random.nextInt(3);
                    imageAdapter.mThumbIds.add(books[nextInt]);
                    imageAdapter.notifyDataSetChanged();
                }
            }
        });
        return inflate;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

}

class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // references to our images
    public List<Integer> mThumbIds = new ArrayList<Integer>();
    public ImageAdapter(Context c) {
        mContext = c;
        mThumbIds.add(R.drawable.s1086045);
        mThumbIds.add(R.drawable.s1495029);
        mThumbIds.add(R.drawable.s1106934);
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            layout = new LinearLayout(mContext);
            layout.setLayoutParams(new GridView.LayoutParams(dip2px(110), dip2px(188)));
            layout.setOrientation(LinearLayout.VERTICAL);

            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(dip2px(110), dip2px(160)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setImageResource(mThumbIds.get(position));

            TextView textview = new TextView(mContext);
            textview.setLayoutParams(new GridView.LayoutParams(dip2px(110), dip2px(20)));
            textview.setText("Book " + position);
            textview.setTextColor(Color.RED);
            textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

            layout.addView(imageView);
            layout.addView(textview);
        } else {
            layout = (LinearLayout) convertView;
            ImageView imageView = (ImageView) layout.getChildAt(0);
            imageView.setImageResource(mThumbIds.get(position));
            TextView textview = (TextView) layout.getChildAt(1);
            textview.setText("Book " + position);
        }
        return layout;
    }

    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
