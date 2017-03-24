package com.example.wen.wenbook.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.di.component.DaggerAddBookComponent;
import com.example.wen.wenbook.di.module.AddBookModule;
import com.example.wen.wenbook.presenter.contract.AddBookContract;
import com.example.wen.wenbook.presenter.impl.AddBookPresenter;
import com.example.wen.wenbook.ui.fragment.BookAddFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class BookInfoAddActivity2 extends BaseActivity<AddBookPresenter> implements AddBookContract.AddBookView {


    @BindView(R.id.book_cover_bg)
    ImageView mBookCoverBg;
    @BindView(R.id.tv_cover_rate)
    TextView mTvCoverRate;
    @BindView(R.id.rb_cover_rate)
    RatingBar mRbCoverRate;
    @BindView(R.id.book_rate)
    LinearLayout mBookRate;
    @BindView(R.id.book_cover)
    ImageView mBookCover;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appBar)
    AppBarLayout mAppBar;
    @BindView(R.id.contentPanel)
    FrameLayout mContentPanel;
    @BindView(R.id.av_loading_indicator_view)
    AVLoadingIndicatorView mAvLoadingIndicatorView;
    @BindView(R.id.error_text_view)
    TextView mErrorTextView;

    @Override
    public int setLayoutId() {
        return R.layout.coordinator_activity_book_add;
    }

    @Override
    public void init() {

        setSupportActionBar(mToolbar);
        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCollapsingToolbar.setTitle("图书详情");

        // 传入的ISBN
        String isbn = getIntent().getStringExtra("ISBN");

        if (!TextUtils.isEmpty(isbn)) {
            mPresenter.searchBookFromIsbn(isbn);
        }

    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {
        DaggerAddBookComponent.builder().appComponent(appComponent).addBookModule(new AddBookModule(this)).build().inject2(this);
    }


    @Override
    public void showBook(final Book book) {

        // 背景
        Glide.with(this)
                .load(book.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new BlurTransformation(this, 25, 3))
                .into(mBookCoverBg);

        // 图书封面
        Glide.with(this)
                .load(book.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(this).icon(WenFont.Icon.wen_book).colorRes(R.color.boo_cover_icon))
                .error(new IconicsDrawable(this).icon(WenFont.Icon.wen_book).colorRes(R.color.boo_cover_icon))
                .into(mBookCover);

        // 图书评分
        mTvCoverRate.setText(book.getAverage());
        mRbCoverRate.setRating((Float.parseFloat(book.getAverage()) / 2));

        // Activity标题
        mCollapsingToolbar.setTitle(book.getTitle());

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.contentPanel, BookAddFragment.newInstance(book,BookAddFragment.NO_NOTE));

        fragmentTransaction.commit();


    }

    @Override
    public void showLoading() {
        mAvLoadingIndicatorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        mAvLoadingIndicatorView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMsg) {
        mErrorTextView.setText("图书不存在或网络连接错误");
        mErrorTextView.setVisibility(View.VISIBLE);
        Toast.makeText(this, "图书不存在或网络连接错误：" + errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
