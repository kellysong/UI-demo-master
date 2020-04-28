package com.sjl.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by song on 2017/9/16.
 */

public class ApkInfo {
    private String appName;// 得到应用名
    private String packageName; // 得到包名
    private String versionName; // 得到版本信息
    private Drawable appIcon;//得到应用图标


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
