package com.hwangjr.utils.softinput;

import android.content.Context;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftInput {

    private SoftInput() {
    }

    /**
     * Show the input method's soft input area, so the user sees the input method window and can interact with it.
     * This can only be called from the currently active input method, as validated by the given token.
     *
     * @param view the current focused view
     */
    public static void showSoftInputFromInputMethod(View view) {
        if (view != null) {
            Context context = view.getContext();
            IBinder windowToken = view.getWindowToken();
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInputFromInputMethod(windowToken, 0);
        }
    }

    /**
     * Request to hide the soft input window from the context of the window that is currently accepting input.
     * This should be called as a result of the user doing some actually than fairly explicitly requests to have the input window hidden.
     *
     * @param view the current focused view
     */
    public static boolean hideSoftInputFromWindow(View view) {
        boolean result = false;
        if (view != null) {
            Context context = view.getContext();
            IBinder windowToken = view.getWindowToken();
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            result  = inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
        return result;
    }

    /**
     * Synonym for {@link InputMethodManager.showSoftInput(View, int, ResultReceiver)} without
     * a result receiver: explicitly request that the current input method's
     * soft input area be shown to the user, if needed.
     *
     * @param view the current focused view
     */
    public static boolean showSoftInput(View view) {
        boolean result = false;
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            result = inputMethodManager.showSoftInput(view, 0);
        }
        return result;
    }

    /**
     * Request to hide the soft input window from the context of the window that is currently accepting input.
     * This should be called as a result of the user doing some actually than fairly explicitly requests to have the input window hidden.
     *
     * @param view the current focused view
     */
    public static boolean hideSoftInput(View view) {
        return hideSoftInputFromWindow(view);
    }

    /**
     * This method toggles the input method window display.
     * If the input window is already displayed, it gets hidden. If not the input window will be displayed.
     *
     * @param context context to get the input service
     */
    public static void toggle(Context context) {
        if (context != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
