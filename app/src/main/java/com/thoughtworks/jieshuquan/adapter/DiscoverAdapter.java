package com.thoughtworks.jieshuquan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.Discover;
import com.thoughtworks.jieshuquan.viewholder.DiscoveryViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DiscoverAdapter extends BaseAdapter {

    private Context mContext;
    private List<Discover> mList;

    public DiscoverAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void setList(List<Discover> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.discover_list_item, parent, false);
            convertView.setTag(new DiscoveryViewHolder(convertView));
        }
        Discover discover = mList.get(position);
        ((DiscoveryViewHolder) convertView.getTag()).populate(discover, mContext);
        return convertView;
    }

}
