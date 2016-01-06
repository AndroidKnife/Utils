package com.hwangjr.utils.debug;

import android.os.Build;
import android.os.StrictMode;

public class StrictModeHelper {

    /**
     * enable strict mode
     * <br />
     * Usage:
     * if (BuildConfig.DEBUG) {
     *      StrictModeHelper.enableStrictMode();
     * }
     */
    public static void enableStrictMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                threadPolicyBuilder.penaltyFlashScreen().penaltyDeathOnNetwork();
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }
}
