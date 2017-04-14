package com.example.wen.wenbook.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;


import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.GankDate;
import com.example.wen.wenbook.bean.TodayGank;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.common.rxbus.RxBus;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.di.component.DaggerTodayGankComponent;
import com.example.wen.wenbook.di.module.TodayGankModule;
import com.example.wen.wenbook.presenter.contract.TodayGankContract;
import com.example.wen.wenbook.presenter.impl.TodayGankPresenter;
import com.example.wen.wenbook.ui.activity.GankDateActivity;
import com.example.wen.wenbook.ui.adapter.TodayGankAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.Calendar;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wen on 2017/3/20.
 */

public class DiscoverFragment extends ProgressFragment<TodayGankPresenter> implements TodayGankContract.TodayGankView {


    @BindView(R.id.today_recyclerview)
    RecyclerView mTodayRecyclerview;
    @BindView(R.id.fab_date)
    FloatingActionButton mFabDate;
    @BindView(R.id.fab_search)
    FloatingActionButton mFabSearch;
    @BindView(R.id.fabmenu)
    FloatingActionMenu mFabmenu;

    private TodayGankAdapter mTodayGankAdapter;
    private Disposable mDisposable;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    public void setUpFragmentComponent(AppComponent appComponent) {
        DaggerTodayGankComponent.builder().appComponent(appComponent).todayGankModule(new TodayGankModule(this)).build().inject(this);
    }

    @Override
    public void init() {


        initFabMenu();


        //获取当天日期
        Calendar now = Calendar.getInstance();
        getGankData(now.get(Calendar.YEAR) + "", now.get(Calendar.MONTH) + 1 + "", now.get(Calendar.DAY_OF_MONTH) + "");
        mTodayRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));



    }

    @Override
    public void onResume() {
        super.onResume();
        mDisposable = RxBus.getInstance().toObserverable(GankDateActivity.DateEvent.class)
                .subscribe(new Consumer<GankDateActivity.DateEvent>() {
                    @Override
                    public void accept(GankDateActivity.DateEvent dateEvent) throws Exception {
                        if (dateEvent != null){
                            receiveDate(dateEvent.getDate());
                        }
                    }
                });
    }

    public void receiveDate(String date){
        String[] split = date.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        getGankData(year,month,day);
    }



    private void initFabMenu() {
        // 右下角浮动菜单
        mFabmenu.setClosedOnTouchOutside(true);

        //历史干货
        mFabDate.setImageDrawable(new IconicsDrawable(getActivity(), WenFont.Icon.wen_book).colorRes(R.color.white));
        mFabDate.setOnClickListener(new View.OnClickListener() {
            public static final int MIN_CLICK_DELAY_TIME = 1500;
            private long lastClickTime = 0;
            @Override
            public void onClick(View v) {
                mFabmenu.close(true);
                long currentTime = Calendar.getInstance().getTimeInMillis();
                //防止短时间内重复点击
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime;
                    mPresenter.gankDate();
                }

            }
        });

        //搜索干货
        mFabSearch.setImageDrawable(new IconicsDrawable(getActivity(), WenFont.Icon.wen_search).colorRes(R.color.white));
        mFabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFabmenu.close(true);

            }
        });

    }


    public void getGankData(String year, String month, String day) {
        mPresenter.getTodayGank(year, month, day);
    }

    @Override
    public void showResult(TodayGank todayGank) {

        Log.d("DiscoverFragment", todayGank.toString());

        mTodayGankAdapter = new TodayGankAdapter(todayGank, getActivity());

        mTodayRecyclerview.setAdapter(mTodayGankAdapter);
    }

    @Override
    public void showGankDate(GankDate gankDate) {
        Intent intentWithDateList = GankDateActivity.createIntentWithDateList(getActivity(), gankDate);
        startActivity(intentWithDateList);
    }

    @Override
    public void onEmptyTextClick() {

    }

    @Override
    public void onDestroy() {
        if (!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
        super.onDestroy();
    }
}
