package com.example.wen.wenbook.ui.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.WeChatUser;
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

public class MainActivity extends BaseActivity<LoginPresenter> implements LonginContract.LoginView{

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
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initDrawerLayout() {

        headerView = mNavigationView.getHeaderView(0);

        mUserHeadView = (ImageView) headerView.findViewById(R.id.img_account);
        mUserHeadView.setImageDrawable(new IconicsDrawable(this, WenFont.Icon.wen_user).colorRes(R.color.white));
        mTextUserName = (TextView) headerView.findViewById(R.id.txt_username);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.askWeChatLogin();
            }
        });

        mNavigationView.getMenu().findItem(R.id.menu_scanning).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_scanner));
        mNavigationView.getMenu().findItem(R.id.menu_search_online).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_search));
        mNavigationView.getMenu().findItem(R.id.menu_search_local).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_add));
        mNavigationView.getMenu().findItem(R.id.menu_about).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_book));
        mNavigationView.getMenu().findItem(R.id.menu_logout).setIcon(new IconicsDrawable(this, WenFont.Icon.wen_shutdown));


        // 右下角浮动菜单
        mFabMenu.setClosedOnTouchOutside(true);

        // 右下角浮动按钮 - 扫一扫
       mFabScanner = (FloatingActionButton) findViewById(R.id.fab_scanner);
        mFabScanner.setImageDrawable(new IconicsDrawable(this,WenFont.Icon.wen_scanner).colorRes(R.color.white));
        mFabScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabMenu.close(true);
          /*      if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
                } else {
                    Intent intent = new Intent(MainActivity.this, ScanningActivity.class);
                    startActivity(intent);
                }*/

            }
        });

        // 右下角浮动按钮 - 添加
        mFabAdd.setImageDrawable(new IconicsDrawable(this,WenFont.Icon.wen_search).colorRes(R.color.white));
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabMenu.close(true);
               /* Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);*/
            }
        });


        //drawerLayout 与 Toolbar 整合
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

    }

    @Override
    public void showUser(WeChatUser user) {

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this,errorMsg,Toast.LENGTH_SHORT).show();
    }
}
