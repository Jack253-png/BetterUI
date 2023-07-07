package com.mcreater.genshinui.animation;

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
    private Consumer<Double> callback;
    public void setCallback(@NotNull Consumer<Double> func) {
        this.callback = func;
    }
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

    public boolean animationing() {
        return currentValue == expectedValue;
    }
    public void init() {
        node = new AnimationNode(0, delta, currentValue, expectedValue - currentValue);
        node.setOnAnimation(n -> {
            if (n.getAll() > n.getIndex()) {
                this.currentValue = func.apply(n);
                callback.accept(currentValue);
            }
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
