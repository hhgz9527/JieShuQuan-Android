package com.thoughtworks.jieshuquan_android.activity.main.borrow;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FindCallback;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.activity.main.OnFragmentInteractionListener;
import com.thoughtworks.jieshuquan_android.adapter.BooksAdapter;
import com.thoughtworks.jieshuquan_android.model.Book;
import com.thoughtworks.jieshuquan_android.service.BookService;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BorrowFragment extends Fragment {

    @InjectView(R.id.gridview)
    GridView mGridView;

    private OnFragmentInteractionListener mListener;
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
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow, container, false);
        ButterKnife.inject(this, view);

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
        return view;
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
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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
                    mBorrowBooksAdapter.setBookList(list);
                    mBorrowBooksAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}

