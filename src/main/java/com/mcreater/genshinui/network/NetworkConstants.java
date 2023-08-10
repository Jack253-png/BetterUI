package com.mcreater.genshinui.network;

import net.minecraft.util.Identifier;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;

public class NetworkConstants {
    public static final Identifier C2S_GENSHIN_ADVANCED_MESSAGE_CHANNEL = new Identifier(MOD_ID, "network_c2s_genshin_advanced_message");
    public static final Identifier S2C_GENSHIN_ADVANCED_MESSAGE_CHANNEL = new Identifier(MOD_ID, "network_s2c_genshin_advanced_message");
}