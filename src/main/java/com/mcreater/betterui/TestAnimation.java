package com.mcreater.betterui;

import com.mcreater.betterui.animation.AnimationGenerator;
import com.mcreater.betterui.animation.AnimationNode;
import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;

public class TestAnimation {
    public static void main(String[] args) {
        AnimationNode node = new AnimationNode(0, 1000);
        for (int i = 0; i < 1000; i++) {
            System.out.println(AnimationGenerator.QUADRATIC_EASEIN.applyAsDouble(node, new DoubleDoubleImmutablePair(0, 1000)));
            node.nextFrame();
        }
    }
}
