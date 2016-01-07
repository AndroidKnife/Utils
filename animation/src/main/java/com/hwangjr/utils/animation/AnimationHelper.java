package com.hwangjr.utils.animation;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {

    /**
     * default animation duration.
     */
    public static final long DEFAULT_ANIMATION_DURATION = 400;

    /**
     * Don't let anyone instantiate this class.
     */
    private AnimationHelper() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * make view invisible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#INVISIBLE}
     *
     * @param view           to be invisible view
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     * @param listener       the animation listener to be notified
     */
    public static void invisibleViewByAlpha(final View view,
                                            long durationMillis, final boolean isBanClick,
                                            final AnimationListener listener) {
        if (view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
            AlphaAnimation hiddenAlphaAnimation = CustomAnim
                    .getVisibleAlphaAnimation(durationMillis);
            hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(false);
                    }
                    if (listener != null) {
                        listener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(true);
                    }
                    if (listener != null) {
                        listener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(hiddenAlphaAnimation);
        }
    }

    /**
     * make view invisible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#INVISIBLE}
     *
     * @param view           to be invisible view
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     */
    public static void invisibleViewByAlpha(final View view,
                                            long durationMillis, final AnimationListener listener) {
        invisibleViewByAlpha(view, durationMillis, false, listener);
    }

    /**
     * make view invisible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#INVISIBLE}
     *
     * @param view           to be invisible view
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     */
    public static void invisibleViewByAlpha(final View view,
                                            long durationMillis, boolean isBanClick) {
        invisibleViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * make view invisible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#INVISIBLE}
     *
     * @param view           to be invisible view
     * @param durationMillis Duration in milliseconds
     */
    public static void invisibleViewByAlpha(final View view, long durationMillis) {
        invisibleViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * make view invisible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#INVISIBLE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view       to be invisible view
     * @param isBanClick whether view can click in animation
     * @param listener   the animation listener to be notified
     */
    public static void invisibleViewByAlpha(final View view,
                                            boolean isBanClick, final AnimationListener listener) {
        invisibleViewByAlpha(view, DEFAULT_ANIMATION_DURATION, isBanClick, listener);
    }

    /**
     * make view invisible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#INVISIBLE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view     to be invisible view
     * @param listener the animation listener to be notified
     */
    public static void invisibleViewByAlpha(final View view,
                                            final AnimationListener listener) {
        invisibleViewByAlpha(view, DEFAULT_ANIMATION_DURATION, false, listener);
    }

    /**
     * make view invisible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#INVISIBLE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view       to be invisible view
     * @param isBanClick whether view can click in animation
     */
    public static void invisibleViewByAlpha(final View view, boolean isBanClick) {
        invisibleViewByAlpha(view, DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * make view invisible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#INVISIBLE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view to be invisible view
     */
    public static void invisibleViewByAlpha(final View view) {
        invisibleViewByAlpha(view, DEFAULT_ANIMATION_DURATION, false, null);
    }

    /**
     * make view gone by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#GONE}
     *
     * @param view           to be gone view
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     * @param listener       the animation listener to be notified
     */
    public static void goneViewByAlpha(final View view, long durationMillis,
                                       final boolean isBanClick, final AnimationListener listener) {
        if (view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
            AlphaAnimation hiddenAlphaAnimation = CustomAnim
                    .getVisibleAlphaAnimation(durationMillis);
            hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(false);
                    }
                    if (listener != null) {
                        listener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(true);
                    }
                    if (listener != null) {
                        listener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(hiddenAlphaAnimation);
        }
    }

    /**
     * make view gone by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#GONE}
     *
     * @param view           to be gone view
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     */
    public static void goneViewByAlpha(final View view, long durationMillis,
                                       final AnimationListener listener) {
        goneViewByAlpha(view, durationMillis, false, listener);
    }

    /**
     * make view gone by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#GONE}
     *
     * @param view           to be gone view
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     */
    public static void goneViewByAlpha(final View view, long durationMillis,
                                       final boolean isBanClick) {
        goneViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * make view gone by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#GONE}
     *
     * @param view           to be gone view
     * @param durationMillis Duration in milliseconds
     */
    public static void goneViewByAlpha(final View view, long durationMillis) {
        goneViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * make view gone by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#GONE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view       to be gone view
     * @param isBanClick whether view can click in animation
     * @param listener   the animation listener to be notified
     */
    public static void goneViewByAlpha(final View view,
                                       final boolean isBanClick, final AnimationListener listener) {
        goneViewByAlpha(view, DEFAULT_ANIMATION_DURATION, isBanClick, listener);
    }

    /**
     * make view gone by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#GONE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view     to be gone view
     * @param listener the animation listener to be notified
     */
    public static void goneViewByAlpha(final View view, final AnimationListener listener) {
        goneViewByAlpha(view, DEFAULT_ANIMATION_DURATION, false, listener);
    }

    /**
     * make view gone by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#GONE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view       to be gone view
     * @param isBanClick whether view can click in animation
     */
    public static void goneViewByAlpha(final View view, final boolean isBanClick) {
        goneViewByAlpha(view, DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * make view gone by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#GONE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view to be gone view
     */
    public static void goneViewByAlpha(final View view) {
        goneViewByAlpha(view, DEFAULT_ANIMATION_DURATION, false, null);
    }

    /**
     * make view visible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#VISIBLE}
     *
     * @param view           to be visible view
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     * @param listener       the animation listener to be notified
     */
    public static void visibleViewByAlpha(final View view, long durationMillis,
                                          final boolean isBanClick, final AnimationListener listener) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            AlphaAnimation showAlphaAnimation = CustomAnim
                    .getInvisibleAlphaAnimation(durationMillis);
            showAlphaAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(false);
                    }
                    if (listener != null) {
                        listener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(true);
                    }
                    if (listener != null) {
                        listener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(showAlphaAnimation);
        }
    }

    /**
     * make view visible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#VISIBLE}
     *
     * @param view           to be visible view
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     */
    public static void visibleViewByAlpha(final View view, long durationMillis,
                                          final AnimationListener listener) {
        visibleViewByAlpha(view, durationMillis, false, listener);
    }

    /**
     * make view visible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#VISIBLE}
     *
     * @param view           to be visible view
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     */
    public static void visibleViewByAlpha(final View view, long durationMillis,
                                          final boolean isBanClick) {
        visibleViewByAlpha(view, durationMillis, isBanClick, null);
    }

    /**
     * make view visible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#VISIBLE}
     *
     * @param view           to be visible view
     * @param durationMillis Duration in milliseconds
     */
    public static void visibleViewByAlpha(final View view, long durationMillis) {
        visibleViewByAlpha(view, durationMillis, false, null);
    }

    /**
     * make view visible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#VISIBLE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view       to be visible view
     * @param isBanClick whether view can click in animation
     * @param listener   the animation listener to be notified
     */
    public static void visibleViewByAlpha(final View view,
                                          final boolean isBanClick, final AnimationListener listener) {
        visibleViewByAlpha(view, DEFAULT_ANIMATION_DURATION, isBanClick, listener);
    }

    /**
     * make view visible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#VISIBLE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view     to be visible view
     * @param listener the animation listener to be notified
     */
    public static void visibleViewByAlpha(final View view,
                                          final AnimationListener listener) {
        visibleViewByAlpha(view, DEFAULT_ANIMATION_DURATION, false, listener);
    }

    /**
     * make view visible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#VISIBLE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view       to be visible view
     * @param isBanClick whether view can click in animation
     */
    public static void visibleViewByAlpha(final View view,
                                          final boolean isBanClick) {
        visibleViewByAlpha(view, DEFAULT_ANIMATION_DURATION, isBanClick, null);
    }

    /**
     * make view visible by alpha animation.
     * {@link View#setVisibility(int)} set to {@link View#VISIBLE}
     * default duration is {@link #DEFAULT_ANIMATION_DURATION}
     *
     * @param view to be visible view
     */
    public static void visibleViewByAlpha(final View view) {
        visibleViewByAlpha(view, DEFAULT_ANIMATION_DURATION, false, null);
    }

    /**
     * translate the view.
     *
     * @param view           view to be translated
     * @param fromXDelta     Change in X coordinate to apply at the start of the
     *                       animation
     * @param toXDelta       Change in X coordinate to apply at the end of the
     *                       animation
     * @param fromYDelta     Change in Y coordinate to apply at the start of the
     *                       animation
     * @param toYDelta       Change in Y coordinate to apply at the end of the
     *                       animation
     * @param cycles         Repeats the animation for a specified number of cycles {@link CycleInterpolator}.
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     */
    public static void translate(final View view, float fromXDelta,
                                 float toXDelta, float fromYDelta, float toYDelta, float cycles,
                                 long durationMillis, final boolean isBanClick) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                fromXDelta, toXDelta, fromYDelta, toYDelta);
        translateAnimation.setDuration(durationMillis);
        if (cycles > 0.0) {
            translateAnimation.setInterpolator(new CycleInterpolator(cycles));
        }
        translateAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isBanClick) {
                    view.setClickable(false);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isBanClick) {
                    view.setClickable(true);
                }
            }
        });
        view.startAnimation(translateAnimation);
    }

    /**
     * translate the view.
     *
     * @param view           view to be translated
     * @param fromXDelta     Change in X coordinate to apply at the start of the
     *                       animation
     * @param toXDelta       Change in X coordinate to apply at the end of the
     *                       animation
     * @param fromYDelta     Change in Y coordinate to apply at the start of the
     *                       animation
     * @param toYDelta       Change in Y coordinate to apply at the end of the
     *                       animation
     * @param cycles         Repeats the animation for a specified number of cycles {@link CycleInterpolator}.
     * @param durationMillis Duration in milliseconds
     */
    public static void translate(final View view, float fromXDelta, float toXDelta,
                                 float fromYDelta, float toYDelta, float cycles,
                                 long durationMillis) {
        translate(view, fromXDelta, toXDelta, fromYDelta, toYDelta, cycles,
                durationMillis, false);
    }

    /**
     * shake the view.
     *
     * @param view           view to be shake
     * @param fromXDelta     Change in X coordinate to apply at the start of the
     *                       animation
     * @param toXDelta       Change in X coordinate to apply at the end of the
     *                       animation
     * @param cycles         Repeats the animation for a specified number of cycles {@link CycleInterpolator}.
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     */
    public static void shake(View view, float fromXDelta, float toXDelta,
                             float cycles, long durationMillis, final boolean isBanClick) {
        translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles,
                durationMillis, isBanClick);
    }

    /**
     * shake the view.
     *
     * @param view           view to be shake
     * @param fromXDelta     Change in X coordinate to apply at the start of the
     *                       animation
     * @param toXDelta       Change in X coordinate to apply at the end of the
     *                       animation
     * @param cycles         Repeats the animation for a specified number of cycles {@link CycleInterpolator}.
     * @param durationMillis Duration in milliseconds
     */
    public static void shake(View view, float fromXDelta, float toXDelta,
                             float cycles, long durationMillis) {
        translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles,
                durationMillis, false);
    }

    /**
     * shake the view.
     * default x delta is from 0.0f to 10.0f.
     *
     * @param view           view to be shake
     * @param cycles         Repeats the animation for a specified number of cycles {@link CycleInterpolator}.
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     */
    public static void shake(View view, float cycles, long durationMillis,
                             final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis,
                isBanClick);
    }

    /**
     * shake the view.
     * default x delta is from 0.0f to 10.0f.
     * duration is 700ms
     *
     * @param view       view to be shake
     * @param cycles     Repeats the animation for a specified number of cycles {@link CycleInterpolator}
     * @param isBanClick whether view can click in animation
     */
    public static void shake(View view, float cycles, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, 700, isBanClick);
    }

    /**
     * shake the view.
     * default x delta is from 0.0f to 10.0f.
     *
     * @param view           view to be shake
     * @param cycles         Repeats the animation for a specified number of cycles {@link CycleInterpolator}.
     * @param durationMillis Duration in milliseconds
     */
    public static void shake(View view, float cycles, long durationMillis) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, durationMillis, false);
    }

    /**
     * shake the view.
     * default x delta is from 0.0f to 10.0f.
     * repeat 7 times.
     *
     * @param view           view to be shake
     * @param durationMillis Duration in milliseconds
     * @param isBanClick     whether view can click in animation
     */
    public static void shake(View view, long durationMillis, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis, isBanClick);
    }

    /**
     * shake the view.
     * default x delta is from 0.0f to 10.0f.
     * duration is 700ms.
     *
     * @param view   view to be shake
     * @param cycles Repeats the animation for a specified number of cycles {@link CycleInterpolator}.
     */
    public static void shake(View view, float cycles) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, cycles, 700, false);
    }

    /**
     * shake the view.
     * default x delta is from 0.0f to 10.0f.
     * repeat 7 times.
     *
     * @param view           view to be shake
     * @param durationMillis Duration in milliseconds
     */
    public static void shake(View view, long durationMillis) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, durationMillis, false);
    }

    /**
     * shake the view.
     * default x delta is from 0.0f to 10.0f.
     * duration is 700ms.
     * repeat 7 times.
     *
     * @param view       view to be shake
     * @param isBanClick whether view can click in animation
     */
    public static void shake(View view, final boolean isBanClick) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, 700, isBanClick);
    }

    /**
     * shake the view.
     * default x delta is from 0.0f to 10.0f.
     * duration is 700ms.
     * repeat 7 times.
     *
     * @param view view to be shake
     */
    public static void shake(View view) {
        translate(view, 0.0f, 10.0f, 0.0f, 0.0f, 7, 700, false);
    }
}
