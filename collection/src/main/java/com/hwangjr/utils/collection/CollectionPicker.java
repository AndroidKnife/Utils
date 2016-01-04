package com.hwangjr.utils.collection;

import com.hwangjr.utils.basic.ObjectHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionPicker {

    private CollectionPicker() {
        throw new AssertionError();
    }

    /**
     * get the first element from collection if {@link IPicker#isPicked(Object)} return true
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <T extends IPicker<T>> T pickFirst(T expect, Collection<T> collection) {
        if (null == expect || ObjectHelper.isEmpty(collection)) {
            return null;
        }
        T picked = null;
        for (T t : collection) {
            if (t.isPicked(expect)) {
                picked = t;
                break;
            }
        }
        return picked;
    }

    /**
     * get the elements from collection if {@link IPicker#isPicked(Object)} return true
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <T extends IPicker<T>> List<T> pick(T expect, Collection<T> collection) {
        if (null == expect || ObjectHelper.isEmpty(collection)) {
            return null;
        }
        List<T> picked = new ArrayList<>();
        for (T t : collection) {
            if (t.isPicked(expect)) {
                picked.add(t);
            }
        }
        return picked;
    }

    /**
     * get the first element from collection if {@link IPickerStrategy#isPicked(Object, Object)} return true
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <E, T> T pickFirst(E expect, Collection<T> collection, IPickerStrategy<E, T> strategy) {
        if (null == expect || ObjectHelper.isEmpty(collection)) {
            return null;
        }
        T picked = null;
        for (T t : collection) {
            if (strategy.isPicked(expect, t)) {
                picked = t;
                break;
            }
        }
        return picked;
    }

    /**
     * get the last element from collection if {@link IPickerStrategy#isPicked(Object, Object)} return true
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <E, T> T pickFirstReverse(E expect, List<T> collection, IPickerStrategy<E, T> strategy) {
        if (null == expect || ObjectHelper.isEmpty(collection)) {
            return null;
        }
        T picked = null;
        for (int i = collection.size() - 1; i >= 0; i--) {
            T t = collection.get(i);
            if (strategy.isPicked(expect, t)) {
                picked = t;
                break;
            }
        }
        return picked;
    }

    /**
     * get the elements from collection if {@link IPickerStrategy#isPicked(Object, Object)} return true
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <E, T> List<T> pick(E expect, Collection<T> collection, IPickerStrategy<E, T> strategy) {
        if (null == expect || ObjectHelper.isEmpty(collection)) {
            return null;
        }
        if (null == strategy) {
            return null;
        }
        List<T> picked = new ArrayList<>();
        for (T t : collection) {
            if (strategy.isPicked(expect, t)) {
                picked.add(t);
            }
        }
        return picked;
    }

    /**
     * picker wrapper
     *
     * @param <T>
     */
    public interface IPicker<T> {
        boolean isPicked(T target);
    }

    /**
     * Picker Strategy
     *
     * @param <E>
     * @param <T>
     */
    public interface IPickerStrategy<E, T> {
        boolean isPicked(E expect, T target);
    }
}
