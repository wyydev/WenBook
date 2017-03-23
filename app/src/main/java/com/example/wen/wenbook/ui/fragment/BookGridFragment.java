package com.example.wen.wenbook.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.ui.activity.BookInfoActivity;
import com.example.wen.wenbook.ui.adapter.BookGridAdapter;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            if (isVisibleToUser) {
                fetchData();
                bookGridAdapter.notifyDataSetChanged();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
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

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        fetchData();


        bookGridAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));



        //Item点击
        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("id", (int) bookGridAdapter.getItemId(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, final View view, final int position) {

                String []a = new String[]{"删除选中","删除全部"};

                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("删除图书")
                        .setIcon(new IconicsDrawable(getActivity()).icon(WenFont.Icon.wen_book))
                        .setSingleChoiceItems(a, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        delete(position);
                                        break;
                                    case 1:
                                        deleteAll();
                                        break;

                                }

                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).create();

                alertDialog.show();
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });




        mRecyclerView.setAdapter(bookGridAdapter);

        super.onViewCreated(view, savedInstanceState);

    }

    private void deleteAll() {
        // 删除全部
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("确定删除全部图书吗")
                .setContentText("删除后将无法恢复。")
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // 刷新数据
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DataSupport.deleteAll(Book.class);
                                fetchData();
                                bookGridAdapter.notifyDataSetChanged();
                            }
                        }, 1000);
                        sDialog
                                .setTitleText("删除成功")
                                .setContentText("全部图书已被成功删除。")
                                .setConfirmText("确定")
                                .showCancelButton(false)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    private void delete(final int id) {
        // 删除选中
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("确定删除这本图书吗")
                .setContentText("删除后将无法恢复。")
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // 刷新数据
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DataSupport.delete(Book.class, bookGridAdapter.getItemId(id));
                                fetchData();
                                bookGridAdapter.notifyDataSetChanged();
                            }
                        }, 800);

                        sDialog
                                .setTitleText("删除成功")
                                .setContentText("该本图书已被成功删除。")
                                .setConfirmText("确定")
                                .showCancelButton(false)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    public void fetchData() {
        Log.d("wen", type + "GridFragment.fetchData");
        if (type == TYPE_FAVORITE) {
            //查找数据库中的所有数据
            List<Book> bookList = DataSupport.where("favourite = ?", "1").order("id desc").find(Book.class);

            //添加新数据
            bookGridAdapter.setNewData(bookList);

        } else {
            //查找数据库中的所有数据
            List<Book> bookList = DataSupport.order("id desc").find(Book.class);

            //添加新数据
            bookGridAdapter.setNewData(bookList);
        }
    }





    @Override
    public void onResume() {
        super.onResume();
        Log.i("wen", type + "GridFragment.onResume");
        fetchData();
        bookGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
