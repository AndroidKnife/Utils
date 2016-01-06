package com.hwangjr.utils.screen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * Utility for screen.
 * get the screen width and height, get status bar height etc..
 */
public class ScreenHelper {

    /**
     * get screen width. {@link DisplayMetrics#widthPixels}
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * get screen height. {@link DisplayMetrics#heightPixels}
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * get device width. {@link Display#getWidth}
     */
    public static int getDeviceWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    /**
     * get device height. {@link Display#getHeight()}
     */
    public static int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
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
