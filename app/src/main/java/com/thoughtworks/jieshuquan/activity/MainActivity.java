package com.thoughtworks.jieshuquan.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.fragment.BorrowFragment;
import com.thoughtworks.jieshuquan.fragment.DiscoverFragment;
import com.thoughtworks.jieshuquan.fragment.FragmentCallbacks;
import com.thoughtworks.jieshuquan.fragment.MineFragment;
import com.thoughtworks.jieshuquan.fragment.PeopleFragment;
import com.thoughtworks.jieshuquan.login.LoginFragment;
import com.thoughtworks.jieshuquan.view.TabBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements FragmentCallbacks {

    public static final String TAG = MainActivity.class.getSimpleName();
    private long exitTime = 0;

    SectionsPagerAdapter mSectionsPagerAdapter;


    @InjectView(R.id.tab_bar)
    TabBar mTabBar;

    @InjectView(R.id.pager)
    ViewPager mViewPager;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mTabBar.setOnTabClickListener(new TabBar.OnTabClickListener() {
            @Override
            public void onTabClick(View tabView, int position) {
                mViewPager.setCurrentItem(position, false);
            }
        });
        mTabBar.setCurrentItem(0);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabBar.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // rigster push service for main activity
        this.registerPushService();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.SCANER_ACTIVITY_RESULT_TAG && data.getStringExtra("ISBN").length() > 0) {
            this.showAddBookActivity(data.getStringExtra("ISBN"));
        }

        if (resultCode == Constants.LOGIN_ACTIVITY_RESULT_TAG && data.getBooleanExtra(Constants.KSUCCESS, false)) {
            this.registerPushService();
        }

    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), R.string.msg_exit_hint, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showPopularBook() {
        Intent showPopularBook = new Intent(MainActivity.this, PopularActivity.class);
        startActivityForResult(showPopularBook, Constants.POPULAR_ACTIVITY_RESULT_TAG);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private static final int PAGE_SEARCH = 0;
        private static final int PAGE_BOOKS = 1;
        private static final int PAGE_PEOPLE = 2;
        private static final int PAGE_MINE = 3;
        private static final int PAGE_COUNT = 4;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_SEARCH: {
                    return DiscoverFragment.newInstance();

                }
                case PAGE_BOOKS:
                    return BorrowFragment.newInstance();
                case PAGE_PEOPLE:
                    return PeopleFragment.newInstance("test", "PeopleFragment");
                case PAGE_MINE:
                    return MineFragment.newInstance("test", "MoreFragment");
                default:
                    return LoginFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_scan: {
                    MainActivity.this.startScanner();
                }
                break;
                case R.id.action_twitter: {
                    MainActivity.this.showSendTwitterActivity();
                }
                break;
            }
            return true;
        }
    };

    private void startScanner() {
        Intent startScanner = new Intent(this, ScannerActivity.class);
        startActivityForResult(startScanner, Constants.SCANER_ACTIVITY_RESULT_TAG);
    }

    private void showAddBookActivity(String isbn) {
        Intent showAddBookIntent = new Intent(MainActivity.this, AddBookToLibraryActivity.class);
        showAddBookIntent.putExtra("ISBN", isbn);
        startActivity(showAddBookIntent);
    }

    private void showSendTwitterActivity() {
        Intent showSendTwitterIntent = new Intent(MainActivity.this, SendTwitterActivity.class);
        startActivity(showSendTwitterIntent);
    }

    private void registerPushService() {
        PushService.setDefaultPushCallback(this, MainActivity.class);
        PushService.subscribe(this, "public", MainActivity.class);
        PushService.subscribe(this, "private", MainActivity.class);
        PushService.subscribe(this, "protected", MainActivity.class);

        // track AVUser
        Intent intent = getIntent();
        AVAnalytics.trackAppOpened(intent);

        // open AVOS feedback sync
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.sync();
    }

}
