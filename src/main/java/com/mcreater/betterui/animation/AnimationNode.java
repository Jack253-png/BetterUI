package com.mcreater.betterui.animation;

import java.util.List;
import java.util.Vector;

public class AnimationNode {
    private static final List<AnimationNode> nodes = new Vector<>();
    static {
        new Thread("Animation Thread") {
            public void run() {
                while (true) {
                    nodes.forEach(AnimationNode::nextFrame);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private int index;
    private final int all;
    private double base;
    private double addition;
    private boolean backed = false;
    public AnimationNode(int i, int all, double base, double addition) {
        index = i;
        this.all = all;
        this.base = base;
        this.addition = addition;
        nodes.add(this);
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
        if (index < all) index++;
    }
    public void back() {
        backed = true;
        index = 0;
        double tempBase = base;
        double tempAddition = addition;

        base = tempBase + tempAddition;
        addition = -addition;
    }

    public boolean isBacked() {
        return backed;
    }

    public String toString() {
        return String.format("%d / %d animation frames, %f start, %f addition", index, all, base, addition);
    }
}
