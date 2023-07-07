package com.mcreater.genshinui.mixin;

import com.mcreater.genshinui.animation.AnimatedValue;
import com.mcreater.genshinui.animation.AnimationProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.mcreater.genshinui.GenshinUIClient.isClientTick;

@Mixin(value = {ClientWorld.class}, priority = Integer.MAX_VALUE)
public abstract class ClientWorldMixin {
    @Inject(at = @At("HEAD"), method = "tickTime")
    public void onTickTime(CallbackInfo ci) {
        isClientTick = true;
    }

    @Mixin(value = {ClientWorld.Properties.class}, priority = Integer.MAX_VALUE)
    public abstract static class ClientWorldPropertiesMixin implements WorldAccess {
        @Shadow private long timeOfDay;
        @Shadow @Final private GameRules gameRules;
        private static final AnimatedValue value = new AnimatedValue(0, 0, 2000, n -> AnimationProvider.generate(n, AnimationProvider.AnimationType.EASE_IN_OUT, AnimationProvider.AnimationMode.LINEAR));
        private static final AnimatedValue valueexp = new AnimatedValue(0, 0, 4000, n -> AnimationProvider.generate(n, AnimationProvider.AnimationType.EASE_IN_OUT, AnimationProvider.AnimationMode.EXPONENTIAL));
        private boolean getTicked() {
            return this.gameRules.getBoolean(GameRules.DO_DAYLIGHT_CYCLE);
        }
        @Inject(at = @At("RETURN"), method = "setTimeOfDay", cancellable = true)
        public void onSetTimeOfDay(long timeOfDay, CallbackInfo ci) {
            boolean ticked = getTicked();
            if (ticked ? value.getExpectedValue() != timeOfDay && !isClientTick : value.getExpectedValue() != timeOfDay) value.setExpectedValue(timeOfDay);
            if (ticked ? valueexp.getExpectedValue() != timeOfDay && !isClientTick : valueexp.getExpectedValue() != timeOfDay) valueexp.setExpectedValue(timeOfDay);

            isClientTick = false;
            ci.cancel();
        }

        @Inject(at = @At("RETURN"), method = "getTimeOfDay", cancellable = true)
        public void onGetTimeOfDay(CallbackInfoReturnable<Long> cir) {
            cir.setReturnValue((long) (getTicked() ? value.getCurrentValue() : valueexp.getCurrentValue()));
        }
    }
}