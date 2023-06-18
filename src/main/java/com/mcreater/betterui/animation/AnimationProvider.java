package com.mcreater.betterui.animation;

import java.util.function.Supplier;

import static com.mcreater.betterui.animation.AnimationGenerator.*;

public class AnimationProvider {
    public enum AnimationType {
        EASE_IN,
        EASE_OUT,
        EASE_IN_OUT
    }

    public enum AnimationMode {
        LINEAR,
        QUADRATIC,
        SINUSOIDAL,
        EXPONENTIAL,
        CIRCULAR,
        CUBIC,
        QUARTIC,
        QUINTIC,
        ELASTIC,
        BACK,
        BOUNCE
    }

    public double generate(AnimationNode node, AnimationType type, AnimationMode mode) {
        AnimationGenerator generator = LINEAR;
        switch (mode) {
            case LINEAR:
                break;
            case QUADRATIC:
                generator = getGenerator(
                        type,
                        () -> QUADRATIC_EASEIN,
                        () -> QUADRATIC_EASEOUT,
                        () -> QUADRATIC_EASEINOUT
                );
                break;
            case SINUSOIDAL:
                generator = getGenerator(
                        type,
                        () -> SINUSOIDAL_EASEIN,
                        () -> SINUSOIDAL_EASEOUT,
                        () -> SINUSOIDAL_EASEINOUT
                );
                break;
            case EXPONENTIAL:
                generator = getGenerator(
                        type,
                        () -> EXPONENTIAL_EASEIN,
                        () -> EXPONENTIAL_EASEOUT,
                        () -> EXPONENTIAL_EASEINOUT
                );
                break;
            case CIRCULAR:
                generator = getGenerator(
                        type,
                        () -> CIRCULAR_EASEIN,
                        () -> CIRCULAR_EASEOUT,
                        () -> CIRCULAR_EASEINOUT
                );
                break;
            case CUBIC:
                generator = getGenerator(
                        type,
                        () -> CUBIC_EASEIN,
                        () -> CUBIC_EASEOUT,
                        () -> CUBIC_EASEINOUT
                );
                break;
        }

        return generator.applyAsDouble(node);
    }

    private AnimationGenerator getGenerator(AnimationType type, Supplier<AnimationGenerator> easein, Supplier<AnimationGenerator> easeout, Supplier<AnimationGenerator> easeinout) {
        switch (type) {
            default:
            case EASE_OUT:
                return easeout.get();
            case EASE_IN:
                return easein.get();
            case EASE_IN_OUT:
                return easeinout.get();
        }
    }
}
