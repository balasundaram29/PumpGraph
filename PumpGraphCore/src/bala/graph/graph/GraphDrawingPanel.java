package bala.graph.graph;

import bala.graph.utilities.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ANNAIENG
 */
public class GraphDrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

    Graph graph;
    private boolean tracing = false;
    private JFrame traceFrame;
    private Rectangle2D dataArea, plotArea;
    Cursor oldCursor;
    JFrame f;

    public GraphDrawingPanel(Graph graph) {
        this.graph = graph;
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
        oldCursor = this.getCursor();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        int w = this.getWidth();
        int h = this.getHeight();
        Insets insets = getInsets();
        plotArea = new Rectangle2D.Double(insets.left, insets.top, w - insets.left - insets.right, h - insets.top - insets.bottom);

        Color saved = g2D.getColor();
        g2D.setColor(Color.white);
        g2D.fill(plotArea);
        g2D.setColor(saved);
        Rectangle2D panelArea = new Rectangle2D.Double(0, 0, w, h);
        try {
            dataArea = graph.drawGraph(g2D, plotArea);
        } catch (Exception ex) {
            Logger.getLogger(GraphDrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if (tracing) {
                traceFrame.dispose();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//return;
            }//already tracing and mouse is now clicked needing frame closure.
            Cursor cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
            this.setCursor(cursor);
            tracing = !tracing;
            traceFrame = new TraceFrame("Trace Values Against Discharge");
            traceFrame.setBounds((int) dataArea.getMinX() + 10, (int) plotArea.getMinY() + 40,
                    (int) dataArea.getWidth(), (int) (dataArea.getMinY() - plotArea.getMinY()));
            traceFrame.getContentPane().setBackground(Color.white);
            traceFrame.setVisible(tracing);
            traceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mouseMoved(e);
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseDragged(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseMoved(MouseEvent e) {
        try {
            if (traceFrame == null) {
                return;
            }

            double x = e.getX();
            double y = e.getY();
            Plot plot = graph.getPlot();
            Renderer cRenderer = plot.getRendererByDatasetType(DatasetAndCurveType.DISCHARGE_VS_CURRENT);
            Dataset cSet = cRenderer.getDataset();
            double scaleX = cSet.getDomainAxis().getScale(dataArea);
            double disch = SpaceConverter.convertFromJava2DToUser2D(x, scaleX, dataArea, RectangleEdge.LEFT);
            Double maxValueX = GraphUtilities.getMax(cSet.getXArray());
            if (disch < 0 || disch > maxValueX) {
                return;
            }
            double scaleY = cSet.getRangeAxis().getScale(dataArea);
            Drawable func = cRenderer.getFunction();
            double current = func.getYValue(disch);

            Renderer hRenderer = plot.getRendererByDatasetType(DatasetAndCurveType.DISCHARGE_VS_HEAD);
            Dataset hSet = hRenderer.getDataset();
            scaleY = hSet.getRangeAxis().getScale(dataArea);
            func = hRenderer.getFunction();
            double head = func.getYValue(disch);
            Renderer eRenderer = plot.getRendererByDatasetType(DatasetAndCurveType.DISCHARGE_VS_EFFICIENCY);
            Dataset eSet = eRenderer.getDataset();
            scaleY = eSet.getRangeAxis().getScale(dataArea);
            func = eRenderer.getFunction();
            double eff = func.getYValue(disch);

            traceFrame.getContentPane().removeAll();
            JLabel l = new JLabel();
            //l.setFont(new Font(Font.SANS_SERIF,8,Font.PLAIN));
            l.setBackground(Color.white);
            traceFrame.getContentPane().add(l, BorderLayout.CENTER);
            String dischStr = String.format("%,.2f", (float) disch);
            String currStr = String.format("%,.2f", (float) current);
            String headStr = String.format("%,.2f", (float) head);
            String effStr = String.format("%,.2f", (float) eff);


            String s = "Disch =  " + dischStr + " lps ;    " + "Head  =  " + headStr + "  m  ;       " + "Overall Eff.  " + effStr
                    + "  % ;       " + " Current =  " + currStr + "  Amps ;";
            l.setText(s);
            traceFrame.validate();
        } catch (Exception ex) {
            Logger.getLogger(GraphDrawingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class TraceFrame extends JFrame implements WindowListener {

        public TraceFrame(String s) {
            super(s);
            this.addWindowListener(this);
            setAlwaysOnTop(true);
        }
        
        @Override
        public void windowOpened(WindowEvent e) {
            // throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowClosing(WindowEvent e) {

            // throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowClosed(WindowEvent e) {
            //  throw new UnsupportedOperationException("Not supported yet.");
            tracing = false;
        }

        @Override
        public void windowIconified(WindowEvent e) {
            //  throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowActivated(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
