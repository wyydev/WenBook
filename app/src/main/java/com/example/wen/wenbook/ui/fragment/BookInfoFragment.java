package com.example.wen.wenbook.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.TagItem;
import com.example.wen.wenbook.ui.adapter.BookInfoAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wen on 2017/2/19.
 */
public class BookInfoFragment extends BaseFragment {

    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Book book;
    private List<TagItem> data;

    public static BookInfoFragment newInstance(int bookId) {
        BookInfoFragment fragment = new BookInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookInfoFragment newInstance(Book book) {
        BookInfoFragment fragment = new BookInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int setLayoutId() {
        return R.layout.fragment_book_info_item_list;
    }

    @Override
    void init() {
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_BOOK_ID)) {
                book = DataSupport.find(Book.class, getArguments().getInt(ARG_BOOK_ID));
            } else if (getArguments().containsKey(ARG_BOOK)) {
                book = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }


        // 数据
        data = new ArrayList<>();
        data.add(new TagItem("作者", book.getAuthor()));
        data.add(new TagItem("出版社", book.getPublisher()));
        if (!book.getOrigin_title().isEmpty()) data.add(new TagItem("原作名", book.getOrigin_title()));
        if (!book.getTranslator().isEmpty()) data.add(new TagItem("译者", book.getTranslator()));
        data.add(new TagItem("出版年", book.getPubdate()));
        data.add(new TagItem("页数", book.getPages()));
        data.add(new TagItem("定价", book.getPrice()));
        if (!book.getBinding().isEmpty()) data.add(new TagItem("装帧", book.getBinding()));
        data.add(new TagItem("ISBN", book.getIsbn13()));

        // 列表适配器
        BookInfoAdapter lvBaseAdapter = new BookInfoAdapter(data);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 列表
        mRecyclerView.setAdapter(lvBaseAdapter);

    }
}
