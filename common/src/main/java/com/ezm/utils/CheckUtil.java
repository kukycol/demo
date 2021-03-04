package com.ezm.utils;


import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckUtil {

    /**
     * 校验jsonObject中key是否存在检验和空校验（null or ""）
     *
     * @param obj 对象
     * @param fieldNames 字符串数组
     */
    @SneakyThrows
    public static Map<String, Object> FieldNull(Object obj, String... fieldNames) {
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];
            for (Field f : obj.getClass().getDeclaredFields()) {
                /**设置字段可访问,解除private*/
                f.setAccessible(true);
                String name = f.getName();
                /**不存在跳过*/
                if (!name.equals(fieldName)) {
                    continue;
                }
                /**字段类型*/
                Type genericType = f.getGenericType();
                String typeName = genericType.getTypeName();
                if (typeName.equals("java.lang.String")) {
                    String fieldValue = (String) f.get(obj);
                    if (fieldValue == null || fieldValue.isEmpty()) {
                        map.put("fieldName", fieldName + "字符串为空值");
                        map.put("flag", false);
                        return map;
                    }
                }
            }
        }
        map.put("flag", true);
        return map;
    }


    /**
     * 判对象空
     * @param obj 对象
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }


    /**
     * 判对象空
     * @param list 集合
     * @return
     */
    public static boolean isListBlank(List<Object> list) {
        if (list == null) {
            return true;
        }

        if (list.size() == 0) {
            return true;
        }

        return false;
    }

}
