package com.mcreater.genshinui.elements;

import com.mcreater.genshinui.elements.skyisland.block.GenshinSkyIslandPathBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;

public class GenshinBlocks {
    public static GenshinSkyIslandPathBlock GENSHIN_SKY_ISLAND_PATH_BLOCK;
    public static Item GENSHIN_SKY_ISLAND_PATH_BLOCK_ITEM;
    public static void register() {
        GENSHIN_SKY_ISLAND_PATH_BLOCK = Registry.register(
                Registry.BLOCK,
                new Identifier(MOD_ID, "genshin_sky_island_path_block"),
                new GenshinSkyIslandPathBlock(FabricBlockSettings.of(Material.BARRIER).strength(1.0F).nonOpaque())
        );

        GENSHIN_SKY_ISLAND_PATH_BLOCK_ITEM = Registry.register(
                Registry.ITEM,
                new Identifier(MOD_ID, "genshin_sky_island_path_block"),
                new BlockItem(GENSHIN_SKY_ISLAND_PATH_BLOCK, new FabricItemSettings())
        );
    }
}
