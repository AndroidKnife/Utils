package com.hwangjr.utils.basic;

import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClassHelper {
    public static final String CLASS_EXTENSION = ".class";
    public static final String JAVA_EXTENSION = ".java";

    private static final ConcurrentMap<Class<?>, Map<String, Method>> GETTER_CACHE
            = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Class<?>> CLASS_CACHE
            = new ConcurrentHashMap<>();

    static {
        CLASS_CACHE.put("boolean", boolean.class);
        CLASS_CACHE.put("char", char.class);
        CLASS_CACHE.put("byte", byte.class);
        CLASS_CACHE.put("short", short.class);
        CLASS_CACHE.put("int", int.class);
        CLASS_CACHE.put("long", long.class);
        CLASS_CACHE.put("float", float.class);
        CLASS_CACHE.put("double", double.class);
        CLASS_CACHE.put("void", void.class);
        CLASS_CACHE.put("Boolean", Boolean.class);
        CLASS_CACHE.put("Character", Character.class);
        CLASS_CACHE.put("Byte", Byte.class);
        CLASS_CACHE.put("Short", Short.class);
        CLASS_CACHE.put("Integer", Integer.class);
        CLASS_CACHE.put("Long", Long.class);
        CLASS_CACHE.put("Float", Float.class);
        CLASS_CACHE.put("Double", Double.class);
        CLASS_CACHE.put("Number", Number.class);
        CLASS_CACHE.put("String", String.class);
        CLASS_CACHE.put("Object", Object.class);
        CLASS_CACHE.put("Class", Class.class);
        CLASS_CACHE.put("Void", Void.class);
        CLASS_CACHE.put("java.lang.Boolean", Boolean.class);
        CLASS_CACHE.put("java.lang.Character", Character.class);
        CLASS_CACHE.put("java.lang.Byte", Byte.class);
        CLASS_CACHE.put("java.lang.Short", Short.class);
        CLASS_CACHE.put("java.lang.Integer", Integer.class);
        CLASS_CACHE.put("java.lang.Long", Long.class);
        CLASS_CACHE.put("java.lang.Float", Float.class);
        CLASS_CACHE.put("java.lang.Double", Double.class);
        CLASS_CACHE.put("java.lang.Number", Number.class);
        CLASS_CACHE.put("java.lang.String", String.class);
        CLASS_CACHE.put("java.lang.Object", Object.class);
        CLASS_CACHE.put("java.lang.Class", Class.class);
        CLASS_CACHE.put("java.lang.Void", Void.class);
        CLASS_CACHE.put("java.util.Date", Date.class);
        CLASS_CACHE.put("boolean[]", boolean[].class);
        CLASS_CACHE.put("char[]", char[].class);
        CLASS_CACHE.put("byte[]", byte[].class);
        CLASS_CACHE.put("short[]", short[].class);
        CLASS_CACHE.put("int[]", int[].class);
        CLASS_CACHE.put("long[]", long[].class);
        CLASS_CACHE.put("float[]", float[].class);
        CLASS_CACHE.put("double[]", double[].class);
        CLASS_CACHE.put("Boolean[]", Boolean[].class);
        CLASS_CACHE.put("Character[]", Character[].class);
        CLASS_CACHE.put("Byte[]", Byte[].class);
        CLASS_CACHE.put("Short[]", Short[].class);
        CLASS_CACHE.put("Integer[]", Integer[].class);
        CLASS_CACHE.put("Long[]", Long[].class);
        CLASS_CACHE.put("Float[]", Float[].class);
        CLASS_CACHE.put("Double[]", Double[].class);
        CLASS_CACHE.put("Number[]", Number[].class);
        CLASS_CACHE.put("String[]", String[].class);
        CLASS_CACHE.put("Object[]", Object[].class);
        CLASS_CACHE.put("Class[]", Class[].class);
        CLASS_CACHE.put("Void[]", Void[].class);
        CLASS_CACHE.put("java.lang.Boolean[]", Boolean[].class);
        CLASS_CACHE.put("java.lang.Character[]", Character[].class);
        CLASS_CACHE.put("java.lang.Byte[]", Byte[].class);
        CLASS_CACHE.put("java.lang.Short[]", Short[].class);
        CLASS_CACHE.put("java.lang.Integer[]", Integer[].class);
        CLASS_CACHE.put("java.lang.Long[]", Long[].class);
        CLASS_CACHE.put("java.lang.Float[]", Float[].class);
        CLASS_CACHE.put("java.lang.Double[]", Double[].class);
        CLASS_CACHE.put("java.lang.Number[]", Number[].class);
        CLASS_CACHE.put("java.lang.String[]", String[].class);
        CLASS_CACHE.put("java.lang.Object[]", Object[].class);
        CLASS_CACHE.put("java.lang.Class[]", Class[].class);
        CLASS_CACHE.put("java.lang.Void[]", Void[].class);
        CLASS_CACHE.put("java.util.Date[]", Date[].class);
    }

    private ClassHelper() {
        throw new AssertionError();
    }

    /**
     * New instance for the given class name.{@link #forName(String)}
     *
     * @param className
     * @return
     */
    public static Object newInstance(String className) {
        try {
            return forName(className).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Returns a {@code Class} object which represents the class with
     * the given name. The name should be the name of a non-primitive
     * class, as described in the {@link Class class definition}.
     * Primitive types can not be found using this method; use {@code
     * int.class} or {@code Integer.TYPE} instead.
     *
     * @param packages  class package
     * @param className given class name
     * @return
     */
    public static Class<?> forName(String[] packages, String className) {
        if (packages != null && packages.length > 0 &&
                !className.contains(".") &&
                !CLASS_CACHE.containsKey(className)) {
            for (String pkg : packages) {
                try {
                    return _forName(pkg + "." + className);
                } catch (ClassNotFoundException e2) {
                }
            }
            try {
                return _forName("java.lang." + className);
            } catch (ClassNotFoundException e2) {
            }
        }
        try {
            return _forName(className);
        } catch (ClassNotFoundException e) {
            int index = className.lastIndexOf('.');
            if (index > 0 && index < className.length() - 1) {
                try {
                    return _forName(className.substring(0, index) + "$" +
                            className.substring(index + 1));
                } catch (ClassNotFoundException e2) {
                }
            }
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Returns a {@code Class} object which represents the class with
     * the given name. The name should be the name of a non-primitive
     * class, as described in the {@link Class class definition}.
     * Primitive types can not be found using this method; use {@code
     * int.class} or {@code Integer.TYPE} instead.
     *
     * @param className
     * @return
     */
    public static Class<?> forName(String className) {
        try {
            return _forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Returns a {@code Class} object which represents the class with
     * the given name. The name should be the name of a non-primitive
     * class, as described in the {@link Class class definition}.
     * Primitive types can not be found using this method; use {@code
     * int.class} or {@code Integer.TYPE} instead.
     * <p/>
     * also support for array, like int[][].
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    private static Class<?> _forName(String name)
            throws ClassNotFoundException {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        String key = name;
        Class<?> clazz = CLASS_CACHE.get(key);
        if (clazz == null) {
            int index = name.indexOf('[');
            if (index > 0) {
                int i = (name.length() - index) / 2;
                name = name.substring(0, index);
                StringBuilder sb = new StringBuilder();
                while (i-- > 0) {
                    sb.append("[");
                }
                if ("void".equals(name)) {
                    sb.append("V");
                } else if ("boolean".equals(name)) {
                    sb.append("Z");
                } else if ("byte".equals(name)) {
                    sb.append("B");
                } else if ("char".equals(name)) {
                    sb.append("C");
                } else if ("double".equals(name)) {
                    sb.append("D");
                } else if ("float".equals(name)) {
                    sb.append("F");
                } else if ("int".equals(name)) {
                    sb.append("I");
                } else if ("long".equals(name)) {
                    sb.append("J");
                } else if ("short".equals(name)) {
                    sb.append("S");
                } else {
                    sb.append('L').append(name).append(';');
                }
                name = sb.toString();
            }
            try {
                clazz = Class.forName(name, true,
                        Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException cne) {
                clazz = Class.forName(name);
            }
            Class<?> old = CLASS_CACHE.putIfAbsent(key, clazz);
            if (old != null) {
                clazz = old;
            }
        }
        return clazz;
    }

    /**
     * get unboxed type of class.
     */
    public static Class<?> getUnboxedClass(Class<?> type) {
        if (type == Boolean.class) {
            return boolean.class;
        } else if (type == Character.class) {
            return char.class;
        } else if (type == Byte.class) {
            return byte.class;
        } else if (type == Short.class) {
            return short.class;
        } else if (type == Integer.class) {
            return int.class;
        } else if (type == Long.class) {
            return long.class;
        } else if (type == Float.class) {
            return float.class;
        } else if (type == Double.class) {
            return double.class;
        } else {
            return type;
        }
    }

    /**
     * get boxed type of class
     */
    public static Class<?> getBoxedClass(Class<?> type) {
        if (type == boolean.class) {
            return Boolean.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == short.class) {
            return Short.class;
        } else if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == double.class) {
            return Double.class;
        } else {
            return type;
        }
    }

    /**
     * box the boolean to Boolean
     */
    public static Boolean boxed(boolean v) {
        return Boolean.valueOf(v);
    }

    /**
     * box the char to Character
     */
    public static Character boxed(char v) {
        return Character.valueOf(v);
    }

    /**
     * box the byte to Byte
     */
    public static Byte boxed(byte v) {
        return Byte.valueOf(v);
    }

    /**
     * box the short to Short
     */
    public static Short boxed(short v) {
        return Short.valueOf(v);
    }

    /**
     * box the int to Integer
     */
    public static Integer boxed(int v) {
        return Integer.valueOf(v);
    }

    /**
     * box the long to Long
     */
    public static Long boxed(long v) {
        return Long.valueOf(v);
    }

    /**
     * box the float to Float
     */
    public static Float boxed(float v) {
        return Float.valueOf(v);
    }

    /**
     * box the double to Double
     */
    public static Double boxed(double v) {
        return Double.valueOf(v);
    }

    /**
     * box the given type
     */
    public static <T> T boxed(T v) {
        return v;
    }

    /**
     * unbox Boolean
     */
    public static boolean unboxed(Boolean v) {
        return v == null ? false : v.booleanValue();
    }

    /**
     * unbox Character
     */
    public static char unboxed(Character v) {
        return v == null ? '\0' : v.charValue();
    }

    /**
     * unbox Byte
     */
    public static byte unboxed(Byte v) {
        return v == null ? 0 : v.byteValue();
    }

    /**
     * unbox Short
     */
    public static short unboxed(Short v) {
        return v == null ? 0 : v.shortValue();
    }

    /**
     * unbox Integer
     */
    public static int unboxed(Integer v) {
        return v == null ? 0 : v.intValue();
    }

    /**
     * unbox Long
     */
    public static long unboxed(Long v) {
        return v == null ? 0 : v.longValue();
    }

    /**
     * unbox Float
     */
    public static float unboxed(Float v) {
        return v == null ? 0 : v.floatValue();
    }

    /**
     * unbox Double
     */
    public static double unboxed(Double v) {
        return v == null ? 0 : v.doubleValue();
    }

    /**
     * unbox the given type
     */
    public static <T> T unboxed(T v) {
        return v;
    }

    /**
     * whether is true
     */
    public static boolean isTrue(boolean object) {
        return object;
    }

    /**
     * whether the char equals '\0'
     */
    public static boolean isTrue(char object) {
        return object != '\0';
    }

    /**
     * whether the byte == 0
     */
    public static boolean isTrue(byte object) {
        return object != (byte) 0;
    }

    /**
     * whether the short == 0
     */
    public static boolean isTrue(short object) {
        return object != (short) 0;
    }

    /**
     * whether the int == 0
     */
    public static boolean isTrue(int object) {
        return object != 0;
    }

    /**
     * whether the long == 0L
     */
    public static boolean isTrue(long object) {
        return object != 0l;
    }

    /**
     * whether the float == 0F
     */
    public static boolean isTrue(float object) {
        return object != 0f;
    }

    /**
     * whether the double == 0D
     */
    public static boolean isTrue(double object) {
        return object != 0d;
    }

    /**
     * whether the boolean is true or {@link #getSize(Object)} is 0
     */
    public static boolean isTrue(Object object) {
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue();
        }
        return getSize(object) != 0;
    }

    /**
     * {@link #isTrue(Object)}
     */
    public static boolean isNotEmpty(Object object) {
        return isTrue(object);
    }

    /**
     * get size of the object. if type is collection or map, then it will return it's size.
     * if it's array, it will return length.
     * otherwise it will return -1.
     */
    public static int getSize(Object object) {
        if (object == null) {
            return 0;
        } else if (object instanceof Collection<?>) {
            return ((Collection<?>) object).size();
        } else if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).size();
        } else if (object instanceof Object[]) {
            return ((Object[]) object).length;
        } else if (object instanceof int[]) {
            return ((int[]) object).length;
        } else if (object instanceof long[]) {
            return ((long[]) object).length;
        } else if (object instanceof float[]) {
            return ((float[]) object).length;
        } else if (object instanceof double[]) {
            return ((double[]) object).length;
        } else if (object instanceof short[]) {
            return ((short[]) object).length;
        } else if (object instanceof byte[]) {
            return ((byte[]) object).length;
        } else if (object instanceof char[]) {
            return ((char[]) object).length;
        } else if (object instanceof boolean[]) {
            return ((boolean[]) object).length;
        } else {
            return -1;
        }
    }

    /**
     * build method by name and parameters, like: method(type1,type2)
     */
    public static String getMethodFullName(String name, Class<?>[] parameterTypes) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append("(");
        if (parameterTypes != null && parameterTypes.length > 0) {
            boolean first = true;
            for (Class<?> type : parameterTypes) {
                if (type != null) {
                    if (first) {
                        first = false;
                    } else {
                        builder.append(",");
                    }
                    builder.append(type.getCanonicalName());
                }
            }
        }
        builder.append(")");
        return builder.toString();
    }

    /**
     * get generic class type, default actual type argument index is 0.
     */
    public static Class<?> getGenericClass(Class<?> cls) {
        return getGenericClass(cls, 0);
    }

    /**
     * get generic class by actual type argument index.
     */
    public static Class<?> getGenericClass(Class<?> cls, int actualTypeArgIndex) {
        try {
            ParameterizedType parameterizedType;
            if (cls.getGenericInterfaces().length > 0 &&
                    cls.getGenericInterfaces()[0] instanceof ParameterizedType) {
                parameterizedType
                        = ((ParameterizedType) cls.getGenericInterfaces()[0]);
            } else if (cls.getGenericSuperclass() instanceof ParameterizedType) {
                parameterizedType
                        = (ParameterizedType) cls.getGenericSuperclass();
            } else {
                parameterizedType = null;
            }
            if (parameterizedType != null) {
                Object genericClass
                        = parameterizedType.getActualTypeArguments()[actualTypeArgIndex];
                if (genericClass instanceof ParameterizedType) {
                    return (Class<?>) ((ParameterizedType) genericClass).getRawType();
                } else if (genericClass instanceof GenericArrayType) {
                    Class<?> componentType
                            = (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
                    if (componentType.isArray()) {
                        return componentType;
                    } else {
                        return Array.newInstance(componentType, 0).getClass();
                    }
                } else if (genericClass instanceof Class) {
                    return (Class<?>) genericClass;
                }
            }
        } catch (Exception e) {
        }
        if (cls.getSuperclass() != null &&
                cls.getSuperclass() != Object.class) {
            return getGenericClass(cls.getSuperclass(), actualTypeArgIndex);
        } else {
            throw new IllegalArgumentException(
                    cls.getName() + " generic type undefined!");
        }
    }

    /**
     * get java version, the property of 'java.specification.version'.
     */
    public static String getJavaVersion() {
        return System.getProperty("java.specification.version");
    }

    /**
     * whether the java version is before java 5.
     * compare with 1.0, 1.1, 1.2, 1.3, 1.4
     */
    public static boolean isBeforeJava5(String javaVersion) {
        return (TextUtils.isEmpty(javaVersion) || "1.0".equals(javaVersion) ||
                "1.1".equals(javaVersion) || "1.2".equals(javaVersion) ||
                "1.3".equals(javaVersion) || "1.4".equals(javaVersion));
    }

    /**
     * whether the java version is before java 6.
     * compare with 1.5 and {@link #isBeforeJava5(String)}
     */
    public static boolean isBeforeJava6(String javaVersion) {
        return isBeforeJava5(javaVersion) || "1.5".equals(javaVersion);
    }

    /**
     * make throwable to string.
     */
    public static String toString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.print(throwable.getClass().getName() + ": ");
        if (throwable.getMessage() != null) {
            printWriter.print(throwable.getMessage() + "\n");
        }
        printWriter.println();
        try {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        } finally {
            printWriter.close();
        }
    }

    /**
     * max JIT length
     */
    private static final int JIT_LIMIT = 5 * 1024;

    /**
     * whether the byte code is greater than {@link #JIT_LIMIT}.
     */
    public static boolean isAffectJIT(byte[] bytecode) {
        if (bytecode.length > JIT_LIMIT) {
            System.err.println(
                    "The template bytecode too long, may be affect the JIT compiler.");
            return true;
        }
        return false;
    }

    /**
     * get method by sizer, it will return the first found method.
     */
    public static String getSizerMethod(Class<?> cls, String[] sizer) {
        for (String s : sizer) {
            try {
                return cls.getMethod(s, new Class<?>[0]).getName() + "()";
            } catch (NoSuchMethodException e) {
            }
        }
        return null;
    }

    /**
     * invoke the leftParameter and get the name property.
     * it will try the Array.length() and Map.get(),
     * then it will try get'Name' and is'Name',
     * last, it will to find the name by invoke field.
     */
    public static Object searchProperty(Object leftParameter, String name)
            throws Exception {
        Class<?> leftClass = leftParameter.getClass();
        Object result;
        if (leftParameter.getClass().isArray() && "length".equals(name)) {
            result = Array.getLength(leftParameter);
        } else if (leftParameter instanceof Map) {
            result = ((Map<Object, Object>) leftParameter).get(name);
        } else {
            try {
                String getter = "get" + name.substring(0, 1).toUpperCase() +
                        name.substring(1);
                Method method = leftClass.getMethod(getter, new Class<?>[0]);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                result = method.invoke(leftParameter, new Object[0]);
            } catch (NoSuchMethodException e2) {
                try {
                    String getter = "is" + name.substring(0, 1).toUpperCase() +
                            name.substring(1);
                    Method method = leftClass.getMethod(getter,
                            new Class<?>[0]);
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    result = method.invoke(leftParameter, new Object[0]);
                } catch (NoSuchMethodException e3) {
                    Field field = leftClass.getField(name);
                    result = field.get(leftParameter);
                }
            }
        }
        return result;
    }

    /**
     * search the method and return the defined method.{@link #searchMethod(Class, String, Class[], boolean)}
     */
    public static Method searchMethod(Class<?> currentClass, String name, Class<?>[] parameterTypes)
            throws NoSuchMethodException {
        return searchMethod(currentClass, name, parameterTypes, false);
    }

    /**
     * search the method and return the defined method.
     * it will {@link Class#getMethod(String, Class[])}, if exception occurs,
     * it will search for all methods, and find the most fit method.
     */
    public static Method searchMethod(Class<?> currentClass, String name, Class<?>[] parameterTypes, boolean boxed)
            throws NoSuchMethodException {
        if (currentClass == null) {
            throw new NoSuchMethodException("class == null");
        }
        try {
            return currentClass.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Method likeMethod = null;
            for (Method method : currentClass.getMethods()) {
                if (method.getName().equals(name) && parameterTypes.length ==
                        method.getParameterTypes().length &&
                        Modifier.isPublic(method.getModifiers())) {
                    if (parameterTypes.length > 0) {
                        Class<?>[] types = method.getParameterTypes();
                        boolean eq = true;
                        boolean like = true;
                        for (int i = 0; i < parameterTypes.length; i++) {
                            Class<?> type = types[i];
                            Class<?> parameterType = parameterTypes[i];
                            if (type != null && parameterType != null &&
                                    !type.equals(parameterType)) {
                                eq = false;
                                if (boxed) {
                                    type = getBoxedClass(type);
                                    parameterType = getBoxedClass(parameterType);
                                }
                                if (!type.isAssignableFrom(parameterType)) {
                                    eq = false;
                                    like = false;
                                    break;
                                }
                            }
                        }
                        if (!eq) {
                            if (like && (likeMethod == null ||
                                    likeMethod.getParameterTypes()[0].isAssignableFrom(
                                            method.getParameterTypes()[0]))) {
                                likeMethod = method;
                            }
                            continue;
                        }
                    }
                    return method;
                }
            }
            if (likeMethod != null) {
                return likeMethod;
            }
            throw e;
        }
    }

    /**
     * return the init value for given type.
     * 0 for byte, short, int, long, float and double.
     * '\0' for char,
     * false for boolean,
     * null for others.
     */
    public static String getInitValue(Class<?> type) {
        if (byte.class.equals(type) || short.class.equals(type) ||
                int.class.equals(type) || long.class.equals(type) ||
                float.class.equals(type) || double.class.equals(type)) {
            return "0";
        } else if (char.class.equals(type)) {
            return "'\\0'";
        } else if (boolean.class.equals(type)) {
            return "false";
        } else {
            return "null";
        }
    }

    /**
     * this will return the init value with type name.
     */
    public static String getInitValueWithType(Class<?> type) {
        if (byte.class.equals(type)) {
            return "(byte) 0";
        } else if (short.class.equals(type)) {
            return "(short) 0";
        } else if (int.class.equals(type)) {
            return "0";
        } else if (long.class.equals(type)) {
            return "0l";
        } else if (float.class.equals(type)) {
            return "0f";
        } else if (double.class.equals(type)) {
            return "0d";
        } else if (char.class.equals(type)) {
            return "'\\0'";
        } else if (boolean.class.equals(type)) {
            return "false";
        } else {
            return "(" + type.getCanonicalName() + ") null";
        }
    }

    /**
     * parse value to boolean
     */
    public static boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return value == null ? false : toBoolean(String.valueOf(value));
    }

    /**
     * parse value to char
     */
    public static char toChar(Object value) {
        if (value instanceof Character) {
            return (Character) value;
        }
        return value == null ? '\0' : toChar(String.valueOf(value));
    }

    /**
     * parse value to byte
     */
    public static byte toByte(Object value) {
        if (value instanceof Number) {
            return ((Number) value).byteValue();
        }
        return value == null ? 0 : toByte(String.valueOf(value));
    }

    /**
     * parse value to short
     */
    public static short toShort(Object value) {
        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }
        return value == null ? 0 : toShort(String.valueOf(value));
    }

    /**
     * parse value to int
     */
    public static int toInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return value == null ? 0 : toInt(String.valueOf(value));
    }

    /**
     * parse value to long
     */
    public static long toLong(Object value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return value == null ? 0 : toLong(String.valueOf(value));
    }

    /**
     * parse value to float
     */
    public static float toFloat(Object value) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        return value == null ? 0 : toFloat(String.valueOf(value));
    }

    /**
     * parse value to double
     */
    public static double toDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return value == null ? 0 : toDouble(String.valueOf(value));
    }

    /**
     * parse value to Class<?>
     */
    public static Class<?> toClass(Object value) {
        if (value instanceof Class) {
            return (Class<?>) value;
        }
        return value == null ? null : toClass(String.valueOf(value));
    }

    /**
     * parse value to boolean
     */
    public static boolean toBoolean(String value) {
        return TextUtils.isEmpty(value) ? false : Boolean.parseBoolean(value);
    }

    /**
     * parse value to char
     */
    public static char toChar(String value) {
        return TextUtils.isEmpty(value) ? '\0' : value.charAt(0);
    }

    /**
     * parse value to byte
     */
    public static byte toByte(String value) {
        return TextUtils.isEmpty(value) ? 0 : Byte.parseByte(value);
    }

    /**
     * parse value to short
     */
    public static short toShort(String value) {
        return TextUtils.isEmpty(value) ? 0 : Short.parseShort(value);
    }

    /**
     * parse value to int
     */
    public static int toInt(String value) {
        return TextUtils.isEmpty(value) ? 0 : Integer.parseInt(value);
    }

    /**
     * parse value to long
     */
    public static long toLong(String value) {
        return TextUtils.isEmpty(value) ? 0 : Long.parseLong(value);
    }

    /**
     * parse value to float
     */
    public static float toFloat(String value) {
        return TextUtils.isEmpty(value) ? 0 : Float.parseFloat(value);
    }

    /**
     * parse value to double
     */
    public static double toDouble(String value) {
        return TextUtils.isEmpty(value) ? 0 : Double.parseDouble(value);
    }

    /**
     * parse value to Class
     */
    public static Class<?> toClass(String value) {
        return TextUtils.isEmpty(value) ? null : forName(value);
    }

    /**
     * get the method start with 'get' or 'is'.
     */
    public static Method getGetter(Object bean, String property) {
        Map<String, Method> cache = GETTER_CACHE.get(bean.getClass());
        if (cache == null) {
            cache = new ConcurrentHashMap<>();
            for (Method method : bean.getClass().getMethods()) {
                if (Modifier.isPublic(method.getModifiers()) &&
                        !Modifier.isStatic(method.getModifiers()) &&
                        !void.class.equals(method.getReturnType()) &&
                        method.getParameterTypes().length == 0) {
                    String name = method.getName();
                    if (name.length() > 3 && name.startsWith("get")) {
                        cache.put(name.substring(3, 4).toLowerCase() +
                                name.substring(4), method);
                    } else if (name.length() > 2 && name.startsWith("is")) {
                        cache.put(name.substring(2, 3).toLowerCase() +
                                name.substring(3), method);
                    }
                }
            }
            Map<String, Method> old = GETTER_CACHE.putIfAbsent(bean.getClass(),
                    cache);
            if (old != null) {
                cache = old;
            }
        }
        return cache.get(property);
    }

    /**
     * check for getter method and return the value.{@link #getGetter(Object, String)}.
     */
    public static Object getProperty(Object bean, String property) {
        if (bean == null || TextUtils.isEmpty(property)) {
            return null;
        }
        try {
            Method getter = getGetter(bean, property);
            if (getter != null) {
                if (!getter.isAccessible()) {
                    getter.setAccessible(true);
                }
                return getter.invoke(bean, new Object[0]);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * find the setter method and set the value.
     */
    public static void setProperties(Object bean, Map<String, Object> properties) {
        for (Method method : bean.getClass().getMethods()) {
            String name = method.getName();
            if (name.length() > 3 && name.startsWith("set") &&
                    Modifier.isPublic(method.getModifiers()) &&
                    method.getParameterTypes().length == 1 &&
                    method.getDeclaringClass() != Object.class) {
                String key = name.substring(3, 4).toLowerCase() +
                        name.substring(4);
                try {
                    Object value = properties.get(key);
                    if (value != null) {
                        method.invoke(bean, new Object[]{
                                convertCompatibleType(value,
                                        method.getParameterTypes()[0])});
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * find all getter and is method and return the value by map.
     */
    public static Map<String, Object> getProperties(Object bean) {
        Map<String, Object> map = new HashMap<>();
        for (Method method : bean.getClass().getMethods()) {
            String name = method.getName();
            if (((name.length() > 3 && name.startsWith("get")) ||
                    (name.length() > 2 && name.startsWith("is"))) &&
                    Modifier.isPublic(method.getModifiers()) &&
                    method.getParameterTypes().length == 0 &&
                    method.getDeclaringClass() != Object.class) {
                int i = name.startsWith("get") ? 3 : 2;
                String key = name.substring(i, i + 1).toLowerCase() +
                        name.substring(i + 1);
                try {
                    map.put(key, method.invoke(bean, new Object[0]));
                } catch (Exception e) {
                }
            }
        }
        return map;
    }

    /**
     * dump the exception to string
     */
    public static String dumpException(Throwable throwable) {
        StringWriter stringWriter = new StringWriter(160);
        stringWriter.write(throwable.getClass().getName());
        stringWriter.write(":\n");
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * filter the java key word
     */
    public static String filterJavaKeyword(String name) {
        if ("abstract".equals(name) || "assert".equals(name) ||
                "boolean".equals(name) || "break".equals(name) ||
                "byte".equals(name) || "case".equals(name) ||
                "catch".equals(name) || "char".equals(name) ||
                "class".equals(name) || "continue".equals(name) ||
                "default".equals(name) || "do".equals(name) ||
                "double".equals(name) || "else".equals(name) ||
                "enum".equals(name) || "extends".equals(name) ||
                "final".equals(name) || "finally".equals(name) ||
                "float".equals(name) || "for".equals(name) ||
                "if".equals(name) || "implements".equals(name) ||
                "import".equals(name) || "instanceof".equals(name) ||
                "int".equals(name) || "interface".equals(name) ||
                "long".equals(name) || "native".equals(name) ||
                "new".equals(name) || "package".equals(name) ||
                "private".equals(name) || "protected".equals(name) ||
                "public".equals(name) || "return".equals(name) ||
                "strictfp".equals(name) || "short".equals(name) ||
                "static".equals(name) || "super".equals(name) ||
                "switch".equals(name) || "synchronized".equals(name) ||
                "this".equals(name) || "throw".equals(name) ||
                "throws".equals(name) || "transient".equals(name) ||
                "try".equals(name) || "void".equals(name) ||
                "volatile".equals(name) || "while".equals(name)) {
            return "$" + name;
        }
        return name;
    }

    /**
     * default date format.
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * convert value to given type.
     * null safe.
     *
     * @param value value for convert
     * @param type  will converted type
     * @return value while converted
     */
    public static Object convertCompatibleType(Object value, Class<?> type) {

        if (value == null || type == null ||
                type.isAssignableFrom(value.getClass())) {
            return value;
        }
        if (value instanceof String) {
            String string = (String) value;
            if (char.class.equals(type) || Character.class.equals(type)) {
                if (string.length() != 1) {
                    throw new IllegalArgumentException(String.format(
                            "CAN NOT convert String(%s) to char!" +
                                    " when convert String to char, the String MUST only 1 char.",
                            string));
                }
                return string.charAt(0);
            } else if (type.isEnum()) {
                return Enum.valueOf((Class<Enum>) type, string);
            } else if (type == BigInteger.class) {
                return new BigInteger(string);
            } else if (type == BigDecimal.class) {
                return new BigDecimal(string);
            } else if (type == Short.class || type == short.class) {
                return Short.valueOf(string);
            } else if (type == Integer.class || type == int.class) {
                return Integer.valueOf(string);
            } else if (type == Long.class || type == long.class) {
                return Long.valueOf(string);
            } else if (type == Double.class || type == double.class) {
                return Double.valueOf(string);
            } else if (type == Float.class || type == float.class) {
                return Float.valueOf(string);
            } else if (type == Byte.class || type == byte.class) {
                return Byte.valueOf(string);
            } else if (type == Boolean.class || type == boolean.class) {
                return Boolean.valueOf(string);
            } else if (type == Date.class) {
                try {
                    return new SimpleDateFormat(DATE_FORMAT).parse(
                            (String) value);
                } catch (ParseException e) {
                    throw new IllegalStateException(
                            "Failed to parse date " + value + " by format " +
                                    DATE_FORMAT + ", cause: " + e.getMessage(),
                            e);
                }
            } else if (type == Class.class) {
                return forName((String) value);
            }
        } else if (value instanceof Number) {
            Number number = (Number) value;
            if (type == byte.class || type == Byte.class) {
                return number.byteValue();
            } else if (type == short.class || type == Short.class) {
                return number.shortValue();
            } else if (type == int.class || type == Integer.class) {
                return number.intValue();
            } else if (type == long.class || type == Long.class) {
                return number.longValue();
            } else if (type == float.class || type == Float.class) {
                return number.floatValue();
            } else if (type == double.class || type == Double.class) {
                return number.doubleValue();
            } else if (type == BigInteger.class) {
                return BigInteger.valueOf(number.longValue());
            } else if (type == BigDecimal.class) {
                return BigDecimal.valueOf(number.doubleValue());
            } else if (type == Date.class) {
                return new Date(number.longValue());
            }
        } else if (value instanceof Collection) {
            Collection collection = (Collection) value;
            if (type.isArray()) {
                int length = collection.size();
                Object array = Array.newInstance(type.getComponentType(),
                        length);
                int i = 0;
                for (Object item : collection) {
                    Array.set(array, i++, item);
                }
                return array;
            } else if (!type.isInterface()) {
                try {
                    Collection result = (Collection) type.newInstance();
                    result.addAll(collection);
                    return result;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else if (type == List.class) {
                return new ArrayList<>(collection);
            } else if (type == Set.class) {
                return new HashSet<>(collection);
            }
        } else if (value.getClass().isArray() &&
                Collection.class.isAssignableFrom(type)) {
            Collection collection;
            if (!type.isInterface()) {
                try {
                    collection = (Collection) type.newInstance();
                } catch (Throwable e) {
                    collection = new ArrayList<>();
                }
            } else if (type == Set.class) {
                collection = new HashSet<>();
            } else {
                collection = new ArrayList<>();
            }
            int length = Array.getLength(value);
            for (int i = 0; i < length; i++) {
                collection.add(Array.get(value, i));
            }
            return collection;
        }
        return value;
    }
}
