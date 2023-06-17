package com.mcreater.betterui.animation;

public class AnimationNode {
    private int index;
    private final int all;
    public AnimationNode(int i, int all) {
        index = i;
        this.all = all;
    }

    public int getIndex() {
        return index;
    }
    public int getAll() {
        return all;
    }
    public void nextFrame() {
        index++;
    }
}
