package com.hwangjr.utils.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

public class CustomAnim {

    /**
     * Don't let anyone instantiate this class.
     */
    private CustomAnim() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * get a rotation animation
     *
     * @param fromDegrees    Rotation offset to apply at the start of the
     *                       animation.
     * @param toDegrees      Rotation offset to apply at the end of the animation.
     * @param pivotXType     Specifies how pivotXValue should be interpreted. One of
     *                       Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or
     *                       Animation.RELATIVE_TO_PARENT.
     * @param pivotXValue    The X coordinate of the point about which the object
     *                       is being rotated, specified as an absolute number where 0 is the
     *                       left edge. This value can either be an absolute number if
     *                       pivotXType is ABSOLUTE, or a percentage (where 1.0 is 100%)
     *                       otherwise.
     * @param pivotYType     Specifies how pivotYValue should be interpreted. One of
     *                       Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or
     *                       Animation.RELATIVE_TO_PARENT.
     * @param pivotYValue    The Y coordinate of the point about which the object
     *                       is being rotated, specified as an absolute number where 0 is the
     *                       top edge. This value can either be an absolute number if
     *                       pivotYType is ABSOLUTE, or a percentage (where 1.0 is 100%)
     *                       otherwise.
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     * @return rotation animation
     */
    public static RotateAnimation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType,
                                                     float pivotXValue, int pivotYType, float pivotYValue,
                                                     long durationMillis, Animation.AnimationListener listener) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees,
                toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(durationMillis);
        if (listener != null) {
            rotateAnimation.setAnimationListener(listener);
        }
        return rotateAnimation;
    }

    /**
     * get an animation rotate by view center point.
     *
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     * @return animation rotate by view center point
     */
    public static RotateAnimation getRotateAnimationByCenter(long durationMillis, Animation.AnimationListener listener) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, durationMillis,
                listener);
    }

    /**
     * get an animation rotate by view center point.
     *
     * @param durationMillis Duration in milliseconds
     * @return animation rotate by view center point
     */
    public static RotateAnimation getRotateAnimationByCenter(long durationMillis) {
        return getRotateAnimationByCenter(durationMillis, null);
    }

    /**
     * get an animation rotate by view center point.
     *
     * @param listener the animation listener to be notified
     * @return animation rotate by view center point
     */
    public static RotateAnimation getRotateAnimationByCenter(Animation.AnimationListener listener) {
        return getRotateAnimationByCenter(AnimationHelper.DEFAULT_ANIMATION_DURATION, listener);
    }

    /**
     * get an animation rotate by view center point.
     * animation duration is {@link AnimationHelper#DEFAULT_ANIMATION_DURATION}
     *
     * @return animation rotate by view center point
     */
    public static RotateAnimation getRotateAnimationByCenter() {
        return getRotateAnimationByCenter(AnimationHelper.DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * get an alpha animation.
     *
     * @param fromAlpha      Starting alpha value for the animation, where 1.0 means
     *                       fully opaque and 0.0 means fully transparent.
     * @param toAlpha        Ending alpha value for the animation.
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     * @return An animation that controls the alpha level of an object.
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis,
                                                   Animation.AnimationListener listener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(durationMillis);
        if (listener != null) {
            alphaAnimation.setAnimationListener(listener);
        }
        return alphaAnimation;
    }

    /**
     * get an alpha animation.
     *
     * @param fromAlpha      Starting alpha value for the animation, where 1.0 means
     *                       fully opaque and 0.0 means fully transparent.
     * @param toAlpha        Ending alpha value for the animation.
     * @param durationMillis Duration in milliseconds
     * @return An animation that controls the alpha level of an object.
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis) {
        return getAlphaAnimation(fromAlpha, toAlpha, durationMillis, null);
    }

    /**
     * get an alpha animation. duration is {@link AnimationHelper#DEFAULT_ANIMATION_DURATION}.
     *
     * @param fromAlpha Starting alpha value for the animation, where 1.0 means
     *                  fully opaque and 0.0 means fully transparent.
     * @param toAlpha   Ending alpha value for the animation.
     * @param listener  the animation listener to be notified
     * @return An animation that controls the alpha level of an object.
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, Animation.AnimationListener listener) {
        return getAlphaAnimation(fromAlpha, toAlpha, AnimationHelper.DEFAULT_ANIMATION_DURATION, listener);
    }

    /**
     * get an alpha animation. duration is {@link AnimationHelper#DEFAULT_ANIMATION_DURATION}.
     *
     * @param fromAlpha Starting alpha value for the animation, where 1.0 means
     *                  fully opaque and 0.0 means fully transparent.
     * @param toAlpha   Ending alpha value for the animation.
     * @return An animation that controls the alpha level of an object.
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha) {
        return getAlphaAnimation(fromAlpha, toAlpha, AnimationHelper.DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * get an animation from visible to invisible by changing alpha.
     *
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     * @return an animation from visible to invisible by changing alpha
     */
    public static AlphaAnimation getVisibleAlphaAnimation(long durationMillis, Animation.AnimationListener listener) {
        return getAlphaAnimation(1.0f, 0.0f, durationMillis, listener);
    }

    /**
     * get an animation from visible to invisible by changing alpha.
     *
     * @param durationMillis Duration in milliseconds
     * @return an animation from visible to invisible by changing alpha
     */
    public static AlphaAnimation getVisibleAlphaAnimation(long durationMillis) {
        return getVisibleAlphaAnimation(durationMillis, null);
    }

    /**
     * get an animation from visible to invisible by changing alpha.
     * default duration is {@link AnimationHelper#DEFAULT_ANIMATION_DURATION}
     *
     * @param listener the animation listener to be notified
     * @return an animation from visible to invisible by changing alpha
     */
    public static AlphaAnimation getVisibleAlphaAnimation(Animation.AnimationListener listener) {
        return getVisibleAlphaAnimation(AnimationHelper.DEFAULT_ANIMATION_DURATION, listener);
    }

    /**
     * get an animation from visible to invisible by changing alpha.
     * default duration is {@link AnimationHelper#DEFAULT_ANIMATION_DURATION}
     *
     * @return an animation from visible to invisible by changing alpha
     */
    public static AlphaAnimation getVisibleAlphaAnimation() {
        return getVisibleAlphaAnimation(AnimationHelper.DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * get an animation from invisible to visible by changing alpha.
     *
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     * @return an animation from invisible to visible by changing alpha
     */
    public static AlphaAnimation getInvisibleAlphaAnimation(long durationMillis, Animation.AnimationListener listener) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, listener);
    }

    /**
     * get an animation from invisible to visible by changing alpha.
     *
     * @param durationMillis Duration in milliseconds
     * @return an animation from invisible to visible by changing alpha
     */
    public static AlphaAnimation getInvisibleAlphaAnimation(long durationMillis) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, null);
    }

    /**
     * get an animation from invisible to visible by changing alpha.
     * default duration is {@link AnimationHelper#DEFAULT_ANIMATION_DURATION}
     *
     * @param listener the animation listener to be notified
     * @return an animation from invisible to visible by changing alpha
     */
    public static AlphaAnimation getInvisibleAlphaAnimation(Animation.AnimationListener listener) {
        return getAlphaAnimation(0.0f, 1.0f, AnimationHelper.DEFAULT_ANIMATION_DURATION, listener);
    }

    /**
     * get an animation from invisible to visible by changing alpha.
     * default duration is {@link AnimationHelper#DEFAULT_ANIMATION_DURATION}
     *
     * @return an animation from invisible to visible by changing alpha
     */
    public static AlphaAnimation getInvisibleAlphaAnimation() {
        return getAlphaAnimation(0.0f, 1.0f, AnimationHelper.DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * get a lessen scale animation
     *
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     * @return An animation that controls the lessen scale of an object
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis, Animation.AnimationListener listener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
                0.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }

    /**
     * get a lessen scale animation
     *
     * @param durationMillis Duration in milliseconds
     * @return An animation that controls the lessen scale of an object
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis) {
        return getLessenScaleAnimation(durationMillis, null);

    }

    /**
     * get a lessen scale animation
     *
     * @param listener the animation listener to be notified
     * @return An animation that controls the lessen scale of an object
     */
    public static ScaleAnimation getLessenScaleAnimation(Animation.AnimationListener listener) {
        return getLessenScaleAnimation(AnimationHelper.DEFAULT_ANIMATION_DURATION, listener);

    }

    /**
     * get a amplification scale animation
     *
     * @param durationMillis Duration in milliseconds
     * @param listener       the animation listener to be notified
     * @return An animation that controls the amplification scale of an object
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis, Animation.AnimationListener listener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
                1.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }

    /**
     * get a amplification scale animation
     *
     * @param durationMillis Duration in milliseconds
     * @return An animation that controls the amplification scale of an object
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis) {
        return getAmplificationAnimation(durationMillis, null);
    }

    /**
     * get a amplification scale animation
     *
     * @param listener the animation listener to be notified
     * @return An animation that controls the amplification scale of an object
     */
    public static ScaleAnimation getAmplificationAnimation(Animation.AnimationListener listener) {
        return getAmplificationAnimation(AnimationHelper.DEFAULT_ANIMATION_DURATION, listener);
    }
}
