package cn.bensun.frida_util;


import java.lang.reflect.Field;
import java.util.List;

/**
 * @Description frida工具类
 * @CreatedBy weizongtang
 * @CreateTime 2022/01/05 19:01:58
 */
public class Util<T> {
    private Util() {
    }

    /**
     * @Description 反射设置对象值
     * @CreatedBy weizongtang
     * @CreateTime 2022/01/05 20:13:15
     */
    public static void setValue(Object obj, String fieldName, Object value) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * @Description 反射获取对象值
     * @CreatedBy weizongtang
     * @CreateTime 2022/01/05 20:15:59
     */
    public static Object getValue(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * @Description 反射获取对象值
     * @CreatedBy weizongtang
     * @CreateTime 2022/01/05 20:15:59
     */
    public static List getValueList(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object o = field.get(obj);
        if (o instanceof List) {
            return (List) o;
        }
        return null;
    }
}
