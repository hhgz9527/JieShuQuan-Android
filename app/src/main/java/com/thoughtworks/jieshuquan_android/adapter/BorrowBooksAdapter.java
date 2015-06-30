package com.thoughtworks.jieshuquan_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.jieshuquan_android.R;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.jieshuquan_android.utils.CommonUtils.dipToPx;

public class BorrowBooksAdapter extends BaseAdapter {
    private Context mContext;
    // references to our images
    public List<Integer> mThumbIds = new ArrayList<Integer>();
    public BorrowBooksAdapter(Context c) {
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
            layout.setLayoutParams(new GridView.LayoutParams(dipToPx(110), dipToPx(188)));
            layout.setOrientation(LinearLayout.VERTICAL);

            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(dipToPx(110), dipToPx(160)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setImageResource(mThumbIds.get(position));

            TextView textview = new TextView(mContext);
            textview.setLayoutParams(new GridView.LayoutParams(dipToPx(110), dipToPx(20)));
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


}
