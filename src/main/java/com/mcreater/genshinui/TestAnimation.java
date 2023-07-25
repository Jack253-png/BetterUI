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

        /*JFrame frame = new JFrame("Animation") {
            public void paint(Graphics g) {
                paintComponents(g);
            }

            public void paintComponents(Graphics g) {
                List<Pair<Double, Double>> vertices = new Vector<>();
                int x = 0;
                int y = 0;
                int radius = 100;
                int n = 1000;
                for (int i = 0; i < n; i++) {
                    double xtemp = (Math.cos(2 * PI * i / n)) * radius + x;
                    double ytemp = (Math.sin(2 * PI * i / n)) * radius + y;
                    System.out.printf("%f,%f");
                }
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1000, 1000);
        frame.setVisible(true);*/

        System.load("D:/mods/betterui-1.18.x/run/KAIMyEntitySaba.dll");
    }
}
