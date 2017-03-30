package com.example.wen.wenbook.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.BookNoteBean;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.ui.activity.BookNoteActivity;
import com.example.wen.wenbook.ui.adapter.BookNoteListAdapter;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by wen on 2017/3/25.
 */

public class BookNoteFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_add_note)
    Button mAddNoteButton;
    private Unbinder unbinder;
    private String isbn;
    private List<BookNoteBean> mBookNoteBeen;

    private BookNoteListAdapter mBookNoteListAdapter;

    private ImageView mIvEmptyIcon;
    private TextView mTvEmptyText;


    public static BookNoteFragment newInstance(String isbn) {

        Bundle args = new Bundle();
        args.putString("isbn", isbn);
        BookNoteFragment fragment = new BookNoteFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            isbn = arguments.getString("isbn");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_note, container, false);
        unbinder = ButterKnife.bind(this, view);

        mAddNoteButton.setCompoundDrawables(null,new IconicsDrawable(getActivity(), WenFont.Icon.wen_add).colorRes(R.color.blue_btn_bg_color),null,null);

        mAddNoteButton.setOnClickListener(this);

        mBookNoteListAdapter = new BookNoteListAdapter();


        //emptyView
        View emptyView = View.inflate(getActivity(),R.layout.empty_view,null);
        mIvEmptyIcon = (ImageView) emptyView.findViewById(R.id.iv_empty_icon);
        mTvEmptyText = (TextView) emptyView.findViewById(R.id.tv_empty_text);

        mIvEmptyIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(WenFont.Icon.wen_write).colorRes(R.color.grid_empty_icon).sizeDp(40));
        mTvEmptyText.setText("暂无笔记");

        mBookNoteListAdapter.setEmptyView(emptyView);

        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            if (isVisibleToUser) {
                //可见时才获取数据
                fetchData();

                mBookNoteListAdapter.notifyDataSetChanged();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 到数据库加载数据
     */
    private void fetchData() {

        mBookNoteBeen = DataSupport.where("isbn=?", isbn).find(BookNoteBean.class);

        //添加新数据
        mBookNoteListAdapter.setNewData(mBookNoteBeen);

     /*   mBookNoteListAdapter.notifyDataSetChanged();*/
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fetchData();

        mBookNoteListAdapter.notifyDataSetChanged();



        //item点击事件
        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = BookNoteActivity.newIntent(getActivity(),mBookNoteListAdapter.getData().get(position).getNoteId().toString(),isbn);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                //长按删除
                String []a = new String[]{"删除选中","删除全部"};

                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("删除笔记")
                        .setIcon(new IconicsDrawable(getActivity()).icon(WenFont.Icon.wen_write))
                        .setSingleChoiceItems(a, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        delete(mBookNoteListAdapter.getData().get(position).getNoteId());
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


        mRecyclerView.setAdapter(mBookNoteListAdapter);

        super.onViewCreated(view, savedInstanceState);
    }


    private void deleteAll() {
        // 删除全部
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("确定删除全部笔记吗")
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
                                DataSupport.deleteAll(BookNoteBean.class,"isbn = ?",isbn);
                                fetchData();
                                mBookNoteListAdapter.notifyDataSetChanged();
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

    private void delete(final String id) {
        // 删除选中
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("确定删除这条笔记吗")
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
                                DataSupport.deleteAll(BookNoteBean.class,"isbn = ? and mNoteId = ?",isbn,id.toString());
                                fetchData();
                                mBookNoteListAdapter.notifyDataSetChanged();
                            }
                        }, 800);

                        sDialog
                                .setTitleText("删除成功")
                                .setContentText("该笔记已被成功删除。")
                                .setConfirmText("确定")
                                .showCancelButton(false)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }



    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
        fetchData();
        mBookNoteListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }

    @Override
    public void onClick(View v) {
        //添加笔记
        Intent intent = BookNoteActivity.newIntent(getActivity(),UUID.randomUUID().toString(),isbn);
        startActivity(intent);
    }
}
