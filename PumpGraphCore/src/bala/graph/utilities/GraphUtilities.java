package bala.graph.utilities;

import bala.graph.graph.Dataset;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;

public class GraphUtilities {

    static double[] prefScales = {1, 1.2, 1.6, 2, 2.4,2.6, 3, 3.6, 4, 4.4, 5, 6, 7, 8, 10, 12, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100};

    public GraphUtilities() {
    }

    public static double getMax(double[] array) {
        double max = array[0];
        for (double member : array) {
            if (max < member) {
                max = member;
            }
        }
        return max;
    }

    public static double getScaleY(Dataset dataset, Rectangle2D dataArea) {

        double yMax = GraphUtilities.getMax(dataset.getYArray());
        double scaleFactor = 1.20 * yMax;
        if (yMax <= 100) {
            for (int i = 0; i < prefScales.length - 1; i++) {
                if (yMax * 1.02 > prefScales[i] && yMax * 1.02 <= prefScales[i + 1]) {
                    scaleFactor = prefScales[i + 1];
                }
            }
        }
        double scaleY = dataArea.getHeight() / scaleFactor;//(1.20 * yMax);
        return scaleY;
    }

    public static double getScaleX(Dataset dataset, Rectangle2D dataArea) {
        double xMax = GraphUtilities.getMax(dataset.getXArray());
        double scaleFactor = 1.20 * xMax;
        if (xMax <= 100) {
            for (int i = 0; i < prefScales.length - 1; i++) {
                if (xMax * 1.02 > prefScales[i] && xMax * 1.02 <= prefScales[i + 1]) {
                    scaleFactor = prefScales[i + 1];
                }
            }
        }
        double scaleX = dataArea.getWidth() / scaleFactor;

        return scaleX;
    }
}
