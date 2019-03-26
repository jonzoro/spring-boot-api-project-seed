package com.company.project.util;

import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModelUtil {

    /***
     * 获取实体类所有声明的字段
     */
    public static <T> List<Field> getAllDeclaredFields(T model) {
        List<Field> declaredFields = new ArrayList<>();
        Class tmpClass = model.getClass();
        do {
            declaredFields.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
            tmpClass = tmpClass.getSuperclass();
        } while (tmpClass != Object.class);
        return declaredFields;
    }

    /***
     * Model转换成Map,需指定字段
     */
    public static <T> Map<String, Object> modelToMap(T model, List<String> fields) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        if (null == fields || fields.isEmpty()) {
            return map;
        }
        if (null == model) {
            return map;
        }
        List<Field> declaredFields = getAllDeclaredFields(model);
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (fields.contains(fieldName)) {
                Object fieldVal = field.get(model);
                map.put(fieldName, fieldVal);
            }
        }

        return map;
    }

    /***
     * Model列表转换成Map列表,需指定字段
     */
    public static <T> List<Map<String, Object>> modelListToMapList(List<T> modelList, List<String> fields) throws IllegalAccessException {
        List<Map<String, Object>> result = new ArrayList<>();
        for (T model : modelList) {
            result.add(modelToMap(model, fields));
        }
        return result;
    }

    /***
     * Model转换成Map(使用了缓存，初次创建bean时需要初始化，之后就使用缓存，所以速度极快)
     */
    public static <T> Map<String, Object> modelToMapAll(T model) {
        Map<String, Object> map = new HashMap<>();
        if (null != model) {
            BeanMap beanMap = BeanMap.create(model);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }

    /***
     * Model列表转换成Map列表
     */
    public static <T> List<Map<String, Object>> modelListToMapListAll(List<T> modelList) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (T model : modelList) {
            result.add(modelToMapAll(model));
        }
        return result;
    }

    /***
     * 获取两个实体类之间不同的属性
     */
    public static <T> List<String> getDiff(T modelA, T modelB) throws IllegalAccessException {
        List<String> diffList = new ArrayList<>();
        List<Field> declaredFields = getAllDeclaredFields(modelA);
        // 类型相同但是值不一样的时候才进行比较
        if (modelA.getClass().isInstance(modelB) && modelA != modelB) {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object aVal = field.get(modelA);
                Object bVal = field.get(modelB);
                if (bVal != aVal) {
                    diffList.add(field.getName());
                }
            }
        }
        return diffList;
    }

    /***
     * 获取两个实体类之间不同的属性及对应的值变化
     */
    public static <T> List<Map<String, Object>> getChange(T before, T after) throws IllegalAccessException {
        List<Map<String, Object>> diffList = new ArrayList<>();
        List<Field> declaredFields = getAllDeclaredFields(before);
        // 类型相同但是值不一样的时候才进行比较
        if (before.getClass().isInstance(after) && before != after) {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object beforeVal = field.get(before);
                Object afterVal = field.get(after);
                if (afterVal != beforeVal) {
                    Map<String, Object> diffMap = new HashMap<>();
                    diffMap.put("name", field.getName());
                    diffMap.put("before", beforeVal);
                    diffMap.put("after", afterVal);
                    diffList.add(diffMap);
                }
            }
        }
        return diffList;
    }

    /***
     * 拷贝Mao和Model之间的同名属性
     */
    public static <T> void copyProp4MapToObject(Map sourceMap, T destObj) throws Exception {
        Field[] fields = destObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            for (Object entryObj : sourceMap.entrySet()) {
                Map.Entry entry = (Map.Entry) entryObj;
                if (field.getName() == entry.getKey()) {
                    field.setAccessible(true);
                    if (field.getType() == String.class) {
                        field.set(destObj, entry.getValue().toString());
                    } else if (field.getType() == Integer.class) {
                        field.set(destObj, new Integer(entry.getValue().toString()));
                    } else if (field.getType() == Long.class) {
                        field.set(destObj, new Long(entry.getValue().toString()));
                    } else if (field.getType() == Short.class) {
                        field.set(destObj, new Short(entry.getValue().toString()));
                    } else if (field.getType() == Double.class) {
                        field.set(destObj, new Double(entry.getValue().toString()));
                    } else if (field.getType() == Date.class) {
                        field.set(destObj, new SimpleDateFormat().parse(entry.getValue().toString()));
                    } else if (field.getType() == BigDecimal.class) {
                        field.set(destObj, new BigDecimal(entry.getValue().toString()));
                    }
                }
            }
        }
    }
}
