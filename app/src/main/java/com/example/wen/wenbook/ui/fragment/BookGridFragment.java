package com.example.wen.wenbook.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.ui.adapter.BookGridAdapter;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import butterknife.BindView;

/**
 * Created by wen on 2017/3/21.
 */

public class BookGridFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ImageView mIvEmptyIcon;

    private  TextView mTvEmptyText;


    private int type;
    public static final int TYPE_ALL = 1;
    public static final int TYPE_FAVORITE = 2;

    private BookGridAdapter bookGridAdapter; // 数据适配器

    public static BookGridFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        BookGridFragment fragment = new BookGridFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    int setLayoutId() {
        return R.layout.fragment_book_grid;
    }

    @Override
    void init() {
        Bundle arguments = getArguments();
        if (arguments!=null){
            type = arguments.getInt("type",TYPE_ALL);
        }



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        View emptyView = View.inflate(getActivity(),R.layout.empty_view,null);
        mIvEmptyIcon = (ImageView) emptyView.findViewById(R.id.iv_empty_icon);
        mTvEmptyText = (TextView) emptyView.findViewById(R.id.tv_empty_text);
        // EmptyView
        if (type == TYPE_FAVORITE) {
            mIvEmptyIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(WenFont.Icon.wen_love_fill).colorRes(R.color.grid_empty_icon).sizeDp(40));
            mTvEmptyText.setText("暂无收藏");
        } else {
            mIvEmptyIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(WenFont.Icon.wen_book).colorRes(R.color.grid_empty_icon).sizeDp(48));
            mTvEmptyText.setText("暂无图书");
        }

        bookGridAdapter = new BookGridAdapter();
        bookGridAdapter.setEmptyView(emptyView);

        fetchData();

        bookGridAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.setAdapter(bookGridAdapter);
        super.onViewCreated(view, savedInstanceState);

    }

    public void fetchData() {
        Log.d("wen", type + "GridFragment.fetchData");
        if (type == TYPE_FAVORITE) {
            bookGridAdapter.addData(DataSupport.where("favourite = ?", "1").order("id desc").find(Book.class));
        } else {
            bookGridAdapter.addData(DataSupport.order("id desc").find(Book.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("wen", type + "GridFragment.onResume");
        fetchData();
        bookGridAdapter.notifyDataSetChanged();
    }

}
