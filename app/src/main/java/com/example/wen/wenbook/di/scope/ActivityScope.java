package com.example.wen.wenbook.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wen on 2017/3/21.
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface ActivityScope {
}
