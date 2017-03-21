package com.example.wen.wenbook.di.component;

import com.example.wen.wenbook.di.module.LoginModule;
import com.example.wen.wenbook.di.scope.ActivityScope;
import com.example.wen.wenbook.ui.activity.MainActivity;
import com.example.wen.wenbook.wxapi.WXEntryActivity;

import dagger.Component;

/**
 * Created by wen on 2017/3/21.
 */

@ActivityScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {
    void injectMainActivity(MainActivity mainActivity);
   // void injectWxEntryActivity(WXEntryActivity wxEntryActivity);
}
