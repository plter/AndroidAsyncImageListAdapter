package com.plter.aila.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by plter on 12/15/15.
 */
public class DynimacObjectUtil {


    static public <T> T getFieldValue(Object obj, String fieldName) {
        String first = fieldName.substring(0, 1);
        String remain = fieldName.substring(1);

        return callMethodWithNoArgs(obj, String.format("get%s%s", first.toUpperCase(), remain));
    }


    static public <T> T callMethodWithNoArgs(Object obj, String methodName) {
        Class<?> clazz = obj.getClass();
        try {
            Method method = clazz.getMethod(methodName);
            return (T) method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

            throw new RuntimeException("NoSuchMethodException " + methodName);
        } catch (InvocationTargetException e) {
            e.printStackTrace();

            throw new RuntimeException("InvocationTargetException " + methodName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();

            throw new RuntimeException("IllegalAccessException " + methodName);
        }
    }
}
