package com.hwangjr.utils.screen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Shoot for activity and view etc..
 */
public class ScreenShoot {

    /**
     * shoot the activity, exclude the status bar.
     */
    public static Bitmap shoot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        Bitmap drawingCache = getViewDrawableCache(view);
        int statusBarHeight = ScreenHelper.getStatusBarHeight(activity);
        int width = ScreenHelper.getDeviceWidth(activity);
        int height = ScreenHelper.getDeviceHeight(activity);
        Bitmap bitmap = Bitmap.createBitmap(drawingCache, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * shoot the view
     */
    public static Bitmap shoot(View view) {
        Bitmap drawingCache = getViewDrawableCache(view);
        Bitmap bitmap = Bitmap.createBitmap(drawingCache, 0, 0, drawingCache.getWidth(), drawingCache.getHeight());
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * shoot the web view
     */
    public static Bitmap shoot(WebView webView) {
        Picture snapShot = webView.capturePicture();
        Bitmap bitmap = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        snapShot.draw(canvas);
        return bitmap;
    }

    /**
     * get view drawable cache.
     * <strong>
     * Remember to destroy the cache.{@link View#destroyDrawingCache()}
     * </strong>
     */
    private static Bitmap getViewDrawableCache(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    /**
     * shoot activity and save bitmap to default path.
     * if save succeed, it will return the path.
     * path : {@link #getDefaultPath(Context)}
     */
    public static String shootAndSave(Activity activity) {
        String filePath = getDefaultPath(activity);
        return saveBitmap(shoot(activity), filePath) ? filePath : "";
    }

    /**
     * shoot web view and save bitmap to default path.
     * if save succeed, it will return the path.
     * path : {@link #getDefaultPath(Context)}
     */
    public static String shootAndSave(WebView webView) {
        String filePath = getDefaultPath(webView.getContext());
        return saveBitmap(shoot(webView), filePath) ? filePath : "";
    }

    /**
     * save the bitmap to path.
     */
    public static boolean saveBitmap(Bitmap bitmap, String path) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                outputStream.flush();
                outputStream.close();
                outputStream = null;
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * generate the default path for the file.
     * default file name format is: yyyyMMddHHmmss.png.
     */
    private static String getDefaultPath(Context context) {
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png";
        String path = context.getFilesDir() + fileName;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = context.getExternalCacheDir() + File.separator + fileName;
        }
        return path;
    }
}
