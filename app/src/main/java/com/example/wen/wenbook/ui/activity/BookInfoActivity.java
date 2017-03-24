package com.example.wen.wenbook.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.ui.fragment.BookAddFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.wang.avi.AVLoadingIndicatorView;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by wen on 2017/3/22.
 */

public class BookInfoActivity extends AppCompatActivity {

    @BindView(R.id.book_cover_bg)
    ImageView mBookCoverBg;
    @BindView(R.id.book_cover)
    ImageView mBookCover;
    @BindView(R.id.tv_cover_rate)
    TextView mTvCoverRate;
    @BindView(R.id.rb_cover_rate)
    RatingBar mRbCoverRate;
    @BindView(R.id.book_rate)
    LinearLayout mBookRate;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appBar)
    AppBarLayout mAppBar;
    @BindView(R.id.av_loading_indicator_view)
    AVLoadingIndicatorView mAvLoadingIndicatorView;
    @BindView(R.id.error_text_view)
    TextView mErrorTextView;
    @BindView(R.id.contentPanel)
    FrameLayout mContentPanel;


    // Book
    private Book book;

    // 收藏按钮图片
    private int iconFavorite[] = {R.drawable.ic_favorite_border_white_24dp, R.drawable.ic_favorite_white_24dp};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_activity_book_add);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        setSupportActionBar(mToolbar);
        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mCollapsingToolbar.setTitle("图书详情");

        // 图书ID
        int bookId = getIntent().getIntExtra("id", -1);

        // 图书Obj
        book = DataSupport.find(Book.class, bookId);


        initBookCover();


        initFragment();

    }

    private void initFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.contentPanel, BookAddFragment.newInstance(book,BookAddFragment.WITH_NOTE));

        fragmentTransaction.commit();

    }

    private void initBookCover() {
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
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                return true;
            case R.id.action_favorite:
                book.setFavourite(!book.isFavourite());
                book.save();
                invalidateOptionsMenu();
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(book.isFavourite() ? "收藏成功" : "取消收藏")
                        .setContentText(book.isFavourite() ? "图书已收藏" : "图书已取消收藏")
                        .setConfirmText("确定")
                        .show();
                return true;
            case R.id.action_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(book.getAlt()));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.book_info_menu, menu);
        //返回true才会显示overflow按钮
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_favorite);
        menuItem.setIcon(iconFavorite[book.isFavourite() ? 1 : 0]);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
