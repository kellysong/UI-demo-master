package com.sjl.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.sjl.entity.ApkInfo;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by song on 2017/9/16.
 */

public class AppInfoUtils {

    /**
     * 获取apk信息
     *
     * @param apkPath apk包的绝对路径
     * @param context
     */
    public static ApkInfo getApkInfo(String apkPath, Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApkInfo apkInfo = new ApkInfo();
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
        /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            String appName = pm.getApplicationLabel(appInfo).toString();// 得到应用名
            String packageName = appInfo.packageName; // 得到包名
            String versionName = pkgInfo.versionName; // 得到版本信息
            Drawable icon = appInfo.loadIcon(pm);
            String pkgInfoStr = String.format("PackageName:%s, Vesion: %s, AppName: %s", packageName, versionName, appName);
            Logger.i(pkgInfoStr);
            apkInfo.setAppName(appName);
            apkInfo.setPackageName(packageName);
            apkInfo.setVersionName(versionName);
            apkInfo.setAppIcon(icon);
            return apkInfo;
        }
        return null;
    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param context
     * @param serviceName 是包名+服务的类名
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(Integer.MAX_VALUE);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {

            String mName = myList.get(i).service.getClassName().toString();
            Log.i("isServiceRunning","正在运行服务："+mName);
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
