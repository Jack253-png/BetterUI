package com.mcreater.genshinui.elements.skyisland.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static com.mcreater.genshinui.elements.GenshinEntities.GENSHIN_SKY_ISLAND_PATH_BLOCK_ENTITY;

public class GenshinSkyIslandPathBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public GenshinSkyIslandPathBlockEntity(BlockPos pos, BlockState state) {
        super(GENSHIN_SKY_ISLAND_PATH_BLOCK_ENTITY, pos, state);
    }

    public BlockPos getPos() {
        return super.getPos();
    }

    public void registerControllers(AnimationData animationData) {

    }

    public AnimationFactory getFactory() {
        return this.factory;
    }
}
