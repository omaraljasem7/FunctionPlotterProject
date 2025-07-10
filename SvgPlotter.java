// src/SvgPlotter.java

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Locale;

//public class SvgPlotter {
//    private final int w, h;
//    private final double xmin, xmax, ymin, ymax;
//
//    public SvgPlotter(int w, int h,
//                      double xmin, double xmax,
//                      double ymin, double ymax) {
//        this.w = w; this.h = h;
//        this.xmin = xmin; this.xmax = xmax;
//        this.ymin = ymin; this.ymax = ymax;
//    }
//
//    public String plot(List<Point2D.Double> pts, String colorHex) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<svg width='").append(w)
//                .append("' height='").append(h)
//                .append("' xmlns='http://www.w3.org/2000/svg'>\n");
//        // Achsen
//        double midX = w*(0 - xmin)/(xmax-xmin);
//        double midY = h - h*(0 - ymin)/(ymax-ymin);
//        sb.append("<line x1='0' y1='").append(midY)
//                .append("' x2='").append(w).append("' y2='").append(midY)
//                .append("' stroke='#888'/>\n");
//        sb.append("<line x1='").append(midX).append("' y1='0'")
//                .append(" x2='").append(midX).append("' y2='").append(h)
//                .append("' stroke='#888'/>\n");
//        // Kurve
//        sb.append("<polyline fill='none' stroke='#").append(colorHex)
//                .append("' points='");
//        for (var p : pts) {
//            double svgX = w * (p.x - xmin)/(xmax-xmin);
//            double svgY = h - h*(p.y - ymin)/(ymax-ymin);
//            sb.append((int)svgX).append(",").append((int)svgY).append(" ");
//        }
//        sb.append("'/>\n</svg>");
//        return sb.toString();
//    }
//}




//public class SvgPlotter {
//    private final int    w, h;
//    private final double xmin, xmax, ymin, ymax;
//    // für das Grid
//    // Abstand zwischen zwei Gitterlinien in „logischen“ Einheiten
//    private  double gridStepX = 1.0;
//    private  double gridStepY = 1.0;
//
//    // Skalierung der x-achse und y-achse
//    // Abstand in Pixeln von der Achse zur Beschriftung
//    private static final double LABEL_OFFSET_X   = 6;   // horizontaler Abstand
//    private static final double LABEL_OFFSET_Y   = 12;  // vertikaler Abstand
//    private static final int    LABEL_FONT_SIZE  = 10;  // Schriftgröße in px
//
//
//
//    public SvgPlotter(int w, int h,
//                      double xmin, double xmax,
//                      double ymin, double ymax,double gridStepX,double gridStepY) {
//        this.w = w; this.h = h;
//        this.xmin = xmin; this.xmax = xmax;
//        this.ymin = ymin; this.ymax = ymax;
//        this.gridStepX=gridStepX;this.gridStepY=gridStepY;
//    }
//    //Grid
//    private void drawGrid(StringBuilder sb) {
//        // X-Gitterlinien
//        double startX = Math.ceil(xmin / gridStepX) * gridStepX;
//        double endX   = Math.floor(xmax / gridStepX) * gridStepX;
//        for (double x = startX; x <= endX; x += gridStepX) {
//            double sx = mapX(x);
//            sb.append(String.format(Locale.US,
//                    "<line x1='%.1f' y1='%.1f' x2='%.1f' y2='%.1f' "
//                            + "stroke='#ccc' stroke-width='1.0'/>\n",
//                    sx, mapY(ymin), sx, mapY(ymax)
//            ));
//        }
//        // Y-Gitterlinien
//        double startY = Math.ceil(ymin / gridStepY) * gridStepY;
//        double endY   = Math.floor(ymax / gridStepY) * gridStepY;
//        for (double y = startY; y <= endY; y += gridStepY) {
//            double sy = mapY(y);
//            sb.append(String.format(Locale.US,
//                    "<line x1='%.1f' y1='%.1f' x2='%.1f' y2='%.1f' "
//                            + "stroke='#ccc' stroke-width='1.0'/>\n",
//                    mapX(xmin), sy, mapX(xmax), sy
//            ));
//        }
//    }
//
//
//    /** Logisches x → SVG-Pixel */
//    private double mapX(double x) {
//        return (x - xmin) / (xmax - xmin) * w;
//    }
//
//    /** Logisches y → SVG-Pixel (SVG-Y-Achse ist umgekehrt) */
//    private double mapY(double y) {
//        return h - ( (y - ymin) / (ymax - ymin) * h );
//    }
//
//    //labels für die x-achse und y-achse
//    private void drawLabels(StringBuilder sb) {
//        // die gleichen Grenzen wie im Grid
//        double startX = Math.ceil(xmin / gridStepX) * gridStepX;
//        double endX   = Math.floor(xmax / gridStepX) * gridStepX;
//        double startY = Math.ceil(ymin / gridStepY) * gridStepY;
//        double endY   = Math.floor(ymax / gridStepY) * gridStepY;
//
//        // 1) X-Achsen-Labels unter y=0
//        double labelY = mapY(0) + LABEL_OFFSET_Y;
//        for (double x = startX; x <= endX; x += gridStepX) {
//            double sx = mapX(x);
//            sb.append(String.format(Locale.US,
//                    "<text x='%.1f' y='%.1f' font-size='%d' text-anchor='middle'>%.1f</text>\n",
//                    sx, labelY, LABEL_FONT_SIZE, x
//            ));
//        }
//
//
//        // 2) Y-Achsen-Labels links von x=0
//        double labelX = mapX(0) - LABEL_OFFSET_X;
//        for (double y = startY; y <= endY; y += gridStepY) {
//            if (y == 0.0)
//                continue;        // <= hier Null überspringen
//            double sy = mapY(y);
//            sb.append(String.format(Locale.US,
//                    "<text x='%.1f' y='%.1f' font-size='%d' text-anchor='end' dominant-baseline='middle'>%.1f</text>\n",
//                    labelX, sy, LABEL_FONT_SIZE, y
//            ));
//        }
//
//
//    }
//
//    public String plot(List<Point2D.Double> pts, String colorHex) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<svg width='").append(w)
//                .append("' height='").append(h)
//                .append("' xmlns='http://www.w3.org/2000/svg'>\n");
//        // 1) GRID HINTERGRUND
//        drawGrid(sb);
//
//        // 2) Achsenbeschriftungen zeichnen
//        drawLabels(sb);
//
//
//        // Achsen
//        double midX = w*(0 - xmin)/(xmax-xmin);
//        double midY = h - h*(0 - ymin)/(ymax-ymin);
//        sb.append("<line x1='0' y1='").append(midY)
//                .append("' x2='").append(w).append("' y2='").append(midY)
//                .append("' stroke='#888'/>\n");
//        sb.append("<line x1='").append(midX).append("' y1='0'")
//                .append(" x2='").append(midX).append("' y2='").append(h)
//                .append("' stroke='#888'/>\n");
//
//        // Funktion in Segmenten zeichnen
//        StringBuilder seg = new StringBuilder();
//        boolean inSeg = false;
//        for (Point2D.Double p : pts) {
//            double x = p.x, y = p.y;
//            if (Double.isFinite(y)) {
//                double svgX = w * (x - xmin)/(xmax-xmin);
//                double svgY = h - h*(y - ymin)/(ymax-ymin);
//                if (!inSeg) {
//                    // Neues Segment aufmachen
//                    seg.setLength(0);
//                    seg.append(svgX).append(",").append(svgY);
//                    inSeg = true;
//                } else {
//                    seg.append(" ").append((int)svgX)
//                            .append(",").append((int)svgY);
//                }
//            } else {
//                // y ist NaN/Inf → bestehendes Segment beenden
//                if (inSeg) {
//                    appendPolyline(sb, seg.toString(), colorHex);
//                    inSeg = false;
//                }
//            }
//        }
//        // letzten Segment-Rest zeichnen
//        if (inSeg) {
//            appendPolyline(sb, seg.toString(), colorHex);
//        }
//
//        sb.append("</svg>");
//
//        return sb.toString();
//    }
//
//    private void appendPolyline(StringBuilder sb, String points, String colorHex) {
//        sb.append("<polyline fill='none' stroke='#")
//                .append(colorHex)
//                .append("' points='")
//                .append(points)
//                .append("'/>\n");
//    }
//
//}
import java.awt.geom.Point2D;
import java.util.List;

public class SvgPlotter {
    private final StringBuilder sb = new StringBuilder();
    private final int width;
    private final int height;
    private final double xmin;
    private final double xmax;
    private final double ymin;
    private final double ymax;
    private final double scaleX;
    private final double scaleY;

    /**
     * @param width   Breite des SVG in px
     * @param height  Höhe des SVG in px
     * @param xmin    minimaler x-Wert
     * @param xmax    maximaler x-Wert
     * @param ymin    minimaler y-Wert
     * @param ymax    maximaler y-Wert
     * @param scaleX  zusätzlicher x-Skalierungsfaktor (meist 1.0)
     * @param scaleY  zusätzlicher y-Skalierungsfaktor (meist 1.0)
     */
    public SvgPlotter(int width, int height,
                      double xmin, double xmax,
                      double ymin, double ymax,
                      double scaleX, double scaleY) {
        this.width   = width;
        this.height  = height;
        this.xmin    = xmin;
        this.xmax    = xmax;
        this.ymin    = ymin;
        this.ymax    = ymax;
        this.scaleX  = scaleX;
        this.scaleY  = scaleY;

        // SVG-Header
        sb.append("<svg width='").append(width)
                .append("' height='").append(height)
                .append("' viewBox='0 0 ").append(width)
                .append(" ").append(height)
                .append("' xmlns='http://www.w3.org/2000/svg'>\n");

        drawGrid();
        drawAxes();
        drawLabels();
    }

    /**
     * Fügt eine Kurve (Polyline) in der angegebenen Farbe hinzu.
     * Mehrfache Aufrufe sammeln mehrere Kurven im selben SVG.
     * @param pts      List der Punkte (x,y) im Koordinatensystem
     * @param colorHex Farbcode ohne „#“, z.B. "ff0000" für Rot
     */
    public void plot(List<Point2D.Double> pts, String colorHex) {
        StringBuilder points = new StringBuilder();
        boolean drawing = false;

        for (Point2D.Double p : pts) {
            double x = p.x * scaleX;
            double y = p.y * scaleY;

            if (Double.isFinite(y)) {
                double svgX = mapX(x);
                double svgY = mapY(y);

                if (!drawing) {
                    points.append((int) svgX).append(",").append((int) svgY);
                    drawing = true;
                } else {
                    points.append(" ")
                            .append((int) svgX).append(",")
                            .append((int) svgY);
                }
            } else if (drawing) {
                appendPolyline(points.toString(), colorHex);
                points.setLength(0);
                drawing = false;
            }

        }

        if (drawing) {
            appendPolyline(points.toString(), colorHex);
        }
    }

    /** Schließt das SVG und gibt den vollständigen String zurück. */
    public String buildSvg() {
        sb.append("</svg>");
        return sb.toString();
    }

    // —————————————————————————————————————————————————————————————————————————————
    // Private Hilfsmethoden
    // —————————————————————————————————————————————————————————————————————————————

    /** Zeichnet das Gitternetz */
    private void drawGrid() {
        sb.append("<g stroke='#ccc' stroke-opacity='0.3'>\n");
        // Vertikale Linien
        for (int i = (int) Math.ceil(xmin); i <= xmax; i++) {
            double x = mapX(i);
            sb.append("<line x1='").append(x)
                    .append("' y1='0' x2='").append(x)
                    .append("' y2='").append(height)
                    .append("'/>\n");
        }
        // Horizontale Linien
        for (int j = (int) Math.ceil(ymin); j <= ymax; j++) {
            double y = mapY(j);
            sb.append("<line x1='0' y1='").append(y)
                    .append("' x2='").append(width)
                    .append("' y2='").append(y)
                    .append("'/>\n");
        }
        sb.append("</g>\n");
    }

    /** Zeichnet die x- und y-Achsen */
    private void drawAxes() {
        sb.append("<g stroke='#000' stroke-width='1.5'>\n");
        // x-Achse (y=0)
        if (ymin <= 0 && ymax >= 0) {
            double y0 = mapY(0);
            sb.append("<line x1='0' y1='").append(y0)
                    .append("' x2='").append(width)
                    .append("' y2='").append(y0)
                    .append("'/>\n");
        }
        // y-Achse (x=0)
        if (xmin <= 0 && xmax >= 0) {
            double x0 = mapX(0);
            sb.append("<line x1='").append(x0)
                    .append("' y1='0' x2='").append(x0)
                    .append("' y2='").append(height)
                    .append("'/>\n");
        }
        sb.append("</g>\n");
    }

    /** Beschriftet jede ganze Einheit auf x- und y-Achse */
    private void drawLabels() {
        sb.append("<g font-family='sans-serif' font-size='10' fill='#000'>\n");
        // x-Achsen-Labels
        for (int i = (int) Math.ceil(xmin); i <= xmax; i++) {
            double x = mapX(i);
            double y = mapY(0);
            sb.append("<text x='").append(x + 2)
                    .append("' y='").append(y - 2)
                    .append("'>").append(i).append("</text>\n");
        }
        // y-Achsen-Labels
        for (int j = (int) Math.ceil(ymin); j <= ymax; j++) {
            if(j == 0){
                continue;
            }
            double x = mapX(0);
            double y = mapY(j);
            sb.append("<text x='").append(x + 2)
                    .append("' y='").append(y + 12)
                    .append("'>").append(j).append("</text>\n");
        }
        sb.append("</g>\n");
    }

    /** Fügt eine Polyline-Kurve in der Farbe colorHex hinzu */
    private void appendPolyline(String pts, String colorHex) {
        sb.append("<polyline fill='none' stroke='#").append(colorHex)
                .append("' stroke-width='1.2' points='")
                .append(pts)
                .append("'/>\n");
    }

    /** Transformiert einen x-Wert in SVG-Koordinate */
    private double mapX(double x) {
        return (x - xmin) / (xmax - xmin) * width;
    }

    /** Transformiert einen y-Wert in SVG-Koordinate (invertiert) */
    private double mapY(double y) {
        return height - (y - ymin) / (ymax - ymin) * height;
    }
}
