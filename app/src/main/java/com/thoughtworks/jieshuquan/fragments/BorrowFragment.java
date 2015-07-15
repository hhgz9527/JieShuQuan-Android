package com.thoughtworks.jieshuquan.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FindCallback;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.interfaces.MainActivityListener;
import com.thoughtworks.jieshuquan.adapter.BooksAdapter;
import com.thoughtworks.jieshuquan.converter.BookItemConverter;
import com.thoughtworks.jieshuquan.model.BookItem;
import com.thoughtworks.jieshuquan.service.BookService;
import com.thoughtworks.jieshuquan.service.model.Book;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.google.common.collect.FluentIterable.from;

public class BorrowFragment extends Fragment {

    @InjectView(R.id.books_grid_view)
    GridView mGridView;
    @InjectView(R.id.loading_view)
    ViewGroup mLoadingView;
    @InjectView(R.id.info_view)
    ViewGroup mInfoView;
    @InjectView(R.id.info_text)
    TextView mInfoText;

    @InjectView(R.id.topContainer)
    View topContainer;

    private MainActivityListener mListener;
    private BooksAdapter mBorrowBooksAdapter;

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
        mGridView.setAdapter(mBorrowBooksAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(parent.getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        showLoadingView();
    }

    private void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
        mGridView.setVisibility(View.GONE);
        mInfoView.setVisibility(View.GONE);
    }

    private void showContentView() {
        mGridView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        mInfoView.setVisibility(View.GONE);
    }

    private void showInfoView(String textInfo) {
        if (!TextUtils.isEmpty(textInfo)) {
            mInfoText.setText(textInfo);
        }
        mInfoView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        mGridView.setVisibility(View.GONE);
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
            mListener = (MainActivityListener) activity;
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
                    final BookItemConverter converter = new BookItemConverter();
                    ImmutableList<BookItem> bookItemList = from(list).transform(new Function<Book, BookItem>() {
                        @Override
                        public BookItem apply(Book input) {
                            return converter.convert(input);
                        }
                    }).toList();
                    mBorrowBooksAdapter.setBookList(bookItemList);
                    mBorrowBooksAdapter.notifyDataSetChanged();
                } else if (list.size() == 0) {
                    showInfoView(getString(R.string.msg_default_empty));
                } else if (e != null) {
                    showInfoView(e.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.topContainer)
    public void onTopContainerClicked(View view) {
        Log.d(this.getClass().getName(), "onTopContainer");
        mListener.showPopularBook();
    }

}

