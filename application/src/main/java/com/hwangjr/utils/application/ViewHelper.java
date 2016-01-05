package com.hwangjr.utils.application;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ViewHelper {

    private ViewHelper() {
        throw new AssertionError();
    }

    /**
     * get descended views from parent.
     *
     * @param <T>             type of the view
     * @param parent          ViewGroup
     * @param filter          Type of views which will be returned.
     * @param includeSubClass Whether returned list will include views which are subclass of filter or not.
     * @return View
     */
    public static <T extends View> List<T> getDescendants(ViewGroup parent, Class<T> filter, boolean includeSubClass) {
        List<T> descendedViewList = new ArrayList<>();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            Class<? extends View> childClass = child.getClass();
            if ((includeSubClass && filter.isAssignableFrom(childClass))
                    || (!includeSubClass && childClass == filter)) {
                descendedViewList.add(filter.cast(child));
            }
            if (child instanceof ViewGroup) {
                descendedViewList.addAll(getDescendants((ViewGroup) child, filter, includeSubClass));
            }
        }
        return descendedViewList;
    }

    /**
     * set view background drawable
     *
     * @param view
     * @param drawable
     */
    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    /**
     * get view measured height, it will calculate view and return height
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view) {
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * get view measured width, it will calculate view and return width
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(View view) {
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * measure the view
     */
    public static void calcViewMeasure(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }

    /**
     * change image view brightness by color filter
     */
    public static void changeBrightness(ImageView imageview, float brightness) {
        imageview.setColorFilter(getBrightnessMatrixColorFilter(brightness));
    }

    /**
     * change drawable brightness by color filter
     */
    public static void changeBrightness(Drawable drawable, float brightness) {
        drawable.setColorFilter(getBrightnessMatrixColorFilter(brightness));
    }

    private static ColorMatrixColorFilter getBrightnessMatrixColorFilter(float brightness) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(
                new float[]{
                        1, 0, 0, 0, brightness,
                        0, 1, 0, 0, brightness,
                        0, 0, 1, 0, brightness,
                        0, 0, 0, 1, 0
                });
        return new ColorMatrixColorFilter(matrix);
    }

    /**
     * get color state list
     */
    public static ColorStateList createColorStateList(int normal, int pressed) {
        return createColorStateList(normal, pressed, Color.GRAY);
    }

    /**
     * get color state list
     */
    public static ColorStateList createColorStateList(int normal, int pressed, int unable) {
        return createColorStateList(normal, pressed, pressed, pressed, unable);
    }

    /**
     * get color state list
     */
    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int checked, int unable) {
        int[] colors = new int[]{pressed, focused, checked, normal, unable};
        int[][] states = new int[5][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_focused, android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_checked, android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_enabled};
        states[4] = new int[]{};
        return new ColorStateList(states, colors);
    }
}
