package com.thoughtworks.jieshuquan_android.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.thoughtworks.jieshuquan_android.Constants;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.model.Discover;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by leihuang on 6/17/15.
 */
public class DiscoveryViewHolder {
    @InjectView(R.id.discoverItemIcon)
    ImageView iconImageView;

    @InjectView(R.id.discoverItemName)
    TextView nameTextView;

    @InjectView(R.id.discoverItemTime)
    TextView timeTextView;

    @InjectView(R.id.discoverItemContent)
    TextView contentTextView;

    public DiscoveryViewHolder(View view) {
        ButterKnife.inject(this, view);
    }

    public void populate(Discover discover, final Context context) {
        final AVQuery<AVUser> query = AVUser.getQuery();
        query.whereEqualTo(Constants.KOBJECT_ID, discover.getUser().getObjectId());
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null && list.size() > 0) {
                    AVUser user = list.get(0);
                    nameTextView.setText(user.getUsername());
                    AVFile file = user.getAVFile(Constants.KAVATAR);
                    String imageUrlString = file.getUrl();
                    Glide.with(context).load(imageUrlString).into(iconImageView);
                }
            }
        });
        timeTextView.setText(new PrettyTime(new Date()).format(discover.getCreatedAt()));
        if (discover.getBook() != null && discover.getBook().length() > 0) {
            String content = R.string.discover_add_book_message_header + discover.getBook() + R.string.discover_add_book_message_footer;
            contentTextView.setText(content);
        } else {
            contentTextView.setText(discover.getTwitter());
        }
    }
}
