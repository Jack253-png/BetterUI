package com.mcreater.genshinui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestAnimation {
    public static class FuncShow extends JPanel {
        private final List<List<Double>> values;
        public FuncShow(List<List<Double>> values) {
            this.values = values;
        }
        protected void paintComponent(Graphics g) {
            g.setColor(new Color(50, 50, 50));
            g.fillRect(0, 0, 10000, 10000);
            g.setColor(new Color(253, 253, 253));
            final int[] val = {0};
            values.forEach(l -> {
                l.forEach(pair -> {
                    g.fillRect(val[0], 700 - pair.intValue(), 2, 2);
                    val[0]++;
                });
                val[0] = 0;
            });
        }
    }
    public static void main(String[] args) {
        /*JFrame frame = new JFrame("Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Double> doubles = new Vector<>();
        List<Double> doubles1 = new Vector<>();
        List<Double> doubles2 = new Vector<>();
        List<List<Double>> res = new Vector<>();

        AnimationNode node = new AnimationNode(0, 1000, 0, 500);
        for (int i = 0; i < 1000; i++) {
            doubles.add(AnimationGenerator.SINUSOIDAL_EASEIN.applyAsDouble(node));
            doubles1.add(AnimationGenerator.SINUSOIDAL_EASEOUT.applyAsDouble(node));
            doubles2.add(AnimationGenerator.SINUSOIDAL_EASEINOUT.applyAsDouble(node));
        }

        res.add(doubles);
        res.add(doubles1);
        res.add(doubles2);

        frame.add(new FuncShow(res));

        frame.setSize(1000, 1000);
        frame.setVisible(true);*/
    }
}
