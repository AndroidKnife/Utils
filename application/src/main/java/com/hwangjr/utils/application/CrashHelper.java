package com.hwangjr.utils.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHelper implements UncaughtExceptionHandler {

    private static CrashHelper INSTANCE = new CrashHelper();

    private String showMessage = "Opps, App has crashed, we will fixed it soon!";
    private UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    //As part of file name
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private String crashFilePath = "/crash/";

    private CrashHelper() {
    }

    /**
     * Singleton
     */
    public static CrashHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Initialize
     *
     * @param context
     * @param crashFilePath
     */
    public void init(Context context, String crashFilePath, String showMessage) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.crashFilePath = crashFilePath;
        if (!TextUtils.isEmpty(showMessage)) {
            this.showMessage = showMessage;
        }
    }

    /**
     * Initialize
     *
     * @param context
     */
    public void init(Context context) {
        init(context, "/crash/" + context.getPackageName());
    }

    /**
     * Initialize
     *
     * @param context
     */
    public void init(Context context, String crashFilePath) {
        init(context, crashFilePath, "");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }


    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, showMessage, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        saveCrashInfo2File(ex, getDeviceInfo(mContext));
        return true;
    }

    /**
     * Collect Device info
     */
    public Map<String, String> getDeviceInfo(Context context) {
        Map<String, String> infos = new HashMap<>();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return infos;
    }

    /**
     * Save Info to files
     */
    private String saveCrashInfo2File(Throwable throwable, Map<String, String> info) {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : info.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            buffer.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        buffer.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path =
                        Environment.getExternalStorageDirectory().getAbsolutePath() +
                                (!TextUtils.isEmpty(crashFilePath) ? crashFilePath : "/crash/");
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(buffer.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
