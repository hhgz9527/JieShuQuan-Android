package com.thoughtworks.jieshuquan.activity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.utils.ShowUtils;

import butterknife.ButterKnife;


public class FeedbackActivity extends ToolbarBaseActivity {

    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup rootView) {
        inflater.inflate(R.layout.activity_feedback, rootView, true);
        ButterKnife.inject(this);
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.feedback;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.submit) {
            ShowUtils.showShortToast("submit");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
