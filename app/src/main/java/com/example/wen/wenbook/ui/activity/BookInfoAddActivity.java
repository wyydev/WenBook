package com.example.wen.wenbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.di.component.DaggerAddBookComponent;
import com.example.wen.wenbook.di.module.AddBookModule;
import com.example.wen.wenbook.presenter.contract.AddBookContract;
import com.example.wen.wenbook.presenter.impl.AddBookPresenter;
import com.example.wen.wenbook.ui.fragment.BookInfoFragment;
import com.example.wen.wenbook.ui.fragment.BookIntroFragment;
import com.wang.avi.AVLoadingIndicatorView;

import org.litepal.crud.DataSupport;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wen on 2017/3/22.
 */

public class BookInfoAddActivity extends BaseActivity<AddBookPresenter> implements AddBookContract.AddBookView{


    @BindView(R.id.av_loading_indicator_view)
    AVLoadingIndicatorView mAvLoadingIndicatorView;
    @BindView(R.id.loadView)
    RelativeLayout mLoadView;
    @BindView(R.id.error_text_view)
    TextView mErrorTextView;
    @BindView(R.id.errorView)
    RelativeLayout mErrorView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.contentPanel)
    LinearLayout mContentPanel;

    @Override
    public int setLayoutId() {
        return R.layout.activity_book_info_add;
    }

    @Override
    public void init() {

        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Activity标题
        setTitle("图书详情");

        // 传入的ISBN
        String isbn = getIntent().getStringExtra("ISBN");

        if (!TextUtils.isEmpty(isbn)){
            mPresenter.searchBookFromIsbn(isbn);
        }


    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {
        DaggerAddBookComponent.builder().appComponent(appComponent).addBookModule(new AddBookModule(this)).build().inject(this);
    }



    // 保存图书
    public void saveBook(Book book) {
        Boolean isAdded = false;

        // 遍历当前数据库中所有的书籍，用来判断是否已经添加过这本书
        List<Book> books = DataSupport.findAll(Book.class);
        for (int i = 0; i < books.size(); i++) {
            Book book_db = books.get(i);
            if ((book_db.getAuthor() + book_db.getTitle()).equals(book.getAuthor() + book.getTitle())) {
                isAdded = true;
                break;
            } else {
                isAdded = false;
            }
        }

        if (isAdded) {
            Toast.makeText(this, "你已经添加过了哦～", Toast.LENGTH_SHORT).show();
        } else {
            if (book.save()) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BookInfoActivity.class);
                intent.putExtra("id", book.getId());
                startActivity(intent);
            } else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showBook(final Book book) {
        // 设置保存按钮监听器
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook(book);
            }
        });

        final List<Fragment> fragments = new ArrayList<>(2);

        // 基本信息 Fragment
        fragments.add(BookInfoFragment.newInstance(book));

        // 图书简介 Fragment
        fragments.add(BookIntroFragment.newInstance(book));

        final List<String> titles = new ArrayList<>(2);
        titles.add("基本信息");
        titles.add("图书简介");

        // PagerAdapter
       FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

           @Override
           public CharSequence getPageTitle(int position) {
               return titles.get(position);
           }
       };

        // 设置数据适配器
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.setupWithViewPager(mViewPager);

        // Activity标题
        setTitle(book.getTitle());

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
    public void showError(String errorMsg) {
        Toast.makeText(this, "图书不存在或网络连接错误："+errorMsg, Toast.LENGTH_SHORT).show();
        mErrorTextView.setText("图书不存在或网络连接错误："+errorMsg);
        mErrorTextView.setVisibility(View.VISIBLE);
    }



}
