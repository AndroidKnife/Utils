package com.hwangjr.utils.collection;


import com.hwangjr.utils.basic.ObjectHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapHelper {
    /**
     * default separator between key and value
     **/
    public static final String DEFAULT_KEY_AND_VALUE_SEPARATOR = ":";
    /**
     * default separator between key-value pairs
     **/
    public static final String DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR = ",";

    private MapHelper() {
        throw new AssertionError();
    }

    /**
     * is null or its size is 0
     * <p/>
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1, 2})    =   false;
     * </pre>
     *
     * @param sourceMap
     * @return if map is null or its size is 0, return true, else return false.
     */
    public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
        return (sourceMap == null || sourceMap.size() == 0);
    }

    /**
     * add key-value pair to map, and key need not null or empty
     *
     * @param map
     * @param key
     * @param value
     * @return <ul>
     * <li>if map is null, return false</li>
     * <li>if key is null or empty, return false</li>
     * <li>return {@link Map#put(Object, Object)}</li>
     * </ul>
     */
    public static boolean putNotEmptyKey(Map<String, String> map, String key, String value) {
        if (map == null || ObjectHelper.isEmpty(key)) {
            return false;
        }

        map.put(key, value);
        return true;
    }

    /**
     * add key-value pair to map, both key and value need not null or empty
     *
     * @param map
     * @param key
     * @param value
     * @return <ul>
     * <li>if map is null, return false</li>
     * <li>if key is null or empty, return false</li>
     * <li>if value is null or empty, return false</li>
     * <li>return {@link Map#put(Object, Object)}</li>
     * </ul>
     */
    public static boolean putNotEmptyKeyAndValue(Map<String, String> map, String key, String value) {
        if (map == null || ObjectHelper.isEmpty(key) || ObjectHelper.isEmpty(value)) {
            return false;
        }

        map.put(key, value);
        return true;
    }

    /**
     * add key-value pair to map, key need not null or empty
     *
     * @param map
     * @param key
     * @param value
     * @param defaultValue
     * @return <ul>
     * <li>if map is null, return false</li>
     * <li>if key is null or empty, return false</li>
     * <li>if value is null or empty, put defaultValue, return true</li>
     * <li>if value is neither null nor empty，put value, return true</li>
     * </ul>
     */
    public static boolean putNotEmptyKeyAndValue(Map<String, String> map, String key, String value,
                                                 String defaultValue) {
        if (map == null || ObjectHelper.isEmpty(key)) {
            return false;
        }

        map.put(key, ObjectHelper.isEmpty(value) ? defaultValue : value);
        return true;
    }

    /**
     * add key-value pair to map, key need not null
     *
     * @param map
     * @param key
     * @param value
     * @return <ul>
     * <li>if map is null, return false</li>
     * <li>if key is null, return false</li>
     * <li>return {@link Map#put(Object, Object)}</li>
     * </ul>
     */
    public static <K, V> boolean putNotEmptyKey(Map<K, V> map, K key, V value) {
        if (map == null || key == null) {
            return false;
        }

        map.put(key, value);
        return true;
    }

    /**
     * add key-value pair to map, both key and value need not null
     *
     * @param map
     * @param key
     * @param value
     * @return <ul>
     * <li>if map is null, return false</li>
     * <li>if key is null, return false</li>
     * <li>if value is null, return false</li>
     * <li>return {@link Map#put(Object, Object)}</li>
     * </ul>
     */
    public static <K, V> boolean putNotEmptyKeyAndValue(Map<K, V> map, K key, V value) {
        if (map == null || key == null || value == null) {
            return false;
        }

        map.put(key, value);
        return true;
    }

    /**
     * get key by value, match the first entry front to back
     * <ul>
     * <strong>Attentions:</strong>
     * <li>for HashMap, the order of entry not same to put order, so you may need to use TreeMap</li>
     * </ul>
     *
     * @param <V>
     * @param map
     * @param value
     * @return <ul>
     * <li>if map is null, return null</li>
     * <li>if value exist, return key</li>
     * <li>return null</li>
     * </ul>
     */
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        if (isEmpty(map)) {
            return null;
        }

        for (Entry<K, V> entry : map.entrySet()) {
            if (ObjectHelper.isEquals(entry.getValue(), value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * parse key-value pairs to map, ignore empty key
     * <p/>
     * <pre>
     * parseToMap("","","",true)=null
     * parseToMap(null,"","",true)=null
     * parseToMap("a:b,:","","",true)={(a,b)}
     * parseToMap("a:b,:d","","",true)={(a,b)}
     * parseToMap("a:b,c:d","","",true)={(a,b),(c,d)}
     * parseToMap("a=b, c = d","=",",",true)={(a,b),(c,d)}
     * parseToMap("a=b, c = d","=",",",false)={(a, b),( c , d)}
     * parseToMap("a=b, c=d","=", ",", false)={(a,b),( c,d)}
     * parseToMap("a=b; c=d","=", ";", false)={(a,b),( c,d)}
     * parseToMap("a=b, c=d", ",", ";", false)={(a=b, c=d)}
     * </pre>
     *
     * @param source                   key-value pairs
     * @param keyAndValueSeparator     separator between key and value
     * @param keyAndValuePairSeparator separator between key-value pairs
     * @param ignoreSpace              whether ignore space at the begging or end of key and value
     * @return
     */
    public static Map<String, String> parseToMap(String source, String keyAndValueSeparator,
                                                 String keyAndValuePairSeparator, boolean ignoreSpace) {
        if (ObjectHelper.isEmpty(source)) {
            return null;
        }

        if (ObjectHelper.isEmpty(keyAndValueSeparator)) {
            keyAndValueSeparator = DEFAULT_KEY_AND_VALUE_SEPARATOR;
        }
        if (ObjectHelper.isEmpty(keyAndValuePairSeparator)) {
            keyAndValuePairSeparator = DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR;
        }
        Map<String, String> keyAndValueMap = new HashMap<>();
        String[] keyAndValueArray = source.split(keyAndValuePairSeparator);
        if (keyAndValueArray == null) {
            return null;
        }

        for (String valueEntity : keyAndValueArray) {
            if (!ObjectHelper.isEmpty(valueEntity)) {
                int separatorIndex = valueEntity.indexOf(keyAndValueSeparator);
                if (separatorIndex != -1) {
                    if (ignoreSpace) {
                        putNotEmptyKey(keyAndValueMap, valueEntity.substring(0, separatorIndex).trim(),
                                valueEntity.substring(separatorIndex + 1).trim());
                    } else {
                        putNotEmptyKey(keyAndValueMap, valueEntity.substring(0, separatorIndex),
                                valueEntity.substring(separatorIndex + 1));
                    }
                }
            }
        }
        return keyAndValueMap;
    }

    /**
     * parse key-value pairs to map, ignore empty key
     *
     * @param source      key-value pairs
     * @param ignoreSpace whether ignore space at the begging or end of key and value
     * @return
     * @see {@link #parseToMap(String, String, String, boolean)}, keyAndValueSeparator is
     * {@link #DEFAULT_KEY_AND_VALUE_SEPARATOR}, keyAndValuePairSeparator is
     * {@link #DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR}
     */
    public static Map<String, String> parseToMap(String source, boolean ignoreSpace) {
        return parseToMap(source, DEFAULT_KEY_AND_VALUE_SEPARATOR, DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR, ignoreSpace);
    }

    /**
     * parse key-value pairs to map, ignore empty key, ignore space at the begging or end of key and value
     *
     * @param source key-value pairs
     * @return
     * @see {@link #parseToMap(String, String, String, boolean)}, keyAndValueSeparator is
     * {@link #DEFAULT_KEY_AND_VALUE_SEPARATOR}, keyAndValuePairSeparator is
     * {@link #DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR}, ignoreSpace is true
     */
    public static Map<String, String> parseToMap(String source) {
        return parseToMap(source, DEFAULT_KEY_AND_VALUE_SEPARATOR, DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR, true);
    }
}
