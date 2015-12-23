package com.hwangjr.utils.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Wrapper of Gson.
 */
public class GSONWrapper {

    private Gson mGson;

    /**
     * Build the default gson.
     * This will set the field name strategy to {@link JFieldNamingStrategy} and
     * exclusion strategy to {@link JExclusionStrategy}
     *
     * @return gson
     */
    public static Gson buildGson() {
        GsonBuilder builder = new GsonBuilder().setFieldNamingStrategy(
                new JFieldNamingStrategy()).setExclusionStrategies(
                new JExclusionStrategy());
        Gson gson = builder.create();
        return gson;
    }

    public synchronized Gson getGson() {
        if (mGson == null) {
            mGson = buildGson();
        }
        return mGson;
    }

    /**
     * convert object to json string.
     *
     * @param src object
     * @return string of json
     */
    public String toJson(Object src) {
        return getGson().toJson(src);
    }

    /**
     * convert json string to the object.
     *
     * @param json  json string
     * @param clazz object
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }

    /**
     * convert json string to the object.
     *
     * @param json json string
     * @param type type of object
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, Type type) {
        return getGson().fromJson(json, type);
    }

    private static class JFieldNamingStrategy implements FieldNamingStrategy {

        @Override
        public String translateName(Field f) {
            JSONNode colunmInfo = f.getAnnotation(JSONNode.class);
            if (colunmInfo != null) {
                return colunmInfo.key();
            }
            return f.getName();
        }
    }

    private static class JExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(JSONNode.class) == null;
        }
    }
}
