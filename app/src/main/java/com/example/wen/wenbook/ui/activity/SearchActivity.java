package com.example.wen.wenbook.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
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

public class SearchActivity extends BaseActivity<SearchBookPresenter> implements SearchBookContract.SearchView, BaseQuickAdapter.RequestLoadMoreListener {

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
    @BindView(R.id.fragment_search_book_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_search_book_swipe)
    SwipeRefreshLayout mSwipeRefrshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    private int search_type = SEARCH_LOCAL;

    private String searchBookName = "";

    private BookSearchAdapter mBookSearchAdapter;

    // 图书搜索结果总条数
    private static int total = 10;

    // RecyclerView 线性布局管理器
    private LinearLayoutManager manager;


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

        mBookSearchAdapter = new BookSearchAdapter(this);
        mBookSearchAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mBookSearchAdapter.setOnLoadMoreListener(this, mRecyclerView);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);


        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    getData();
                    return true;
                }
                return false;
            }
        });



        mSwipeRefrshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_red, R.color.google_green, R.color.google_yellow);
        mSwipeRefrshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

    }

    private void initActionBarAndTitle() {
        setSupportActionBar(mToolbar);
        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

     /*   // 去掉ActionBar阴影
        getSupportActionBar().setElevation(0);*/

        // Activity标题
        mToolbar.setTitle(search_type == SEARCH_LOCAL ? "本地查询" : "在线搜索");
    }


    @OnClick(R.id.ib_search)
    public void clickToSearch() {
        // 关闭软键盘
        closeKeyboard();

        getData();


    }

    private void getData() {


        if (!mEtSearch.getText().toString().trim().isEmpty()) {

            searchBookName = mEtSearch.getText().toString().trim().replace(" ", "\b");

            mBookSearchAdapter.getData().clear();
            mBookSearchAdapter.notifyDataSetChanged();

            mPresenter.searchBook(search_type, searchBookName, mBookSearchAdapter.getItemCount());

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
        mSwipeRefrshLayout.setRefreshing(false);
    }

    @Override
    public void showResult(final List<Book> bookList, int totalCount) {

        total = totalCount;
      //  Log.d("SearchActivity", "total:" + total);

       // Toast.makeText(this, "这次获取了" + bookList.size(), Toast.LENGTH_SHORT).show();

        if (total == 0) {
            Toast.makeText(this, "找不到图书", Toast.LENGTH_SHORT).show();
        }

        mBookSearchAdapter.addData(bookList);


        //是否允许开启上拉加载更多
        mBookSearchAdapter.setEnableLoadMore(mBookSearchAdapter.getItemCount() < totalCount ? true : false);


        mRecyclerView.setAdapter(mBookSearchAdapter);


    }

    @Override
    public void onLoadMoreComplete() {
        mBookSearchAdapter.loadMoreComplete();
    }

    @Override
    public void showError(String errorMsg) {
        mBookSearchAdapter.loadMoreFail();
        Toast.makeText(this, "错误" + errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMoreRequested() {

        if (mBookSearchAdapter.getItemCount() < total) {
            mPresenter.searchBook(search_type, searchBookName, mBookSearchAdapter.getItemCount());
        } else {
            mBookSearchAdapter.loadMoreEnd();
        }

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
