package com.mcreater.betterui.animation;

import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;

import java.util.function.ToDoubleBiFunction;

public interface AnimationGenerator extends ToDoubleBiFunction<AnimationNode, DoubleDoubleImmutablePair> {
    AnimationGenerator LINEAR = (animationNode, pair) -> {
        double t = animationNode.getIndex();
        double b = pair.leftDouble();
        double c = pair.rightDouble();
        double d = animationNode.getAll();
        return c * t / d + b;
    };
    AnimationGenerator QUADRATIC_EASEIN = (animationNode, pair) -> {
        double t = animationNode.getIndex();
        double b = pair.leftDouble();
        double c = pair.rightDouble();
        double d = animationNode.getAll();
        return c * (t / d) * (t / d) + b;
    };
    AnimationGenerator QUADRATIC_EASEOUT = (animationNode, pair) -> {
        double t = animationNode.getIndex();
        double b = pair.leftDouble();
        double c = pair.rightDouble();
        double d = animationNode.getAll();
        return -c * (t / d) * (t / d - 2) + b;
    };
    AnimationGenerator QUADRATIC_EASEINOUT = (animationNode, pair) -> {
        double t = animationNode.getIndex();
        double b = pair.leftDouble();
        double c = pair.rightDouble();
        double d = animationNode.getAll();

        double i1 = t / d * 2;
        if (i1 < 1) return c / 2 * i1 * i1 + b;
        double i2 = i1 - 1;
        return -c / 2 * (i2 * (i2 - 2) - 1) + b;
    };
    AnimationGenerator SINUSOIDAL_EASEIN = (animationNode, pair) -> {
        double t = animationNode.getIndex();
        double b = pair.leftDouble();
        double c = pair.rightDouble();
        double d = animationNode.getAll();
        return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
    };
}
