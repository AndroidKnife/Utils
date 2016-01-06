package com.hwangjr.utils.debug;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * watch app health
 */
public class AppWatcher {

    private static AppWatcher sInstance = new AppWatcher();

    private Context mContext;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private AppWatcher() {
    }

    public static AppWatcher getInstance() {
        return sInstance;
    }

    /**
     * Reference the context, must be application context
     *
     * @param context
     */
    public static void init(Context context) {
        sInstance.mContext = context;
    }

    /**
     * watch for memory usage and heap size per seconds
     */
    public void run() {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                MemoryInfo memoryInfo = new MemoryInfo();
                ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Activity.ACTIVITY_SERVICE);
                activityManager.getMemoryInfo(memoryInfo);
                Runtime runtime = Runtime.getRuntime();
                String msg = String.format("free:%s%% %sKB total:%sKB max:%sKB ", runtime.freeMemory() * 100f / runtime.totalMemory(),
                        runtime.freeMemory(), runtime.totalMemory() / 1024, runtime.maxMemory() / 1024);
                msg += String.format("native: free:%sKB total:%sKB max:%sKB", android.os.Debug.getNativeHeapFreeSize() / 1024,
                        android.os.Debug.getNativeHeapAllocatedSize() / 1024, android.os.Debug.getNativeHeapSize() / 1024);
                msg += String.format("| availMem:%sKB", memoryInfo.availMem / 1024);
                Log.d("memory", msg);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 1000, 1000);
    }
}
