package com.mcreater.betterui.util;

public class SafeValue {
    public static boolean safeBoolean(Object aboolean) {
        return aboolean instanceof Boolean ? (Boolean) aboolean : false;
    }
}
