package com.example.wen.wenbook.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wen.wenbook.MyApplication;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.presenter.BasePresenter;
import com.example.wen.wenbook.presenter.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wen on 2017/4/12.
 */

public abstract class ProgressFragment<T extends BasePresenter> extends Fragment implements BaseView {
    private LinearLayout mProgressView;
    private FrameLayout mContentView;
    private TextView mEmptyTextView;
    private LinearLayout mEmptyView;
    private FrameLayout mRootView;
    private Unbinder mUnBinder;
    private MyApplication mApplication;

    @Inject
    T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = (FrameLayout) inflater.inflate(R.layout.fragment_progress, container, false);

        mContentView = (FrameLayout) mRootView.findViewById(R.id.fl_content_view);
        mProgressView = (LinearLayout) mRootView.findViewById(R.id.ll_progress);
        mEmptyView = (LinearLayout) mRootView.findViewById(R.id.ll_empty_view);

        mEmptyTextView = (TextView) mRootView.findViewById(R.id.tv_empty_text);


        mEmptyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyTextClick();
            }
        });

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mApplication = (MyApplication) getActivity().getApplication();
        setUpFragmentComponent(mApplication.getAppComponent());

        setRealContentView();

        init();
    }

    /**
     * 重写该方法实现emptyText 点击事件，如点击后重新加载数据
     */
    public void onEmptyTextClick(){

    }

    protected  void setRealContentView(){
        View view = LayoutInflater.from(getActivity()).inflate(setLayoutId(),mContentView,true);
        mUnBinder = ButterKnife.bind(this,view);
    }

    public void showProgressView(){
        showView(R.id.ll_progress);
    }

    public void showContentView(){
        showView(R.id.fl_content_view);
    }

    public void showEmptyView(){
        showView(R.id.ll_empty_view);
    }

    private void showView(int id){
        for (int i = 0;i<  mRootView.getChildCount();i++){
            if (mRootView.getChildAt(i).getId() == id){
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            }else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    public void showEmptyView(String emptyText){
        showEmptyView();
        mEmptyTextView.setText(emptyText);
    }

    public void showEmptyView(@StringRes int resId){
        showEmptyView();
        mEmptyTextView.setText(resId);
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void showError(String msg) {
        showEmptyView(msg);
    }

    @Override
    public void dismissLoading() {
        showContentView();
    }

    public abstract int setLayoutId();

    public abstract void setUpFragmentComponent(AppComponent appComponent);

    public abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY){
            mUnBinder.unbind();
        }
    }
}
