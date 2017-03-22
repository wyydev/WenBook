package com.example.wen.wenbook.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.di.component.DaggerSearchBookComponent;
import com.example.wen.wenbook.di.module.SearchBookModule;
import com.example.wen.wenbook.presenter.contract.SearchBookContract;
import com.example.wen.wenbook.presenter.impl.SearchBookPresenter;
import com.example.wen.wenbook.ui.adapter.BookSearchAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wen on 2017/3/21.
 */

public class SearchActivity extends BaseActivity<SearchBookPresenter> implements SearchBookContract.SearchView{

    public static int SEARCH_LOCAL = 0;
    public static int SEARCH_NET = 1;

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.ib_search)
    ImageButton mIbSearch;
    @BindView(R.id.av_loading_indicator_view)
    AVLoadingIndicatorView mAvLoadingIndicatorView;
    @BindView(R.id.loadView)
    RelativeLayout mLoadView;
    @BindView(R.id.search_book_recycler_view)
    RecyclerView mRecyclerView;

    private int search_type = SEARCH_LOCAL;
    private String searchBookName="";
    private BookSearchAdapter mBookSearchAdapter;

    // 图书搜索结果总条数
    static int total = 10;

    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {

        mIbSearch.setImageDrawable(new IconicsDrawable(this, WenFont.Icon.wen_search).colorRes(R.color.white));

        // 搜索类型
        search_type = getIntent().getIntExtra("search_type", SEARCH_NET);

        initActionBarAndTitle();


    }

    private void initActionBarAndTitle() {
        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

     /*   // 去掉ActionBar阴影
        getSupportActionBar().setElevation(0);*/

        // Activity标题
        setTitle(search_type == SEARCH_LOCAL ? "本地查询" : "在线搜索");
    }


    @OnClick(R.id.ib_search)
    public void clickToSearch(){
        // 关闭软键盘
        closeKeyboard();

        if (!mEtSearch.getText().toString().trim().isEmpty()) {

            searchBookName = mEtSearch.getText().toString().trim().replace(" ", "\b");

             mPresenter.serchBook(search_type,searchBookName);


        } else {
            Toast.makeText(this, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
        }

    }

    // 关闭软键盘
    private void closeKeyboard() {
        // 取消焦点
        mEtSearch.clearFocus();

        // 关闭输入法
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {
        DaggerSearchBookComponent.builder().appComponent(appComponent).searchBookModule(new SearchBookModule(this)).build().inject(this);
    }


    @Override
    public void showLoading() {
        mLoadView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        mLoadView.setVisibility(View.GONE);
    }

    @Override
    public void showResult(List<Book> bookList) {

        mBookSearchAdapter = new BookSearchAdapter();

        mBookSearchAdapter.addData(bookList);
        Toast.makeText(this, "搜索的内容大小:"+bookList.size(), Toast.LENGTH_SHORT).show();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mBookSearchAdapter);

    }

    @Override
    public void showError(String errorMsg) {
        Log.d("SearchActivity",errorMsg);
    }
}
