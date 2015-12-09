package com.hwangjr.utils.json;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This is a utility to get the value from JSON by key.
 */
public class JSONGetter {
    /**
     * Get string from JSONObject.
     *
     * @param json JSONObject
     * @param key  key of the value
     * @return value of the key, if the value is null then return ""
     */
    public static String getString(JSONObject json, String key) {
        return getString(json, key, "");
    }

    /**
     * Get string from JSONObject.
     *
     * @param json         JSONObject
     * @param key          key of the value
     * @param defaultValue default value
     * @return value of the key, if the value is null then return {@code defaultValue}
     */
    public static String getString(JSONObject json, String key, String defaultValue) {
        try {
            return json.has(key) ? json.getString(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get int from JSONObject.
     *
     * @param json JSONObject
     * @param key  key of the value
     * @return value of the key, if the value is null then return 0
     */
    public static int getInt(JSONObject json, String key) {
        return getInt(json, key, 0);
    }

    /**
     * Get int from JSONObject.
     *
     * @param json         JSONObject
     * @param key          key of the value
     * @param defaultValue default value
     * @return value of the key, if the value is null then return {@code defaultValue}
     */
    public static int getInt(JSONObject json, String key, int defaultValue) {
        try {
            return json.has(key) ? json.getInt(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get long from JSONObject.
     *
     * @param json JSONObject
     * @param key  key of the value
     * @return value of the key, if the value is null then return 0l
     */
    public static long getLong(JSONObject json, String key) {
        return getLong(json, key, 0l);
    }

    /**
     * Get long from JSONObject.
     *
     * @param json         JSONObject
     * @param key          key of the value
     * @param defaultValue default value
     * @return value of the key, if the value is null then return {@code defaultValue}
     */
    public static long getLong(JSONObject json, String key, long defaultValue) {
        try {
            return json.has(key) ? json.getLong(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get boolean from JSONObject.
     *
     * @param json JSONObject
     * @param key  key of the value
     * @return value of the key, if the value is null then return false
     */
    public static boolean getBoolean(JSONObject json, String key) {
        return getBoolean(json, key, false);
    }

    /**
     * Get boolean from JSONObject.
     *
     * @param json         JSONObject
     * @param key          key of the value
     * @param defaultValue default value
     * @return value of the key, if the value is null then return {@code defaultValue}
     */
    public static boolean getBoolean(JSONObject json, String key, boolean defaultValue) {
        try {
            return json.has(key) ? json.getBoolean(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get Double from JSONObject.
     *
     * @param json JSONObject
     * @param key  key of the value
     * @return value of the key, if the value is null then return 0d
     */
    public static double getDouble(JSONObject json, String key) {
        return getDouble(json, key, 0d);
    }

    /**
     * Get Double from JSONObject.
     *
     * @param json         JSONObject
     * @param key          key of the value
     * @param defaultValue default value
     * @return value of the key, if the value is null then return {@code defaultValue}
     */
    public static double getDouble(JSONObject json, String key, double defaultValue) {
        try {
            return json.has(key) ? json.getDouble(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get {@link JSONObject} from JSONObject.
     *
     * @param json JSONObject
     * @param key  key of the value
     * @return value of the key, if the value is null then return null
     */
    public static JSONObject getJSONObject(JSONObject json, String key) {
        return getJSONObject(json, key, null);
    }

    /**
     * Get {@link JSONObject} from JSONObject.
     *
     * @param json         JSONObject
     * @param key          key of the value
     * @param defaultValue default value
     * @return value of the key, if the value is null then return {@code defaultValue}
     */
    public static JSONObject getJSONObject(JSONObject json, String key, JSONObject defaultValue) {
        try {
            return json.has(key) ? json.getJSONObject(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get {@link JSONArray} from JSONObject.
     *
     * @param json JSONObject
     * @param key  key of the value
     * @return value of the key, if the value is null then return null
     */
    public static JSONArray getJSONArray(JSONObject json, String key) {
        return getJSONArray(json, key, null);
    }

    /**
     * Get {@link JSONArray} from JSONObject.
     *
     * @param json         JSONObject
     * @param key          key of the value
     * @param defaultValue default value
     * @return value of the key, if the value is null then return {@code defaultValue}
     */
    public static JSONArray getJSONArray(JSONObject json, String key, JSONArray defaultValue) {
        try {
            return json.has(key) ? json.getJSONArray(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Whether the {@link JSONArray} is empty or not
     *
     * @param jsonArray The array to check
     * @return true if the array is empty, false if not
     */
    public static boolean isEmpty(JSONArray jsonArray) {
        return null == jsonArray || jsonArray.length() <= 0;
    }
}
