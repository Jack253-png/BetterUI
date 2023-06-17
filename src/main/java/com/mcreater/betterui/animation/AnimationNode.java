package com.mcreater.betterui.animation;

public class AnimationNode {
    private int index;
    private final int all;
    private final double base;
    private final double addition;
    public AnimationNode(int i, int all, double base, double addition) {
        index = i;
        this.all = all;
        this.base = base;
        this.addition = addition;
    }

    public int getIndex() {
        return index;
    }
    public int getAll() {
        return all;
    }

    public double getBase() {
        return base;
    }

    public double getAddition() {
        return addition;
    }

    public void nextFrame() {
        index++;
    }
}
