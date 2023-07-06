package com.mcreater.betterui.animation;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class AnimatedValue {
    private double expectedValue;
    private double currentValue;
    private AnimationNode node;
    private final int delta;
    private final Function<AnimationNode, Double> func;
    @NotNull
    private final Consumer<Double> callback;
    public AnimatedValue(double expectedValue, double currentValue, int delta, Function<AnimationNode, Double> function, @NotNull Consumer<Double> callback) {
        this.expectedValue = expectedValue;
        this.currentValue = currentValue;
        this.delta = delta;
        func = function;
        this.callback = callback;
        init();
    }
    public AnimatedValue(double expectedValue, double currentValue, int delta, Function<AnimationNode, Double> function) {
        this(expectedValue, currentValue, delta, function, a -> {});
    }
    private void init() {
        node = new AnimationNode(0, delta, currentValue, expectedValue - currentValue);
        node.setOnAnimation(n -> {
            this.currentValue = func.apply(n);
            callback.accept(currentValue);
        });
    }

    public double getExpectedValue() {
        return expectedValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public void setExpectedValue(double expectedValue) {
        this.expectedValue = expectedValue;
        init();
    }
}
