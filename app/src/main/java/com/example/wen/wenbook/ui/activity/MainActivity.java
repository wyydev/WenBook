package com.example.wen.wenbook.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.WeChatUser;
import com.example.wen.wenbook.common.Constant;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.di.component.DaggerLoginComponent;
import com.example.wen.wenbook.di.module.LoginModule;
import com.example.wen.wenbook.presenter.contract.LonginContract;
import com.example.wen.wenbook.presenter.impl.LoginPresenter;
import com.example.wen.wenbook.ui.adapter.MainViewPagerAdapter;
import com.example.wen.wenbook.ui.fragment.BookGridFragment;
import com.example.wen.wenbook.ui.fragment.DiscoverFragment;
import com.example.wen.wenbook.ui.fragment.RelaxFragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.iconics.IconicsDrawable;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<LoginPresenter> implements LonginContract.LoginView,NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.fab_scanner)
    FloatingActionButton mFabScanner;
    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;
    @BindView(R.id.fabmenu)
    FloatingActionMenu mFabMenu;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private View headerView;
    private ImageView mUserHeadView;
    private TextView mTextUserName;
    private List<Fragment> mFragmentList;
    private static final int ZXING_CAMERA_PERMISSION = 1;



    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {


        initDrawerLayout();

        initFragment();

        initTabLayout();

    }

    private void initFragment() {
        Fragment fragment1 = new DiscoverFragment();
        Fragment fragment2 = BookGridFragment.newInstance(BookGridFragment.TYPE_ALL);
        Fragment fragment3 = BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE);
        Fragment fragment4 = new RelaxFragment();

        mFragmentList = new ArrayList<>(4);
        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);
        mFragmentList.add(fragment4);
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {

        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().injectMainActivity(this);
    }

    private void initTabLayout() {

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),mFragmentList);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 3){
                    mFabMenu.setVisibility(View.GONE);
                }else {
                    mFabMenu.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }



    private void initDrawerLayout() {

        //headerView
        headerView = mNavigationView.getHeaderView(0);

        mUserHeadView = (ImageView) headerView.findViewById(R.id.img_account);
        mUserHeadView.setImageDrawable(new IconicsDrawable(this, WenFont.Icon.wen_user).colorRes(R.color.white));
        mTextUserName = (TextView) headerView.findViewById(R.id.txt_username);

        //headerView点击
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.askWeChatLogin();
            }
        });

        //设置侧拉菜单icon
        mNavigationView.getMenu().findItem(R.id.menu_scanning).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_scanner));
        mNavigationView.getMenu().findItem(R.id.menu_search_online).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_search));
        mNavigationView.getMenu().findItem(R.id.menu_search_local).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_add));
        mNavigationView.getMenu().findItem(R.id.menu_about).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_book));
        mNavigationView.getMenu().findItem(R.id.menu_logout).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_shutdown));

        //item点击
        mNavigationView.setNavigationItemSelectedListener(this);

        // 右下角浮动菜单
        mFabMenu.setClosedOnTouchOutside(true);

        // 右下角浮动按钮 - 扫一扫
       mFabScanner = (FloatingActionButton) findViewById(R.id.fab_scanner);
        mFabScanner.setImageDrawable(new IconicsDrawable(this,WenFont.Icon.wen_scanner).colorRes(R.color.white));
        mFabScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabMenu.close(true);
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
                } else {
                    Intent intent = new Intent(MainActivity.this, ScanningActivity.class);
                    startActivity(intent);
                }

            }
        });

        // 右下角浮动按钮 - 添加
        mFabAdd.setImageDrawable(new IconicsDrawable(this,WenFont.Icon.wen_search).colorRes(R.color.white));
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabMenu.close(true);
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("search_type", Constant.SEARCH_NET);
                startActivity(intent);
            }
        });

        mFabMenu.setVisibility(View.GONE);


        //drawerLayout 与 Toolbar 整合
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

    }



    @Override
    public void showUser(WeChatUser user) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this,"错误："+errorMsg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.menu_scanning: //扫一扫

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
                } else {
                    Intent intent = new Intent(MainActivity.this, ScanningActivity.class);
                    startActivity(intent);
                }

                break;

            case R.id.menu_search_online: //在线搜索
                Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                intent2.putExtra("search_type", SearchActivity.SEARCH_NET);
                startActivity(intent2);
                break;

            case R.id.menu_search_local: //本地查询
                Intent intent3 = new Intent(MainActivity.this, SearchActivity.class);
                intent3.putExtra("search_type", SearchActivity.SEARCH_LOCAL);
                startActivity(intent3);
                break;

            case R.id.menu_about: //关于
                break;

            case R.id.menu_logout: //退出登录
                break;
        }
       // mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        //处理返回键逻辑 若侧拉面板打开返回键首先关闭侧拉面板
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
           this.finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, ScanningActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "使用该功能前需要开启相机权限", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
