package cn.bensun.frida_util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
    public static <T> void setValue(Object obj, String fieldName, Class<T> valueClazz, Object value) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            clazz.getField(fieldName);
        }
        field.setAccessible(true);
        field.set(obj, (T) value);
    }

    /**
     * @Description 反射设置对象值
     * @CreatedBy weizongtang
     * @CreateTime 2022/01/05 20:13:15
     */
    public static void setValueInt(Object obj, String fieldName, int value) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            clazz.getField(fieldName);
        }
        field.setAccessible(true);
        field.setInt(obj, value);
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

    public static Object callMethod(Object obj, String fieldName, Object... params) {
        Class<?> clazz = obj.getClass();
        List<Class> classes = new ArrayList<>();
        for (Object param : params) {
            classes.add(param.getClass());
        }
        Class[] classArr = new Class[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            classArr[i] = classes.get(i);
        }
        try {
            Method method;
            if (classArr.length == 0) {
                method = clazz.getDeclaredMethod(fieldName);
            } else {
                method = clazz.getDeclaredMethod(fieldName, classArr);
            }
            method.setAccessible(true);
            method.invoke(obj, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用GET方法读取http中的数据
     * <p>
     * url地址
     *
     * @return 请求的响应数据
     */
    public static String doGET(URI uri) {
        try {
            // 创建URL对象
            URL url = uri.toURL();
            // 打开连接 获取连接对象
            URLConnection connection = url.openConnection();

            // 从连接对象中获取网络连接中的输入字节流对象
            InputStream inputStream = connection.getInputStream();
            // 将输入字节流包装成输入字符流对象,并进行字符编码
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            // 创建一个输入缓冲区对象，将字符流对象传入
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // 定义一个字符串变量，用来接收输入缓冲区中的每一行字符串数据
            String line;
            // 创建一个可变字符串对象，用来装载缓冲区对象的数据，使用字符串追加的方式，将响应的所有数据都保存在该对象中
            StringBuilder stringBuilder = new StringBuilder();
            // 使用循环逐行读取输入缓冲区的数据，每次循环读入一行字符串数据赋值给line字符串变量，直到读取的行为空时标识内容读取结束循环
            while ((line = bufferedReader.readLine()) != null) {
                // 将从输入缓冲区读取到的数据追加到可变字符对象中
                stringBuilder.append(line);
            }
            // 依次关闭打开的输入流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            // 将可变字符串转换成String对象返回
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取对象所有值
    public static String getObjValue(Object obj) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Field declaredField : obj.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(obj);
                sb.append(declaredField.getName()).append(":").append(value).append("\t\t#").append(declaredField.getType());
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
