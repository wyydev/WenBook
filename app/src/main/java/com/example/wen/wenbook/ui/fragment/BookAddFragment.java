package com.example.wen.wenbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.ui.activity.BookInfoActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wen on 2017/3/23.
 */

public class BookAddFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    private Book mBook;

    public static BookAddFragment newInstance(Book book) {

        Bundle args = new Bundle();
        args.putSerializable("book",book);
        BookAddFragment fragment = new BookAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_info, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null){
           mBook = (Book) getArguments().getSerializable("book");
        }

        // 设置保存按钮监听器
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook(mBook);
            }
        });

        initViewPager();


        return view;
    }

    private void initViewPager() {

        final List<Fragment> fragments = new ArrayList<>(2);

        // 基本信息 Fragment
        fragments.add(BookInfoFragment.newInstance(mBook));

        // 图书简介 Fragment
        fragments.add(BookIntroFragment.newInstance(mBook));

        final List<String> titles = new ArrayList<>(2);
        titles.add("基本信息");
        titles.add("图书简介");

        // PagerAdapter
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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
            Toast.makeText(getActivity(), "你已经添加过了哦～", Toast.LENGTH_SHORT).show();
        } else {
            if (book.save()) {
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("id", book.getId());
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
