/*
 * 文件名：BeanUtils.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved.
 * 描述： BeanUtils.java
 * 修改人：zxiaofan
 * 修改时间：2016年9月26日
 * 修改内容：新增
 */
package com.hujiya.other;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * github.zxiaofan.com/Javautils.
 *
 * @author zxiaofan
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BeanUtils {
    /**
     * 构造函数.
     */
    public BeanUtils() {
        throw new RuntimeException("this is a util class,can not instance");
    }

    /**
     * copy级别-原始数据不为null.
     */
    public static final int copy_src_null = 1;

    /**
     * copy级别-原始数据不为null或empty.
     */
    public static final int copy_src_nullOrEmpty = 2;

    /**
     * copy级别-目标数据不为null.
     */
    public static final int copy_dest_null = 3;

    /**
     * copy级别-目标数据不为null或empty.
     */
    public static final int copy_dest_nullOrEmpty = 4;

    /**
     * 属性拷贝：source to dest(默认直接覆盖).
     * <p>
     * note：不匹配属性直接忽略；
     *
     * @param source     源实体类
     * @param dest       目标实体类
     * @param coverLevel 覆盖级别： 1:source_field != null; 2:source_field != null (&& !"".equals(source_field)); 3:dest_field==null; 4:dest_field==null(|| "".equals(dest_field)); others:fullDeepCopy  。
     */
    public static void copy(Object source, Object dest, int... coverLevel) {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] destFields = dest.getClass().getDeclaredFields();
        // 如果两个类属性数量差距较大，能提升性能
        Field[] littleFields = sourceFields.length <= destFields.length ? sourceFields : destFields;
        int coverLevelInt = coverLevel.length > 0 ? coverLevel[0] : 0;
        for (int i = 0, j = littleFields.length; i < j; i++) {
            String propertyName = littleFields[i].getName();
            Object propertyValue = null;
            try {
                propertyValue = getProperty(source, propertyName);
            } catch (Exception e) {
                // 有异常说明属性不匹配，直接忽略
                continue;
            }
            switch (coverLevelInt) {
                case copy_src_null:
                    if (propertyValue != null) {
                        setProperty(dest, propertyName, propertyValue);
                    }
                    break;
                case copy_src_nullOrEmpty:
                    if (!isNullOrEmpty(propertyValue)) {
                        setProperty(dest, propertyName, propertyValue);
                    }
                    break;
                case copy_dest_null:
                    Object destValue = null;
                    try {
                        destValue = getProperty(source, propertyName);
                    } catch (Exception e) {
                        continue;
                    }
                    if (null == destValue) {
                        setProperty(dest, propertyName, propertyValue);
                    }
                    break;
                case copy_dest_nullOrEmpty:
                    Object destValue4 = null;
                    try {
                        destValue4 = getProperty(source, propertyName);
                    } catch (Exception e) {
                        continue;
                    }
                    if (isNullOrEmpty(destValue4)) {
                        setProperty(dest, propertyName, propertyValue);
                    }
                    break;
                default:
                    setProperty(dest, propertyName, propertyValue);
                    break;
            }
        }
        return;
    }

    /**
     * 根据属性名取值.
     *
     * @param bean         实体
     * @param propertyName 属性名
     * @return value
     */
    public static Object getProperty(Object bean, String propertyName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        Class clazz = bean.getClass();
        Field field = null;
        try {
            field = clazz.getDeclaredField(propertyName);
            Method method = clazz.getDeclaredMethod(getGetterName(field.getName()), new Class[]{});
            return method.invoke(bean, new Object[]{});
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 给指定属性赋值.
     *
     * @param bean         实体
     * @param propertyName 属性名
     * @param value        值
     */
    public static void setProperty(Object bean, String propertyName, Object value) {
        Class clazz = bean.getClass();
        Field field = null;
        try {
            field = clazz.getDeclaredField(propertyName);
            if (null == field) {
                return;
            }
            Method method = clazz.getDeclaredMethod(getSetterName(field.getName()), new Class[]{field.getType()});
            method.invoke(bean, new Object[]{value});
        } catch (Exception e) {
            // NoSuchFieldException
//            e.printStackTrace();
        }
    }

    /**
     * 根据属性名得到get方法.
     *
     * @param propertyName 属性名
     * @return getName
     */
    private static String getGetterName(String propertyName) {
        String method = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        return method;
    }

    /**
     * 根据属性名得到is方法(boolean|Bollean型).
     *
     * @param propertyName 属性名
     * @return getName
     */
    private static String getIsName(String propertyName) {
        String method = "is" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        return method;
    }

    /**
     * 根据属性名得到set方法.
     *
     * @param propertyName 属性名
     * @return setName
     */
    private static String getSetterName(String propertyName) {
        String method = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        return method;
    }

    /**
     * 判断src是否为null(若src为String,判断是否为"").
     *
     * @param src src
     * @return boolean
     */
    private static boolean isNullOrEmpty(Object src) {
        if (null == src) {
            return true;
        }
        if (src instanceof String) {
            return "".equals(((String) src).trim());
        }
        return false;
    }

    /**
     * 为对象属性null值赋初始值，常用于插库前处理对象.
     *
     * @param obj obj
     */
    public static void notNull(Object obj) {
        try {
            if (null == obj) {
                return;
            }
            Field[] fields = obj.getClass().getDeclaredFields();
            Class cla = obj.getClass();
            for (Field field : fields) {
                field.setAccessible(true);
                if (null == field.get(obj)) {
                    setFieldValue(obj, cla, field);
                }
                field.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置属性.
     *
     * @param obj   obj
     * @param cla   Class
     * @param field Field
     * @throws Exception Exception
     */
    private static void setFieldValue(Object obj, Class cla, Field field) throws Exception {
        // NoSuchMethodException, IllegalAccessException, InvocationTargetException
        Class type = field.getType();
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method;
        if (String.class.equals(type)) {
            method = cla.getMethod(methodName, String.class);
            method.invoke(obj, "");
        } else if (char.class.equals(type)) {
            method = cla.getMethod(methodName, char.class);
            method.invoke(obj, ' '); // ''编译报错Invalid character constant
        } else if (Character.class.equals(type)) {
            method = cla.getMethod(methodName, Character.class);
            method.invoke(obj, ' ');
        } else if (boolean.class.equals(type)) {
            method = cla.getMethod(methodName, boolean.class);
            method.invoke(obj, true);
        } else if (Boolean.class.equals(type)) {
            method = cla.getMethod(methodName, Boolean.class);
            method.invoke(obj, true);
        } else if (byte.class.equals(type)) {
            method = cla.getMethod(methodName, byte.class);
            method.invoke(obj, (byte) 0);
        } else if (Byte.class.equals(type)) {
            method = cla.getMethod(methodName, Byte.class);
            method.invoke(obj, (byte) 0);
        } else if (short.class.equals(type)) {
            method = cla.getMethod(methodName, short.class);
            method.invoke(obj, (short) 0);
        } else if (Short.class.equals(type)) {
            method = cla.getMethod(methodName, Short.class);
            method.invoke(obj, (short) 0);
        } else if (int.class.equals(type)) {
            method = cla.getMethod(methodName, int.class);
            method.invoke(obj, 0);
        } else if (Integer.class.equals(type)) {
            method = cla.getMethod(methodName, Integer.class);
            method.invoke(obj, 0);
        } else if (long.class.equals(type)) {
            method = cla.getMethod(methodName, long.class);
            method.invoke(obj, 0);
        } else if (Long.class.equals(type)) {
            method = cla.getMethod(methodName, Long.class);
            method.invoke(obj, 0L); // 大写L
        } else if (float.class.equals(type)) {
            method = cla.getMethod(methodName, float.class);
            method.invoke(obj, 0.0f);
        } else if (Float.class.equals(type)) {
            method = cla.getMethod(methodName, Float.class);
            method.invoke(obj, 0.0f);
        } else if (double.class.equals(type)) {
            method = cla.getMethod(methodName, double.class);
            method.invoke(obj, 0.0d);
        } else if (Double.class.equals(type)) {
            method = cla.getMethod(methodName, Double.class);
            method.invoke(obj, 0.0d);
        } else if (Date.class.equals(type)) {
            method = cla.getMethod(methodName, Date.class);
            method.invoke(obj, new Date());
        } else if (BigDecimal.class.equals(type)) {
            method = cla.getMethod(methodName, BigDecimal.class);
            method.invoke(obj, new BigDecimal(0.0D));
        } else if (Timestamp.class.equals(type)) {
            method = cla.getMethod(methodName, Timestamp.class);
            method.invoke(obj, new Timestamp(System.currentTimeMillis()));
        }
    }

    /**
     * 拼接属性（尽量使用包装类，基本类型会有默认值）.
     *
     * @param obj obj
     * @return 拼接结果
     * @throws Exception Exception
     */
    public static String joinProperty(Object obj) throws Exception {
        StringBuffer value = new StringBuffer(100);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String upper = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method m = null;
            try {
                m = obj.getClass().getMethod("get" + upper);
            } catch (NoSuchMethodException e) {
                m = obj.getClass().getMethod("is" + upper);
            }
            if (null != m.invoke(obj)) {
                value.append("&" + name + "=");
                value.append(m.invoke(obj));
            }
        }
        return value.toString();
    }
}
