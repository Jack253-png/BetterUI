package com.mcreater.genshinui.elements;

import com.mcreater.genshinui.elements.skyisland.entity.GenshinSkyIslandPathBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;
import static com.mcreater.genshinui.elements.GenshinBlocks.GENSHIN_SKY_ISLAND_PATH_BLOCK;

public class GenshinEntities {
    public static BlockEntityType<GenshinSkyIslandPathBlockEntity> GENSHIN_SKY_ISLAND_PATH_BLOCK_ENTITY;
    public static void register() {
        GENSHIN_SKY_ISLAND_PATH_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "genshin_sky_island_path_block_entity"),
                FabricBlockEntityTypeBuilder.create(GenshinSkyIslandPathBlockEntity::new,GENSHIN_SKY_ISLAND_PATH_BLOCK).build()
        );
    }
}
