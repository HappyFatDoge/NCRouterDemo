package com.nineteenc.module_annotation;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

/**
 * Author    zhengchengbin
 * Describe:
 * Data:      2019/8/14 10:19
 * Modify by:
 * Modification date:
 * Modify content:
 */
public class NavigationUtils {

    private String mActivityName;
    private Bundle mBundle;

    public NavigationUtils(String activityName) {
        this.mActivityName = activityName;
        this.mBundle = new Bundle();
    }

    public NavigationUtils withInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public NavigationUtils withString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public NavigationUtils with(Bundle bundle) {
        if (bundle != null) {
            mBundle = bundle;
        }
        return this;
    }

    public void navigation() {

    }

    public void navigation(Activity context, int requestCode) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context.getPackageName(), mActivityName));
        intent.putExtras(mBundle);
        context.startActivityForResult(intent, requestCode);
    }
}
