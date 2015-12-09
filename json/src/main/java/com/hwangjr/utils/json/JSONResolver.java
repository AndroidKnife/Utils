package com.hwangjr.utils.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the main class for using JSONResolver.
 * <br />
 * JSONResolver is typically used by parsing the json or the object. It's convenience to call {@link #toJson(Object)} and {@link #fromJson(String, Class)} to parse the object.
 */
public class JSONResolver {

    /**
     * Whether the clazz is the basic type.
     *
     * @param clazz the class
     * @return true if the clazz is basic type or false if not.
     */
    public static boolean isBasicType(Class<?> clazz) {
        if (clazz.isPrimitive() || String.class.isAssignableFrom(clazz)) {
            return true;
        }
        return isWrapType(clazz);
    }

    private static boolean isWrapType(Class<?> clazz) {
        try {
            if (((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Convert the object to json string, this will return a string formatted by json.
     *
     * @param src source object
     * @return json string
     * @throws Exception
     */
    public static String toJson(Object src) throws Exception {
        return toJson(src, src.getClass());
    }

    /**
     * Convert the object to json string, this will return a string formatted by json.
     *
     * @param src        source object
     * @param classOfSrc the class of this object
     * @return json string
     * @throws Exception
     */
    public static String toJson(Object src, Class<?> classOfSrc)
            throws Exception {
        if (isBasicType(classOfSrc)) {
            return src.toString();
        } else if (List.class.isAssignableFrom(classOfSrc)) {
            JSONArray jsonArray = new JSONArray();
            List<?> list = (List<?>) src;
            for (Object object : list) {
                jsonArray.put(new JSONObject(toJson(object)));
            }
            return jsonArray.toString();
        } else if (classOfSrc.isArray()) {
            JSONArray jsonArray = new JSONArray();
            Object[] objArray = (Object[]) src;
            for (Object object : objArray) {
                jsonArray.put(new JSONObject(toJson(object)));
            }
            return jsonArray.toString();
        } else {
            return object2Json(src);
        }
    }

    /**
     * Convert object to json string.
     */
    private static String object2Json(Object entity) throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<Field> fields = getAllFields(entity.getClass());
        for (Field field : fields) {
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            JSONNode jsonNode = field.getAnnotation(JSONNode.class);
            if (jsonNode != null) {
                Object value = field.get(entity);
                if (value != null) {
                    String jsonKey = jsonNode.key();
                    Class<?> fieldClazz = field.getType();
                    if (isBasicType(fieldClazz)) {
                        if (!jsonObject.has(jsonKey)) {
                            jsonObject.put(jsonKey, value);
                        } else {
                            Log.w("JSON::",
                                    "class["
                                            + field.getDeclaringClass()
                                            .getSimpleName()
                                            + "] declares multiple JSON fields named name["
                                            + jsonKey + "]..");
                        }
                    } else {
                        if (List.class.isAssignableFrom(fieldClazz)) {
                            JSONArray jsonArray = new JSONArray();
                            List<?> list = (List<?>) value;
                            for (Object object : list) {
                                if (isBasicType(object.getClass())) {
                                    jsonArray.put(object);
                                } else {
                                    jsonArray
                                            .put(new JSONObject(toJson(object)));
                                }
                            }
                            jsonObject.put(jsonKey, jsonArray);
                        } else if (fieldClazz.isArray()) {
                            JSONArray jsonArray = new JSONArray();
                            Object[] objArray = (Object[]) value;
                            for (Object object : objArray) {
                                if (isBasicType(object.getClass())) {
                                    jsonArray.put(object);
                                } else {
                                    jsonArray
                                            .put(new JSONObject(toJson(object)));
                                }
                            }
                            jsonObject.put(jsonKey, jsonArray);
                        } else {
                            jsonObject.put(jsonKey, new JSONObject(
                                    toJson(value)));
                        }
                    }
                }
            }
        }
        return jsonObject.toString();
    }

    /**
     * Convert json string to object.
     *
     * @param json    source json string
     * @param typeOfT type of the object
     * @param <T>     class of the expected object
     * @return
     * @throws Exception
     */
    public static <T> T fromJson(String json, Type typeOfT) throws Exception {
        if (typeOfT instanceof Class) {
            // Object
            return (T) fromJson(new JSONObject(json),
                    TypeToken.getRawType(typeOfT));
        } else if (typeOfT instanceof ParameterizedType) {
            // List
            JSONArray jsonArray = new JSONArray(json);
            return (T) jsonArray2List(jsonArray, TypeToken.getRawType(typeOfT),
                    typeOfT);
        } else if (typeOfT instanceof GenericArrayType) {
            // Array
            JSONArray jsonArray = new JSONArray(json);
            return (T) jsonArray2Array(jsonArray, TypeToken.getRawType(typeOfT));
        } else {
            String className = typeOfT == null ? "null" : typeOfT.getClass()
                    .getName();
            throw new IllegalArgumentException(
                    "Expected a Class, ParameterizedType, or "
                            + "GenericArrayType, but <" + typeOfT
                            + "> is of type " + className);
        }
    }

    /**
     * Convert json string to object.
     *
     * @param json     source json string
     * @param classOfT class of object
     * @param <T>      class of object
     * @return
     * @throws Exception
     */
    public static <T> T fromJson(String json, Class<T> classOfT)
            throws Exception {
        return fromJson(new JSONObject(json), classOfT);
    }

    /**
     * Convert json to object.
     */
    private static <T> T fromJson(JSONObject jsonObject, Class<T> classOfT)
            throws Exception {
        T entity = classOfT.newInstance();
        List<Field> fields = getAllFields(classOfT);
        for (Field field : fields) {
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            try {
                fillField(entity, field, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return entity;
    }

    private static void fillField(Object entity, Field field,
                                  JSONObject jsonObject) throws Exception {
        JSONNode jsonNode = field.getAnnotation(JSONNode.class);
        if (jsonNode != null) {
            String jsonKey = jsonNode.key();
            if (jsonObject.has(jsonKey)) {
                Class<?> fieldClazz = field.getType();
                if (isBasicType(fieldClazz)) {// 基础类型
                    Object value = jsonObject.get(jsonKey);
                    jsonObject.remove(jsonKey);
                    try {
                        if (isWrapType(fieldClazz)
                                || String.class.isAssignableFrom(fieldClazz)) {
                            // If it's wrapped type, then must have the constructs
                            Constructor<?> cons = fieldClazz
                                    .getConstructor(String.class);
                            Object attrValue = cons.newInstance(value
                                    .toString());
                            field.set(entity, attrValue);
                        } else {
                            field.set(entity, value);
                        }
                    } catch (Exception e) {
                        String error = "invalid value[" + value
                                + "] for field[" + field.getName()
                                + "]; valueClass[" + value.getClass()
                                + "]; annotationName[" + jsonKey + "]";
                        throw new JSONException(error);
                    }
                } else {
                    Object objValue = jsonObject.get(jsonKey);
                    if (objValue != null && !objValue.equals(null)) {
                        if (List.class.isAssignableFrom(fieldClazz)) {
                            List<Object> listValue = jsonArray2List(
                                    jsonObject.getJSONArray(jsonKey),
                                    fieldClazz, field.getGenericType());
                            field.set(entity, listValue);
                        } else if (fieldClazz.isArray()) {
                            field.set(
                                    entity,
                                    jsonArray2Array(
                                            jsonObject.getJSONArray(jsonKey),
                                            fieldClazz));
                        } else {
                            field.set(
                                    entity,
                                    fromJson(jsonObject.getJSONObject(jsonKey),
                                            fieldClazz));
                        }
                    }
                }
            }
        }
    }

    /**
     * convert json array to list.
     */
    private static List<Object> jsonArray2List(JSONArray jsonArray,
                                               Class<?> listClazz, Type type) throws Exception {
        List<Object> listValue = null;
        if (jsonArray != null) {
            if (type instanceof ParameterizedType) {
                if (listClazz.isInterface()
                        || Modifier.isAbstract(listClazz.getModifiers())) {
                    listClazz = ArrayList.class;
                }
                listValue = (List<Object>) listClazz.newInstance();
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> paramClazz = (Class<?>) parameterizedType
                        .getActualTypeArguments()[0];
                int jsonArrayLength = jsonArray.length();
                for (int i = 0; i < jsonArrayLength; i++) {
                    if (isBasicType(paramClazz)) {
                        listValue.add(jsonArray.get(i));
                    } else {
                        listValue.add(fromJson(jsonArray.getJSONObject(i),
                                paramClazz));
                    }
                }
            }
        }
        return listValue;
    }

    /**
     * convert json array to array.
     */
    private static Object jsonArray2Array(JSONArray jsonArray, Class<?> rawType)
            throws Exception {
        Class<?> componentType = rawType.getComponentType();
        int len = jsonArray.length();
        Object array = Array.newInstance(componentType, len);
        for (int i = 0; i < len; i++) {
            Object objValue;
            if (isBasicType(componentType)) {
                objValue = jsonArray.get(i);
            } else {
                objValue = fromJson(jsonArray.getJSONObject(i), componentType);
            }
            Array.set(array, i, objValue);
        }
        return array;
    }

    private static List<Field> getAllFields(Class<?> objClass) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = objClass.getDeclaredFields();
        Collections.addAll(fields, declaredFields);
        fillSuperFields(fields, objClass);
        return fields;
    }

    private static void fillSuperFields(List<Field> fields, Class<?> subClass) {
        Class<?> superClass = subClass.getSuperclass();
        if (superClass != null) {
            Field[] superFields = superClass.getDeclaredFields();
            if (superFields.length > 0) {
                Collections.addAll(fields, superFields);
                fillSuperFields(fields, superClass);
            }
        }
    }

    public static abstract class TypeToken<T> {
        final Class<? super T> rawType;
        final Type type;

        protected TypeToken() {
            this.type = getSuperclassTypeParameter(getClass());
            this.rawType = (Class<? super T>) getRawType(type);
        }

        public Type getType() {
            return type;
        }

        public Class<?> getRawType() {
            return rawType;
        }

        public static Type getInterfacesTypeParameter(Class<?> subclass,
                                                      Class<?> interfClass) {
            Type[] types = subclass.getGenericInterfaces();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    if (interfClass.isAssignableFrom(getRawType(type))) {
                        ParameterizedType parameterized = (ParameterizedType) type;
                        return parameterized.getActualTypeArguments()[0];
                    }
                }
            }
            return null;
        }

        public static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return parameterized.getActualTypeArguments()[0];
        }

        public static Class<?> getRawType(Type type) {
            if (type instanceof Class<?>) {
                return (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                if (!(rawType instanceof Class)) {
                    throw new IllegalArgumentException();
                }
                return (Class<?>) rawType;
            } else if (type instanceof GenericArrayType) {
                Type componentType = ((GenericArrayType) type)
                        .getGenericComponentType();
                return Array.newInstance(getRawType(componentType), 0)
                        .getClass();
            } else if (type instanceof TypeVariable) {
                return Object.class;
            } else if (type instanceof WildcardType) {
                return getRawType(((WildcardType) type).getUpperBounds()[0]);
            } else {
                String className = type == null ? "null" : type.getClass()
                        .getName();
                throw new IllegalArgumentException(
                        "Expected a Class, ParameterizedType, or "
                                + "GenericArrayType, but <" + type
                                + "> is of type " + className);
            }
        }
    }

}
