package com.example.wen.wenbook.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wen on 2017/3/20.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder mUnBinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(),container,false);
        mUnBinder =  ButterKnife.bind(this, view);
        init();
        return view;
    }



    abstract int setLayoutId();
    abstract void init();
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY){
            mUnBinder.unbind();
        }
    }
}
