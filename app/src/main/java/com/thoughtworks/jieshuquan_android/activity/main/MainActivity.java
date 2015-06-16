package com.thoughtworks.jieshuquan_android.activity.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.activity.main.add.AddBookToLibraryActivity;
import com.thoughtworks.jieshuquan_android.activity.main.add.ScannerActivity;
import com.thoughtworks.jieshuquan_android.activity.main.borrow.BorrowFragment;
import com.thoughtworks.jieshuquan_android.activity.main.discover.DiscoverFragment;
import com.thoughtworks.jieshuquan_android.activity.main.mates.PeopleFragment;
import com.thoughtworks.jieshuquan_android.activity.main.mine.MineFragment;
import com.thoughtworks.jieshuquan_android.view.TabBar;
import com.thoughtworks.jieshuquan_android.activity.login.LoginActivity;
import com.thoughtworks.jieshuquan_android.activity.login.LoginFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

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

        if (AVUser.getCurrentUser() == null) {
            //need login
            Intent showLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(showLoginIntent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 100 && data.getStringExtra("ISBN").length() > 0) {
           // new AlertDialog.Builder(this).setTitle("ISBN").setMessage("the ISBN number is " + data.getStringExtra("ISBN")).setPositiveButton("sure", null).show();
            this.showAddBookActivity(data.getStringExtra("ISBN"));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
                    return DiscoverFragment.newInstance("test", "DiscoverFragment");

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
            }
            return true;
        }
    };

    private void startScanner() {
        Intent startScanner = new Intent(this, ScannerActivity.class);
        startActivityForResult(startScanner, 100);
    }

    private void showAddBookActivity(String isbn) {
        Intent showAddBookIntent = new Intent(MainActivity.this, AddBookToLibraryActivity.class);
        showAddBookIntent.putExtra("ISBN",isbn);
        startActivity(showAddBookIntent);
    }

}
