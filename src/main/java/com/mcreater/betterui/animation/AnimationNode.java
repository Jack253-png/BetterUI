package com.mcreater.betterui.animation;

import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

public class AnimationNode {
    private static final List<AnimationNode> nodes = new Vector<>();
    static {
        new Thread("Animation Thread") {
            public void run() {
                while (true) {
                    try {
                        nodes.stream()
                                .filter(a -> !a.stopped)
                                .forEach(AnimationNode::nextFrame);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
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
    private boolean stopped = false;
    private AnimationNode beforeBackNode;
    private Consumer<AnimationNode> onAnimation = m -> {};

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
        if (index < all) {
            index++;
            onAnimation.accept(this);
        }
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
            onAnimation.accept(this);
        }
    }

    public boolean isBacked() {
        return backed;
    }

    public void reset() {
        index = 0;
        onAnimation.accept(this);
    }

    public void setBase(double base) {
        this.base = base;
    }

    public void setAddition(double addition) {
        this.addition = addition;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setOnAnimation(Consumer<AnimationNode> onAnimation) {
        this.onAnimation = onAnimation;
    }

    public String toString() {
        return String.format("%d / %d animation frames, %f start, %f addition", index, all, base, addition);
    }
}
