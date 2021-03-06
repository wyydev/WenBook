package com.example.wen.wenbook.ui.activity;

import android.content.SharedPreferences;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wen.wenbook.MyApplication;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.presenter.BasePresenter;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected Unbinder mUnBinder;
    protected MyApplication mMyApplication;
    protected SharedPreferences mSharedPreferences;
    protected SharedPreferences.Editor mEditor;

    @Inject
    T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        super.onCreate(savedInstanceState);

        setContentView(setLayoutId());

        mMyApplication = (MyApplication) getApplication();

        setUpActivityComponent(mMyApplication.getAppComponent());

        mUnBinder = ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);

        mEditor = mSharedPreferences.edit();

        init();
    }

    public abstract int setLayoutId();

    public abstract void init();

    public abstract void setUpActivityComponent(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY){
            mUnBinder.unbind();
        }
    }
}
