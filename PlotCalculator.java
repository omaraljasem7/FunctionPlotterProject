// src/PlotCalculator.java

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlotCalculator {
    public static List<Point2D.Double> compute(
            Expr ast, double xmin, double xmax, int samples) {
        List<Point2D.Double> pts = new ArrayList<>();
        for (int i = 0; i <= samples; i++) {
            double x = xmin + i*(xmax-xmin)/samples;
            double y = Evaluator.eval(ast, Map.of("x", x));
            pts.add(new Point2D.Double(x,y));
        }
        return pts;
    }
}
