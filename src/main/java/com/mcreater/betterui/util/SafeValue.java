package com.mcreater.betterui.util;

public class SafeValue {
    public static boolean safeBoolean(Object aboolean) {
        return aboolean instanceof Boolean ? (Boolean) aboolean : false;
    }

    public static int safeInteger(Object aInteger) {
        return safeInteger(aInteger, 0);
    }
    public static int safeInteger(Object aInteger, int def) {
        return aInteger instanceof Number ? ((Number) aInteger).intValue() : def;
    }

    public static int safeInteger(Object aInteger, int def, int min, int max) {
        int va = safeInteger(aInteger, def);
        if (va <= min) return min;
        return Math.min(va, max);
    }

    public static <T extends Enum<T>> T safeEnum(Class<T> eClass, Object value, T defaultValue) {
        try {
            return Enum.valueOf(eClass, value.toString());
        }
        catch (Exception e) {
            return defaultValue;
        }
    }
}
