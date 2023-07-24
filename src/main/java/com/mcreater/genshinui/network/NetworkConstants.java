package com.mcreater.genshinui.network;

import net.minecraft.util.Identifier;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;

public class NetworkConstants {
    public static final Identifier C2S_GENSHIN_EMOJI = new Identifier(MOD_ID, "network_c2s_genshin_emoji");
    public static final Identifier S2C_GENSHIN_EMOJI_RESPONSE = new Identifier(MOD_ID, "network_s2c_genshin_emoji_response");
    public static final Identifier S2C_GENSHIN_EMOJI_SYNC = new Identifier(MOD_ID, "network_s2c_genshin_emoji_sync");

    public static final Identifier C2S_GENSHIN_SKY_ISLAND_PATH_EVENT = new Identifier(MOD_ID, "network_c2s_genshin_sky_island_path_event");
}