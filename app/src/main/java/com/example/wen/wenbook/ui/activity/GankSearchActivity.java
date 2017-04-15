package com.example.wen.wenbook.ui.activity;


import android.content.Intent;
import android.os.Bundle;
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
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Gank;
import com.example.wen.wenbook.bean.SearchGank;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.di.component.DaggerSearchGankComponent;
import com.example.wen.wenbook.di.module.SearchGankModule;
import com.example.wen.wenbook.presenter.contract.SearchGankContract;
import com.example.wen.wenbook.presenter.impl.SearchGankPresenter;
import com.example.wen.wenbook.ui.adapter.SearchGankAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GankSearchActivity extends BaseActivity<SearchGankPresenter> implements SearchGankContract.SearchGankView , BaseQuickAdapter.RequestLoadMoreListener{


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.ib_search)
    ImageButton mIbSearch;
    @BindView(R.id.av_loading_indicator_view)
    AVLoadingIndicatorView mAvLoadingIndicatorView;
    @BindView(R.id.loadView)
    RelativeLayout mLoadView;
    @BindView(R.id.error_text_view)
    TextView mErrorTextView;
    @BindView(R.id.ErrorView)
    RelativeLayout mErrorView;
    @BindView(R.id.fragment_search_book_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_search_book_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String searchContent = "";
    public int page=1;

    private SearchGankAdapter mSearchGankAdapter;

    // 干货每次请求条数
    private static String requestCount = "20";

    private static int lastCurrentCount = 0; //记录加载最后一次之前的总数

    // RecyclerView 线性布局管理器
    private LinearLayoutManager manager;
    private boolean isLast;

    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        mIbSearch.setImageDrawable(new IconicsDrawable(this, WenFont.Icon.wen_search).colorRes(R.color.white));

        setSupportActionBar(mToolbar);
        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //标题
        mToolbar.setTitle("搜索干货");
        mEtSearch.setHint("输入要搜索的干货");

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


        mSwipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_red, R.color.google_green, R.color.google_yellow);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });


        mSearchGankAdapter = new SearchGankAdapter();
        mSearchGankAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mSearchGankAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); //开启动画
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);


        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

               SearchGank searchGank = mSearchGankAdapter.getData().get(position);
                Gank gank = new Gank(searchGank.getType());
                gank.desc = searchGank.getDesc();
                gank.url = searchGank.getUrl();
                gank.who = searchGank.getWho();
                gank.publishedAt = searchGank.getPublishedAt();
                Intent intent = new Intent(GankSearchActivity.this, WebViewActivity.class);
                intent.putExtra("gank",gank);
                GankSearchActivity.this.startActivity(intent);

            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {
        DaggerSearchGankComponent.builder().appComponent(appComponent).searchGankModule(new SearchGankModule(this)).build().inject(this);
    }

    @OnClick(R.id.ib_search)
    public void clickToSearch() {
        // 关闭软键盘
        closeKeyboard();

        getData();


    }

    private void getData() {


        if (!mEtSearch.getText().toString().trim().isEmpty()) {

            searchContent = mEtSearch.getText().toString().trim().replace(" ", "\b");

            mSearchGankAdapter.getData().clear();
            mSearchGankAdapter.notifyDataSetChanged();

            page = 1;
            mPresenter.getSearchGank(searchContent,requestCount,page);

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
                .hideSoftInputFromWindow(GankSearchActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }



    @Override
    public void showSearchGank(List<SearchGank> searchGankList, boolean isLast) {

        this.isLast=isLast;

        //不是最后一页，允许开启加载更多
        mSearchGankAdapter.setEnableLoadMore(!this.isLast);


        if(page == 1){
            mSearchGankAdapter.addData(searchGankList);
            mRecyclerView.setAdapter(mSearchGankAdapter);
            lastCurrentCount = mSearchGankAdapter.getItemCount();

        }else {

            mSearchGankAdapter.addData(searchGankList);

//            mSearchGankAdapter.notifyItemRangeInserted(lastCurrentCount,searchGankList.size()); //通知数据更新 ,第二个参数是更新的条数
            mSearchGankAdapter.notifyDataSetChanged();
            if (lastCurrentCount > 0) {
                mRecyclerView.smoothScrollToPosition(lastCurrentCount - 1); //平滑的滑动到新数据的第一条
            }
            lastCurrentCount = mSearchGankAdapter.getData().size();
        }

        this.page++;




    }


    @Override
    public void onLoadMoreComplete() {
        mSearchGankAdapter.loadMoreComplete();
    }

    @Override
    public void showLoading() {
        mLoadView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        if (mLoadView != null)
        mLoadView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String errorMsg) {
        mSearchGankAdapter.loadMoreFail();
        mErrorView.setVisibility(View.VISIBLE);
        mErrorTextView.setText("出错啦，点我重试");
        mErrorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                mErrorView.setVisibility(View.GONE);
            }
        });
        Toast.makeText(this, "错误" + errorMsg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onLoadMoreRequested() {
        if (isLast){
            mSearchGankAdapter.loadMoreEnd();
        }else {
            mPresenter.getSearchGank(searchContent,requestCount,page);
        }
    }
}
