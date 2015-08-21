package com.thoughtworks.jieshuquan.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FindCallback;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.DetailActivity;
import com.thoughtworks.jieshuquan.adapter.BooksAdapter;
import com.thoughtworks.jieshuquan.service.BookService;
import com.thoughtworks.jieshuquan.service.model.Book;
import com.thoughtworks.jieshuquan.service.model.BookEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

import static com.google.common.collect.FluentIterable.from;

public class BorrowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentCallbacks mListener;
    private List<Book> mBookList;

    private BooksAdapter mBorrowBooksAdapter;

    @InjectView(R.id.books_grid_view)
    GridViewWithHeaderAndFooter booksGridView;

    @InjectView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static BorrowFragment newInstance() {
        BorrowFragment fragment = new BorrowFragment();
        return fragment;
    }

    public BorrowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow, container, false);
        ButterKnife.inject(this, view);

        initViews();
        return view;
    }

    private void initViews() {
        mBorrowBooksAdapter = new BooksAdapter(this.getActivity());

        booksGridView.addHeaderView(getTopBooksView());
        booksGridView.setAdapter(mBorrowBooksAdapter);
        booksGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Book book = mBookList.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.KBOOK_ID, book.getObjectId());
                intent.putExtra(Constants.KBOOK_TYPE,Constants.K_TYPE_BOOK_LIST);
                startActivity(intent);
            }
        });
        booksGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
//                    Integer[] books = {R.drawable.s1495029,R.drawable.s1106934,R.drawable.s1086045};
//                    Random random = new Random();
//                    int nextInt = random.nextInt(3);
//                    mBorrowBooksAdapter.mThumbIds.add(books[nextInt]);
//                    mBorrowBooksAdapter.notifyDataSetChanged();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        showLoadingView();
    }

    private View getTopBooksView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.top_books_layout, null);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showPopularBook();
            }
        });
        return headerView;
    }

    private void showLoadingView() {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void showContentView() {

        swipeRefreshLayout.setRefreshing(false);
    }

    private void showInfoView(String textInfo) {
//        if (!TextUtils.isEmpty(textInfo)) {
//            mInfoText.setText(textInfo);
//        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_scan, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (FragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainActivityListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initData() {
        BookService.getInstance().fetchAllBooks(0, new FindCallback<Book>() {
            @Override
            public void done(List<Book> list, AVException e) {
                if (e == null && list.size() > 0) {
                    showContentView();
                    mBookList = list;
                    mBorrowBooksAdapter.setBookList(list);
                    mBorrowBooksAdapter.notifyDataSetChanged();
                } else if (list != null && list.size() == 0) {
                    showInfoView(getString(R.string.msg_default_empty));
                } else if (e != null) {
                    showInfoView(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onRefresh() {

        initData();
    }
}

