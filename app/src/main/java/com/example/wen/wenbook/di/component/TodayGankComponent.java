package com.example.wen.wenbook.di.component;

import com.example.wen.wenbook.di.module.AddBookModule;
import com.example.wen.wenbook.di.module.TodayGankModule;
import com.example.wen.wenbook.di.scope.ActivityScope;
import com.example.wen.wenbook.ui.activity.BookInfoAddActivity;
import com.example.wen.wenbook.ui.activity.BookInfoAddActivity2;
import com.example.wen.wenbook.ui.fragment.DiscoverFragment;

import dagger.Component;

/**
 * Created by wen on 2017/4/12.
 */

@ActivityScope
@Component(modules = TodayGankModule.class,dependencies = AppComponent.class)
public interface TodayGankComponent {
    void inject(DiscoverFragment discoverFragment);
}
