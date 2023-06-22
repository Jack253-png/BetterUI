package com.mcreater.betterui.animation;

import java.util.List;
import java.util.Vector;

import static com.mcreater.betterui.config.Configuration.OPTION_ANIMATION_INTERVAL;

public class AnimationNode {
    private static final List<AnimationNode> nodes = new Vector<>();
    static {
        new Thread("Animation Thread") {
            public void run() {
                while (true) {
                    nodes.forEach(AnimationNode::nextFrame);
                    try {
                        Thread.sleep(OPTION_ANIMATION_INTERVAL.getValue());
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
    private AnimationNode beforeBackNode;

    public AnimationNode getBeforeBackNode() {
        return beforeBackNode;
    }

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
        if (!backed) {
            beforeBackNode = new AnimationNode(index, all, base, addition);

            backed = true;
            reset();
            double tempBase = base;
            double tempAddition = addition;

            base = tempBase + tempAddition;
            addition = -addition;
        }
    }

    public boolean isBacked() {
        return backed;
    }

    public void reset() {
        index = 0;
    }

    public String toString() {
        return String.format("%d / %d animation frames, %f start, %f addition", index, all, base, addition);
    }
}
