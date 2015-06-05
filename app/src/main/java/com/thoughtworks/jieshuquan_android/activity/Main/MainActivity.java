package com.thoughtworks.jieshuquan_android.activity.Main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.view.TabBar;
import com.thoughtworks.jieshuquan_android.activity.login.LoginActivity;
import com.thoughtworks.jieshuquan_android.activity.login.LoginFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int NUM_ITEMS = 4;
    private long exitTime = 0;

    SectionsPagerAdapter mSectionsPagerAdapter;


    @InjectView(R.id.tab_bar)
    TabBar mTabBar;

    @InjectView(R.id.pager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mTabBar = (TabBar) findViewById(R.id.tab_bar);
        mTabBar.setOnTabClickListener(new TabBar.OnTabClickListener() {
            @Override
            public void onTabClick(View tabView, int position) {
                mViewPager.setCurrentItem(position, false);
            }
        });
        mTabBar.setCurrentItem(0);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
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

        if (AVUser.getCurrentUser() == null) {
            //need login
            Intent showLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(showLoginIntent);
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), R.string.msg_exit_hint, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            finish();
            System.exit(0);
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private static final int PAGE_SEARCH = 0;
        private static final int PAGE_BOOKS = 1;
        private static final int PAGE_PEOPLE = 2;
        private static final int PAGE_MORE = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_SEARCH:
                    return DiscoverFragment.newInstance("test","DiscoverFragment");
                case PAGE_BOOKS:
                    return BorrowFragment.newInstance("test","BorrowFragment");
                case PAGE_PEOPLE:
                    return PeopleFragment.newInstance("test","PeopleFragment");
                case PAGE_MORE:
                    return MoreFragment.newInstance("test","MoreFragment");
                default:
                    return LoginFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

}
