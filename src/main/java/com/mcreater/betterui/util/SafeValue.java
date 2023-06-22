package com.mcreater.betterui.util;

public class SafeValue {
    public static boolean safeBoolean(Object aboolean) {
        return aboolean instanceof Boolean ? (Boolean) aboolean : false;
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
