package com.hwangjr.utils.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**
     * whether app has granted this permission
     *
     * @param context
     * @param permission Manifest.permission
     * @return true if app has, also false
     */
    public static boolean hasPermission(Context context, String permission) {
        return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(permission, context.getPackageName());
    }

    /**
     * get granted permission list
     */
    public static List<String> getPermissions(Context context) {
        List<String> permissions = new ArrayList<>();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            permissions.addAll(Arrays.asList(packageInfo.requestedPermissions));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return permissions;
    }
}
