package com.example.wen.wenbook.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/3/20.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {

   // private List<FragmentInfo> mFragmentInfoList = new ArrayList<>(4);

    private List<Fragment> mFragmentList = new ArrayList<>(4);

    private List<String> titles = new ArrayList<>(4);

    public MainViewPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        initTitle();
       // initFragment();
        this.mFragmentList = fragments;


    }

   /* private void initFragment() {
        mFragmentInfoList.add(new FragmentInfo("发现", DiscoverFragment.class));
        mFragmentInfoList.add(new FragmentInfo("全部", BookCoverFragment.class));
        mFragmentInfoList.add(new FragmentInfo("收藏", BookCollectFragment.class));
        mFragmentInfoList.add(new FragmentInfo("休闲", RelaxFragment.class));
    }*/

    private void initTitle(){
        titles.add("发现");
        titles.add("书柜");
        titles.add("收藏");
        titles.add("休闲");
    }

    @Override
    public Fragment getItem(int position) {
       /* try {
            return (Fragment) mFragmentInfoList.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
*/
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
       // return mFragmentInfoList.get(position).getTitle();
        return titles.get(position);
    }
}
