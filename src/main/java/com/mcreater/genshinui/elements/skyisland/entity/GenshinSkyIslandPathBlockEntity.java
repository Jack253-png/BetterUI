package com.mcreater.genshinui.elements.skyisland.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static com.mcreater.genshinui.elements.GenshinEntities.GENSHIN_SKY_ISLAND_PATH_BLOCK_ENTITY;

public class GenshinSkyIslandPathBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final AnimationBuilder FLOATING = new AnimationBuilder()
            .addAnimation("animation.GenshinSkyIslandPath.flake1")
            .addAnimation("animation.GenshinSkyIslandPath.stop");
    public GenshinSkyIslandPathBlockEntity(BlockPos pos, BlockState state) {
        super(GENSHIN_SKY_ISLAND_PATH_BLOCK_ENTITY, pos, state);
    }

    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(FLOATING);

        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(
                new AnimationController<>(this, "controller", 0, this::predicate));
    }

    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void readNbt(NbtCompound nbt) {

    }

    protected void writeNbt(NbtCompound nbt) {

    }
}
