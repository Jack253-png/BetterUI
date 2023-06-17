package com.mcreater.betterui.animation;

import java.util.function.ToDoubleFunction;

public interface AnimationGenerator extends ToDoubleFunction<AnimationNode> {
    AnimationGenerator LINEAR = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return c * t / d + b;
    };
    AnimationGenerator QUADRATIC_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return c * (t / d) * (t / d) + b;
    };
    AnimationGenerator QUADRATIC_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return -c * (t / d) * (t / d - 2) + b;
    };
    AnimationGenerator QUADRATIC_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d * 2;
        if (i1 < 1) return c / 2 * i1 * i1 + b;
        double i2 = i1 - 1;
        return -c / 2 * (i2 * (i2 - 2) - 1) + b;
    };
    AnimationGenerator SINUSOIDAL_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
    };

    AnimationGenerator SINUSOIDAL_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return c * Math.sin(t / d * (Math.PI / 2)) + b;
    };

    AnimationGenerator SINUSOIDAL_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        return -c / 2 * (Math.cos(Math.PI * t / d) - 1) + b;
    };

    AnimationGenerator EXPONENTIAL_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        if (t == 0) return b;
        return c * Math.pow(2, 10 * (t / d - 1)) + b;
    };

    AnimationGenerator EXPONENTIAL_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        if (t == d) return b + c;
        return c * (-Math.pow(2, -10 * t / d) + 1) + b;
    };

    AnimationGenerator EXPONENTIAL_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        if (t == 0) return b;
        if( t == d) return b + c;
        double i1 = t / d * 2;
        if (i1 < 1) return c / 2 * Math.pow(2, 10 * (i1 - 1)) + b;
        return c / 2 * (-Math.pow(2, -10 * (i1 - 1)) + 2) + b;
    };

    AnimationGenerator CIRCULAR_EASEIN = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        double i1 = t / d;
        return -c * (Math.sqrt(1 - i1 * i1) - 1) + b;
    };

    AnimationGenerator CIRCULAR_EASEOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();
        double i1 = t / d - 1;
        return c * Math.sqrt(1 - i1 * i1) + b;
    };

    AnimationGenerator CIRCULAR_EASEINOUT = animationNode -> {
        double t = animationNode.getIndex();
        double b = animationNode.getBase();
        double c = animationNode.getAddition();
        double d = animationNode.getAll();

        double i1 = t / d * 2;
        if (i1 < 1) return -c / 2 * (Math.sqrt(1 - i1 * i1) - 1) + b;
        double i2 = i1 - 2;
        return c / 2 * (Math.sqrt(1 - i2 * i2) + 1) + b;
    };
}
