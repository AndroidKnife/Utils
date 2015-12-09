package com.hwangjr.utils.json.reflect;

/**
 * A simple utility class used to check method Preconditions.
 * <p/>
 * <pre>
 * public long divideBy(long value) {
 *   Preconditions.checkArgument(value != 0);
 *   return this.value / value;
 * }
 * </pre>
 */
public final class Preconditions {
    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    public static void checkArgument(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }
}