package com.mcreater.betterui;

import com.mcreater.betterui.animation.AnimationGenerator;
import com.mcreater.betterui.animation.AnimationNode;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class TestAnimation {
    public static class FuncShow extends JPanel {
        private final List<List<Double>> values;
        public FuncShow(List<List<Double>> values) {
            this.values = values;
        }
        protected void paintComponent(Graphics g) {
            final int[] val = {0};
            values.forEach(l -> {
                l.forEach(pair -> {
                    g.fillRect(val[0], 875 - pair.intValue(), 2, 2);
                    val[0]++;
                });
                val[0] = 0;
            });
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Double> doubles = new Vector<>();
        List<Double> doubles1 = new Vector<>();
        List<Double> doubles2 = new Vector<>();
        List<List<Double>> res = new Vector<>();

        AnimationNode node = new AnimationNode(0, 1000, 0, 750);
        for (int i = 0; i < 1000; i++) {
            doubles.add(AnimationGenerator.CIRCULAR_EASEIN.applyAsDouble(node));
            doubles1.add(AnimationGenerator.CIRCULAR_EASEOUT.applyAsDouble(node));
            doubles2.add(AnimationGenerator.CIRCULAR_EASEINOUT.applyAsDouble(node));
            node.nextFrame();
        }

        res.add(doubles);
        res.add(doubles1);
        res.add(doubles2);

        frame.add(new FuncShow(res));

        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
}
