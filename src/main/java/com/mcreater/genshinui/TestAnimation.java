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
        /*
        * 0, 10 -> 10, 0
          90, 0 -> 100, 10
          100, 10 -> 90, 20
          10, 20 -> 0, 10
        * */
        /*
        JFrame frame = new JFrame("Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Double> doubles = new Vector<>();
        List<List<Double>> res = new Vector<>();

        genArc(10, 20, 0, 10)
                .forEach(i -> {
                    System.out.printf("%d, %d\n", i.getLeft(), i.getRight());
//                    doubles.add(i.getLeft(), Double.valueOf(i.getRight()));
                });

        res.add(doubles);

        frame.add(new FuncShow(res));

        frame.setSize(1000, 1000);
        frame.setVisible(true);
        */
    }
}
