package com.example.wen.wenbook.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.TagItem;
import com.example.wen.wenbook.ui.adapter.BookIntroAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wen on 2017/3/23.
 */

public class BookIntroFragment extends BaseFragment {

    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Book book;
    private List<TagItem> data;

    public static BookIntroFragment newInstance(int bookId) {
        BookIntroFragment fragment = new BookIntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookIntroFragment newInstance(Book book) {
        BookIntroFragment fragment = new BookIntroFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //根据传入的参数构建数据book
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_BOOK_ID)) {
                book = DataSupport.find(Book.class, getArguments().getInt(ARG_BOOK_ID));
            } else if (getArguments().containsKey(ARG_BOOK)) {
                book = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }

    }

    @Override
    int setLayoutId() {
        return R.layout.fragment_book_info_item_list;
    }

    @Override
    void init() {


        // 数据
        data = new ArrayList<>();
        if (!book.getSummary().isEmpty()) data.add(new TagItem("内容简介", book.getSummary()));
        if (!book.getAuthor_intro().isEmpty())
            data.add(new TagItem("作者简介", book.getAuthor_intro()));
        if (!book.getCatalog().isEmpty()) data.add(new TagItem("图书目录", book.getCatalog()));

        // 列表适配器
        BookIntroAdapter bookIntroAdapter = new BookIntroAdapter();
        bookIntroAdapter.addData(data);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(bookIntroAdapter);

    }


}
