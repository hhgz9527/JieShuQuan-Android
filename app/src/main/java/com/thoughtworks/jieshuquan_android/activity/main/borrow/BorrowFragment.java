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

import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.activity.main.OnFragmentInteractionListener;
import com.thoughtworks.jieshuquan_android.adapter.BorrowBooksAdapter;

import java.util.Random;

public class BorrowFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

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
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_borrow, container, false);

        GridView gridview = (GridView) inflate.findViewById(R.id.gridview);
        final BorrowBooksAdapter borrowBooksAdapter = new BorrowBooksAdapter(this.getActivity());
        gridview.setAdapter(borrowBooksAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(parent.getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount>=totalItemCount){
                    Integer[] books = {R.drawable.s1495029,R.drawable.s1106934,R.drawable.s1086045};
                    Random random = new Random();
                    int nextInt = random.nextInt(3);
                    borrowBooksAdapter.mThumbIds.add(books[nextInt]);
                    borrowBooksAdapter.notifyDataSetChanged();
                }
            }
        });
        return inflate;
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

}

