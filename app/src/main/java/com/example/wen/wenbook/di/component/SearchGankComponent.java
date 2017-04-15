package com.example.wen.wenbook.di.component;

import com.example.wen.wenbook.di.module.SearchGankModule;
import com.example.wen.wenbook.di.scope.ActivityScope;
import com.example.wen.wenbook.ui.activity.GankSearchActivity;


import dagger.Component;

/**
 * Created by wen on 2017/4/12.
 */

@ActivityScope
@Component(modules = SearchGankModule.class,dependencies = AppComponent.class)
public interface SearchGankComponent {
    void inject(GankSearchActivity gankSearchActivity);
}
