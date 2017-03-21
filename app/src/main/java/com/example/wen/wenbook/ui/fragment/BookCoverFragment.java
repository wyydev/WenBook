package com.example.wen.wenbook.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wen.wenbook.R;

/**
 * Created by wen on 2017/3/20.
 */

public class BookCoverFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    int setLayoutId() {
        return R.layout.fragment_book_cover;
    }

    @Override
    void init() {

    }
}
