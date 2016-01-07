package com.hwangjr.utils.application;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class ManifestHelper {

    /**
     * get package name
     */
    public static String getPackageName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get meta data from manifest
     *
     * @param context
     * @param key     key
     * @return value
     */
    public static String getMetadata(Context context, String key) {
        try {
            Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            return metaData.get(key).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
