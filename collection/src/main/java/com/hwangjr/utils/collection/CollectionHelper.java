package com.hwangjr.utils.collection;

import android.text.TextUtils;

public class CollectionHelper {
    /**
     * default join separator
     **/
    public static final CharSequence DEFAULT_JOIN_SEPARATOR = ",";

    private CollectionHelper() {
        throw new AssertionError();
    }

    /**
     * join collection to string, separator is {@link #DEFAULT_JOIN_SEPARATOR}
     * <p/>
     * <pre>
     * join(null)      =   "";
     * join({})        =   "";
     * join({a,b})     =   "a,b";
     * </pre>
     *
     * @param collection
     * @return join collection to string, separator is {@link #DEFAULT_JOIN_SEPARATOR}.
     * if collection is empty, return ""
     */
    public static String join(Iterable collection) {
        return join(collection, DEFAULT_JOIN_SEPARATOR);
    }

    /**
     * join list to string
     * <p/>
     * <pre>
     * join(null, '#')     =   "";
     * join({}, '#')       =   "";
     * join({a,b,c}, ' ')  =   "abc";
     * join({a,b,c}, '#')  =   "a#b#c";
     * </pre>
     *
     * @param collection
     * @param separator
     * @return join list to string. if list is empty, return ""
     */
    public static String join(Iterable collection, char separator) {
        return join(collection, new String(new char[]{separator}));
    }

    /**
     * join list to string. if separator is null, use {@link #DEFAULT_JOIN_SEPARATOR}
     * <p/>
     * <pre>
     * join(null, "#")     =   "";
     * join({}, "#$")      =   "";
     * join({a,b,c}, null) =   "a,b,c";
     * join({a,b,c}, "")   =   "abc";
     * join({a,b,c}, "#")  =   "a#b#c";
     * join({a,b,c}, "#$") =   "a#$b#$c";
     * </pre>
     *
     * @param collection
     * @param separator
     * @return join list to string with separator. if list is empty, return ""
     */
    public static String join(Iterable collection, CharSequence separator) {
        return collection == null ? "" : TextUtils.join(separator, collection);
    }
}
