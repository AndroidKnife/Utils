package com.hwangjr.utils.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Check permission and get application granted permissions.
 */
public class PermissionHelper {
    /**
     * whether lack of permissions, {@link #lacksPermission(Context, String)}
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean lacksPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * whether lack of permission. if on per-Marshmallow devices, it will call {@link PackageManager#checkPermission(String, String)},
     * otherwise, it will call {@link #checkSelfPermission(Context, String)}.
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean lacksPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
        } else {
            return context.getPackageManager().checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_DENIED;
        }
    }

    /**
     * Determine whether <em>you</em> have been granted a particular permission.
     *
     * @param permission The name of the permission being checked.
     * @return {@link android.content.pm.PackageManager#PERMISSION_GRANTED} if you have the
     * permission, or {@link android.content.pm.PackageManager#PERMISSION_DENIED} if not.
     * @see android.content.pm.PackageManager#checkPermission(String, String)
     */
    public static int checkSelfPermission(Context context, String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(permission, Process.myPid(), Process.myUid());
    }

    /**
     * whether app has granted this permissions
     *
     * @param context
     * @param permissions Manifest.permission
     * @return true if app has, also false
     */
    public static boolean isPermissionsGranted(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!isPermissionGranted(context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * whether app has granted this permission
     *
     * @param context
     * @param permission Manifest.permission
     * @return true if app has, also false
     */
    public static boolean isPermissionGranted(Context context, String permission) {
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
