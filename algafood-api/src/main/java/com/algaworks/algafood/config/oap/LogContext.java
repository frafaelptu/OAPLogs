package com.algaworks.algafood.config.oap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@RequestScope
public class LogContext {

    LogContext() {
        throw new IllegalStateException("Utility class");
    }

    private static final ThreadLocal<ArrayList<Object>> data = ThreadLocal.withInitial(ArrayList::new);

    public static void addData(Object object) {
        data.get().add(object);
    }

    public static <T> List<T> getDataByType(Class<T> type) {
        return data.get()
                .stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public static <T> T getDataByAttribute(Class<T> type, String attributeName, Object attributeValue) {
        List<T> results = getDataByType(type)
                .stream()
                .filter(item -> {
                    try {
                        Field field = item.getClass().getDeclaredField(attributeName);
                        field.setAccessible(true);
                        Object fieldValue = field.get(item);
                        if (fieldValue instanceof String && attributeValue instanceof String) {
                            return ((String) fieldValue).equalsIgnoreCase((String) attributeValue);
                        }
                        return fieldValue.equals(attributeValue);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
        return results.size() == 1 ? results.get(0) : null;
    }


    public static List<Object> getAll() {
        return new ArrayList<>(data.get());
    }

    public static void clear() {
        data.remove();
    }
}