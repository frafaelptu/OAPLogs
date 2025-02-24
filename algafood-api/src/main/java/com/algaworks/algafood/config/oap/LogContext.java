package com.algaworks.algafood.config.oap;

import java.util.HashMap;
import java.util.Map;

public class LogContext {

    LogContext(){
        throw new IllegalStateException("Utility class");
    }

    private static final ThreadLocal<Map<String, String>> context = ThreadLocal.withInitial(HashMap::new);

    public static void put(String key, String value) {
        context.get().put(key, value);
    }

    public static String get(String key) {
        return context.get().get(key);
    }

    public static Map<String, String> getAll() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}