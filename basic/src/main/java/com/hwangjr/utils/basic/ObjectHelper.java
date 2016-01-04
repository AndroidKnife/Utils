package com.hwangjr.utils.basic;

import java.util.Collection;
import java.util.Map;

/**
 * Compare object or convert some types
 */
public class ObjectHelper {
    private ObjectHelper() {
        throw new AssertionError();
    }

    /**
     * compare two object
     *
     * @param actual
     * @param expected
     * @return <ul>
     * <li>if both are null, return true</li>
     * <li>return actual.{@link Object#equals(Object)}</li>
     * </ul>
     */
    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    /**
     * Object to string
     * <p/>
     * <pre>
     * objectToString(null) = &quot;&quot;;
     * objectToString(&quot;&quot;) = &quot;&quot;;
     * objectToString(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {
        return (obj == null ? "" : (obj instanceof String ? (String) obj : obj.toString()));
    }

    /**
     * convert long array to Long array
     *
     * @param source
     * @return
     */
    public static Long[] transformLongArray(long[] source) {
        Long[] dest = new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }

    /**
     * convert Long array to long array
     *
     * @param source
     * @return
     */
    public static long[] transformLongArray(Long[] source) {
        long[] dest = new long[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }

    /**
     * convert int array to Integer array
     *
     * @param source
     * @return
     */
    public static Integer[] transformIntArray(int[] source) {
        Integer[] dest = new Integer[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }

    /**
     * convert Integer array to int array
     *
     * @param source
     * @return
     */
    public static int[] transformIntArray(Integer[] source) {
        int[] dest = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }

    /**
     * compare two object
     * <ul>
     * <strong>About result</strong>
     * <li>if v1 > v2, return 1</li>
     * <li>if v1 = v2, return 0</li>
     * <li>if v1 < v2, return -1</li>
     * </ul>
     * <ul>
     * <strong>About rule</strong>
     * <li>if v1 is null, v2 is null, then return 0</li>
     * <li>if v1 is null, v2 is not null, then return -1</li>
     * <li>if v1 is not null, v2 is null, then return 1</li>
     * <li>return v1.{@link Comparable#compareTo(Object)}</li>
     * </ul>
     *
     * @param v1
     * @param v2
     * @return
     */
    public static <V> int compare(V v1, V v2) {
        return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable) v1).compareTo(v2));
    }

    /**
     * is null or its size is 0
     * <p/>
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     *
     * @param <V>
     * @param collection
     * @return if collection is null or its size is 0, return true, else return false.
     */
    public static <V> boolean isEmpty(Collection<V> collection) {
        return null == collection || collection.isEmpty();
    }


    /**
     * is null or its size is 0
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return if map is null or its size is 0, return true, else return false.
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return null == map || map.isEmpty();
    }

    /**
     * is null or its size is 0
     *
     * @param objs
     * @return if array is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(Object[] objs) {
        return null == objs || objs.length <= 0;
    }

    /**
     * is null or its size is 0
     *
     * @param objs
     * @return if array is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(int[] objs) {
        return null == objs || objs.length <= 0;
    }

    /**
     * is null or its length is 0
     *
     * @param charSequence
     * @return if char sequence is null or its length is 0, return true, else return false.
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return null == charSequence || charSequence.length() <= 0;
    }

    /**
     * is null
     *
     * @param obj
     * @return if array is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(Object obj) {
        return null == obj;
    }

}
