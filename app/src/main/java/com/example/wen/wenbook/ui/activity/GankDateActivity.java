package com.example.wen.wenbook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.GankDate;
import com.example.wen.wenbook.common.rxbus.RxBus;
import com.example.wen.wenbook.ui.adapter.GankDateAdapter;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GankDateActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private GankDateAdapter mGankDateAdapter;
    private  GankDate gank_date;

    private Unbinder mUnBinder;

    public static Intent createIntentWithDateList(Context viewContext, GankDate gankDate){
        Intent intent = new Intent(viewContext,GankDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("gank_date",gankDate);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_recyclerview);
        mUnBinder = ButterKnife.bind(this);

        init();
    }

    private void init() {

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null ){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("历史干货日期");
        }

        Intent intent = getIntent();
        if (intent != null){
            Bundle extras = intent.getExtras();
            gank_date = (GankDate) extras.getSerializable("gank_date");
        }
        mGankDateAdapter = new GankDateAdapter();

        mGankDateAdapter.addData(gank_date.results);



        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //发送date数据给DiscoverFragment

                RxBus.getInstance().post(new DateEvent("001",gank_date.results.get(position)));

                GankDateActivity.this.finish();
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

        mRecyclerView.setAdapter(mGankDateAdapter);
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
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY){
            mUnBinder.unbind();
        }
    }

    public class DateEvent{
        private String id;
        private String date;

        public DateEvent(String id, String date) {
            this.id = id;
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
