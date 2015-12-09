package com.hwangjr.utils.json;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation For JSON.
 * <br/>
 * Key is the json name, desc is the description for the field.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSONNode {

    String key();

    String desc() default "";
}