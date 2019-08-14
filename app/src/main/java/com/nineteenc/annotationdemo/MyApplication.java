package com.nineteenc.annotationdemo;

import android.app.Application;

import com.nineteenc.module_annotation.NCRoute;

/**
 * Author    zhengchengbin
 * Describe:
 * Data:      2019/8/14 10:46
 * Modify by:
 * Modification date:
 * Modify content:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NCRoute.getInstance().init(this);
    }
}
