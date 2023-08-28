package com.kivi.framework.constant.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 基本变量类型的枚举
 */
public enum BasicType {
    BYTE, SHORT, INT, INTEGER, LONG, DOUBLE, FLOAT, BOOLEAN, CHAR, CHARACTER, STRING;

    /** 原始类型为Key，包装类型为Value，例如： int.class -> Integer.class. */
    public static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>(8);
    /** 包装类型为Key，原始类型为Value，例如： Integer.class -> int.class. */
    public static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>(8);

    static {
        wrapperPrimitiveMap.put(Boolean.class, boolean.class);
        wrapperPrimitiveMap.put(Byte.class, byte.class);
        wrapperPrimitiveMap.put(Character.class, char.class);
        wrapperPrimitiveMap.put(Double.class, double.class);
        wrapperPrimitiveMap.put(Float.class, float.class);
        wrapperPrimitiveMap.put(Integer.class, int.class);
        wrapperPrimitiveMap.put(Long.class, long.class);
        wrapperPrimitiveMap.put(Short.class, short.class);
        wrapperPrimitiveMap.put(String.class, String.class);

        for (Map.Entry<Class<?>, Class<?>> entry : wrapperPrimitiveMap.entrySet()) {
            primitiveWrapperMap.put(entry.getValue(), entry.getKey());
        }
    }

    public static boolean isBasicType( Class<?> clazz ) {
        return wrapperPrimitiveMap.containsKey(clazz) || primitiveWrapperMap.containsKey(clazz);
    }
}
