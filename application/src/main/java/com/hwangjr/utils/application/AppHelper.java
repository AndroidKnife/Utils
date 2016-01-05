package com.hwangjr.utils.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Rect;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Window;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.x500.X500Principal;

public class AppHelper {

    private final static X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");

    /**
     * Don't let anyone instantiate this class.
     */
    private AppHelper() {
        throw new Error("Do not need instantiate!");
    }


    /**
     * get version code
     */
    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            String packageName = context.getPackageName();
            verCode = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * get app max runtime memory
     */
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory() / 1024;
    }

    /**
     * get version name.
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            String packageName = context.getPackageName();
            verName = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * Install apk
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * Install apk
     */
    public static void installApk(Context context, Uri file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * uninstall apk
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }


    /**
     * whether the service is running.
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager
                = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> servicesList
                = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo si : servicesList) {
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;
    }


    /**
     * stop the running service.
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent serviceIntent = null;
        boolean result = false;
        try {
            serviceIntent = new Intent(context, Class.forName(className));
            if (serviceIntent != null) {
                result = context.stopService(serviceIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * whether this process is named with processName
     */
    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null || TextUtils.isEmpty(processName)) {
            return false;
        }

        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList
                = manager.getRunningAppProcesses();
        if (processInfoList != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
                if (processInfo.pid == pid &&
                        processName.equalsIgnoreCase(processInfo.processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * whether application is in background
     * <ul>
     * <li>need use permission android.permission.GET_TASKS in Manifest.xml.
     * (<uses-permission android:name="android.permission.GET_TASKS" />)</li>
     * </ul>
     *
     * @param context
     * @return if application is in background return true, otherwise return
     * false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName()
                    .equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * get application sign info
     *
     * @param context
     * @return
     */
    private String getCertificateSHA1Fingerprint(Context context) {
        String hexString = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = packageInfo.signatures[0].toByteArray();
            InputStream input = new ByteArrayInputStream(cert);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(input);
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            byte[] publicKey = sha1.digest(certificate.getEncoded());
            hexString = byte2Hex(publicKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return hexString;
    }

    /**
     * byte 2 hex code
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            String h = Integer.toHexString(bytes[i]);
            int l = h.length();
            if (l == 1) h = "0" + h;
            if (l > 2) h = h.substring(l - 2, l);
            builder.append(h.toUpperCase());
            if (i < (bytes.length - 1)) builder.append(':');
        }
        return builder.toString();
    }

    /**
     * kill other process and services
     *
     * @param context
     * @return count of clean up process and services
     */
    public static int gc(Context context) {
        int count = 0;
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(100);
        if (serviceList != null) {
            for (ActivityManager.RunningServiceInfo service : serviceList) {
                if (service.pid == android.os.Process.myPid()) continue;
                try {
                    android.os.Process.killProcess(service.pid);
                    count++;
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }

        List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();
        if (processList != null) {
            for (ActivityManager.RunningAppProcessInfo process : processList) {
                if (process.importance >
                        ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = process.pkgList;
                    for (String pkgName : pkgList) {
                        try {
                            am.killBackgroundProcesses(pkgName);
                            count++;
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                }
            }
        }
        return count;
    }


    /**
     * get usable memory for device
     *
     * @param context
     * @return current usable memory
     */
    public static int getDeviceUsableMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(mi);
        return (int) (mi.availMem / (1024 * 1024));
    }


    /**
     * get all applications
     */
    public static List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                apps.add(packageInfo);
            }
        }
        return apps;
    }

    /**
     * whether is in dalvik runtime
     */
    public static boolean isDalvik() {
        return "Dalvik".equals(getCurrentRuntimeValue());
    }

    /**
     * whether is in ART runtime
     */
    public static boolean isART() {
        String currentRuntime = getCurrentRuntimeValue();
        return "ART".equals(currentRuntime) ||
                "ART debug build".equals(currentRuntime);
    }

    /**
     * get current runtime
     *
     * @return Dalvik, ART, ART debug build
     */
    public static String getCurrentRuntimeValue() {
        try {
            Class<?> systemProperties = Class.forName(
                    "android.os.SystemProperties");
            try {
                Method get = systemProperties.getMethod("get", String.class,
                        String.class);
                if (get == null) {
                    return "WTF?!";
                }
                try {
                    final String value = (String) get.invoke(systemProperties,
                            "persist.sys.dalvik.vm.lib",
                            /* Assuming default is */
                            "Dalvik");
                    if ("libdvm.so".equals(value)) {
                        return "Dalvik";
                    } else if ("libart.so".equals(value)) {
                        return "ART";
                    } else if ("libartd.so".equals(value)) {
                        return "ART debug build";
                    }
                    return value;
                } catch (IllegalAccessException e) {
                    return "IllegalAccessException";
                } catch (IllegalArgumentException e) {
                    return "IllegalArgumentException";
                } catch (InvocationTargetException e) {
                    return "InvocationTargetException";
                }
            } catch (NoSuchMethodException e) {
                return "SystemProperties.get(String key, String def) method is not found";
            }
        } catch (ClassNotFoundException e) {
            return "SystemProperties class is not found";
        }
    }


    /**
     * whether current app is debuggable
     */
    public static boolean isDebuggable(Context ctx) {
        boolean debuggable = false;
        try {
            PackageInfo packageInfo = ctx.getPackageManager()
                    .getPackageInfo(ctx.getPackageName(),
                            PackageManager.GET_SIGNATURES);
            Signature signatures[] = packageInfo.signatures;
            for (int i = 0; i < signatures.length; i++) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream stream = new ByteArrayInputStream(
                        signatures[i].toByteArray());
                X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(
                        stream);
                debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
                if (debuggable) {
                    break;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return debuggable;
    }

    /**
     * telephone is sleeping
     *
     * @param context
     * @return
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    /**
     * this device has phone radio?
     *
     * @param context
     * @return
     */
    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephony.getPhoneType();
        if (type == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * get current app version name
     */
    public static String getAppVersion(Context context) {
        String version = "0";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * start home intent
     *
     * @param context
     */
    public static void startHomeActivity(Context context) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(homeIntent);
    }

    /**
     * get status bar height.
     * <br />
     * rely on the fact that te status bar is shown at the time you make your computation,
     * <strong>
     * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
     * this will not work!!
     * </strong>
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * get status bar height from dimension resource called status_bar_height.
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * get status bar plus app bar height, just content's({@link Window#ID_ANDROID_CONTENT}) top.
     *
     * @param activity
     * @return
     */
    public static int getContentTopHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * get app bar height
     *
     * @param activity
     * @return
     */
    public static int getAppBarHeight(Activity activity) {
        return getContentTopHeight(activity) - getStatusBarHeight((Context) activity);
    }

}
