package com.hwangjr.utils.application;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Surface;

public class WindowHelper {
    /**
     * Don't let anyone instantiate this class.
     */
    private WindowHelper() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * get current window rotation
     *
     * @param activity activity
     * @return int {@link Surface#ROTATION_0}: 0; {@link Surface#ROTATION_90}: 90;
     * {@link Surface#ROTATION_180}: 180; {@link Surface#ROTATION_270}: 270
     */
    public static int getDisplayRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * whether current window is landscape
     *
     * @param context context
     * @return boolean true if landscape, otherwise false
     */
    public static final boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * whether current window is portrait
     *
     * @param context context
     * @return boolean
     */
    public static final boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
