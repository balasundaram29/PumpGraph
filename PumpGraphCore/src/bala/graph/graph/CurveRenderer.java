package bala.graph.graph;


/**
 * Write a description of class LoessSmoothRenderer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import bala.graph.utilities.*;
import java.awt.*;
import java.awt.geom.*;

public class CurveRenderer extends Renderer {

    private Stroke stroke;
    private Dataset dataset;
    private Drawable function;//= new LoessFunction(dataset);;
    private final  Stroke DEFAULT_CURVE_STROKE = new BasicStroke(1.0f);

    /**
     * Constructor for objects of class LoessSmoothRenderer
     */
    public CurveRenderer() {
        this.stroke = DEFAULT_CURVE_STROKE;
    }

    /**
     * Sets a  dataset to the renderer.Note that, in turn, the dataset has a reference
     * to its RangeAxis and a Domain Axis.
     * @param  the dataset to be set.
     * @return     
     */
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
        function = (Drawable) new LoessFunction(dataset);
    }

    public Dataset getDataset() {
        return this.dataset;

    }

    public void drawCurve(Graphics2D g2D, Rectangle2D dataArea)throws Exception {
        g2D.setPaint(getCurvePaint());

        g2D.setStroke(stroke);
        RangeAxis rAxis = dataset.getRangeAxis();
        DomainAxis dAxis = dataset.getDomainAxis();
        //AffineTransform saved = g2D.getTransform();
        double scaleX = dAxis.getScale(dataArea);
        double scaleY = rAxis.getScale(dataArea);
        Double maxValueX = GraphUtilities.getMax(dataset.getXArray());
        double zeroJava2D = SpaceConverter.convertFromUserToJava2D(0.0, scaleX, dataArea, RectangleEdge.LEFT);
        double yAtZeroJava2D = SpaceConverter.convertFromUserToJava2D(function.getYValue(0.0), scaleY, dataArea, RectangleEdge.BOTTOM);
        Point2D firstPoint = new Point2D.Double(zeroJava2D, yAtZeroJava2D);
        double inc = maxValueX / 100.0 / 10.00;
        double x = 0.0;

        while (true) {
            x = x + inc;
            if (x > maxValueX) {
                break;
            }

            double xJava2D = SpaceConverter.convertFromUserToJava2D(x, scaleX, dataArea, RectangleEdge.LEFT);
            double yJava2D = SpaceConverter.convertFromUserToJava2D(function.getYValue(x), scaleY, dataArea, RectangleEdge.BOTTOM);

            Point2D nextPoint = new Point2D.Double(xJava2D, yJava2D);
            g2D.draw(new Line2D.Double(firstPoint, nextPoint));
            firstPoint = nextPoint;
        }

       // g2D.setTransform(saved);
        g2D.setStroke(stroke);

        double[] xArray = dataset.getXArray();
        double[] yArray = dataset.getYArray();
        for (int i = 0; i < dataset.getSize(); i++) {
            double xZero = dataArea.getMinX();
            double xUser = xArray[i];//dataset.getXValue(i);
            double yZero = dataArea.getMaxY();
            double yUser = yArray[i];//dataset.getYValue(i);
            int x1 = (int) SpaceConverter.convertFromUserToJava2D(xUser, scaleX, dataArea, RectangleEdge.LEFT);
            int y1 = (int) SpaceConverter.convertFromUserToJava2D(yUser, scaleY, dataArea, RectangleEdge.BOTTOM);
            Ellipse2D smallCircle = new Ellipse2D.Double(-1,-1,2,2);
            Shape CircleAtPlace = ShapeUtilities.createTranslatedShape(smallCircle, x1, y1);
                    switch (dataset.getType()) {


                case DISCHARGE_VS_EFFICIENCY:
                    Rectangle2D rectangle = new Rectangle2D.Double(-3.0, -3.0, 6.0, 6.0);
                    g2D.draw(ShapeUtilities.createTranslatedShape(rectangle, x1, y1));
                    g2D.fill(CircleAtPlace);
                    break;
                case DISCHARGE_VS_CURRENT:
                    Ellipse2D circle = new Ellipse2D.Double(-3.0, -3.0, 6, 6);
                    g2D.draw(ShapeUtilities.createTranslatedShape(circle, x1, y1));
                    g2D.fill(CircleAtPlace);
                    break;
                case DISCHARGE_VS_HEAD:
                    Shape triangle = ShapeUtilities.createUpTriangle(4.00f);
                    g2D.draw(ShapeUtilities.createTranslatedShape(triangle, x1, y1-2));
                    g2D.fill(CircleAtPlace);
               
            }
        }

    }

    public Drawable getFunction() {
      //  this.loessFunction = (Drawable) new LoessFunction(dataset);

        return this.function;
    }

    /**
     * @return the loessFunction
     */
    public LoessFunction getLoessFunction() {
        return (LoessFunction)function;
    }

    /**
     * @param stroke the stroke to set
     */
    @Override
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    /**
     * @return the DEFAULT_CURVE_STROKE
     */
    public Stroke getDEFAULT_CURVE_STROKE() {
        return DEFAULT_CURVE_STROKE;
    }
}
