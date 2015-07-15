package com.thoughtworks.jieshuquan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.Discover;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SendTwitterActivity extends AppCompatActivity {

    @InjectView(R.id.include)
    Toolbar mToolbar;

    @InjectView(R.id.messageText)
    EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_twitter);

        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.send_message);
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_twitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_send: {
                    if (messageText.getText().length()> 140 || messageText.getText().length() == 0){
                        Toast.makeText(getApplicationContext(),R.string.show_not_length_than,Toast.LENGTH_SHORT).show();
                    } else {
                        SendTwitterActivity.this.sendMmessage(messageText.getText().toString());
                    }
                }
                break;
            }
            return true;
        }
    };


    private void sendMmessage(String message){
        Discover discover = new Discover();
        discover.setUser(AVUser.getCurrentUser());
        discover.setTwitter(message);
        discover.setType(Constants.DISCOVERTYPE_TWITTER);
        discover.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), R.string.send_success, Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
