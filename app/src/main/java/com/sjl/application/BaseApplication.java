package com.sjl.application;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by song on 2017/9/12.
 */

public class BaseApplication extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
