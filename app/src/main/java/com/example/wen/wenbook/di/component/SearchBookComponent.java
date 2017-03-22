package com.example.wen.wenbook.di.component;

import com.example.wen.wenbook.di.module.SearchBookModule;
import com.example.wen.wenbook.di.scope.ActivityScope;
import com.example.wen.wenbook.ui.activity.SearchActivity;

import dagger.Component;

/**
 * Created by wen on 2017/3/21.
 */

@ActivityScope
@Component(modules = SearchBookModule.class,dependencies = AppComponent.class)
public interface SearchBookComponent {
    void inject(SearchActivity searchActivity);
}
