package bala.graph.utilities;

import bala.graph.graph.*;
import bala.graph.gui.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import jxl.format.Border;
import net.miginfocom.swing.MigLayout;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bala
 */
public class ModifiedPrintUtility implements Printable {

    Component componentToBePrinted;
    ReadingEntryPanel entryPanel;
    ComponentToBePrintedType type;
    GraphPanel gPanel;
    int originalW, originalH;
    Rectangle rect;
    Container frame;

    public ModifiedPrintUtility(ReadingEntryPanel entryPanel, ComponentToBePrintedType type) {
        this.entryPanel = entryPanel;
        this.type = type;

    }

    public ModifiedPrintUtility(GraphPanel gPanel, ComponentToBePrintedType type) {
        this.gPanel = gPanel;
        frame = (Container) gPanel.getParent();
        this.type = type;
        originalW = gPanel.getW();
        originalH = gPanel.getH();
        rect = gPanel.getBounds();
        // gPanel.set
    }

    public ModifiedPrintUtility(ReadingEntryPanel entryPanel, GraphPanel gp, ComponentToBePrintedType type) {
        this.entryPanel = entryPanel;
        this.gPanel = gp;
        this.type = type;

    }

    public void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PrinterJob pj = PrinterJob.getPrinterJob();
        File file = new File("myPFValues.pf");

        PageFormat pf = pj.defaultPage();
        try {
            // System.out.println("Page format path is " + file.getCanonicalPath());
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));

            PageFormatValues pfv = (PageFormatValues) is.readObject();
            if (pfv.getOrient() == GSPageOrientation.LANDSCAPE) {
                pf.setOrientation(PageFormat.LANDSCAPE);
            } else {
                pf.setOrientation(PageFormat.PORTRAIT);

            }
            Paper paper = new Paper();

            paper.setSize(pfv.getW(), pfv.getH());
            paper.setImageableArea(pfv.getImX(), pfv.getImY(), pfv.getImW(), pfv.getImH());
            pf.setPaper(paper);

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        PageFormat pageFormat = pj.pageDialog(pf);

        // PageFormat pageFormat = pj.pageDialog(pj.defaultPage());
        int paperWidth = (int) pageFormat.getWidth();
        int paperHeight = (int) pageFormat.getHeight();
        int prnW = (int) pageFormat.getImageableWidth();
        int prnH = (int) pageFormat.getImageableHeight();
        int imX = (int) pageFormat.getImageableX();
        int imY = (int) pageFormat.getImageableY();
        GSPageOrientation orient;
        if (pageFormat.getOrientation() == pageFormat.LANDSCAPE) {
            orient = GSPageOrientation.LANDSCAPE;
        } else {
            orient = GSPageOrientation.PORTRAIT;
        }
        PageFormatValues values = new PageFormatValues(orient, pageFormat.getPaper().getWidth(), pageFormat.getPaper().getHeight(),
                pageFormat.getPaper().getImageableX(), pageFormat.getPaper().getImageableY(),
                pageFormat.getPaper().getImageableWidth(), pageFormat.getPaper().getImageableHeight());
        try {

            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(values);
        } catch (Exception ex) {
            // System.out.println("Could not write file");
        }

        JFrame f = new JFrame("Print Preview");
        f.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        f.setSize(paperWidth, paperHeight + 8);
        f.setLayout(null);
        // f.setLayout(new MigLayout("insets 0 0 0 0","",""));
        f.setBackground(Color.white);
        if (type == ComponentToBePrintedType.GRAPH) {
            GraphPanel gp = new GraphPanel(gPanel.getReportPanel());            //change size for printing
            gp.setSize(prnW, prnH);
            for (Renderer renderer : gp.getGraph().getPlot().getRendererList()) {
                // ((LoessSmoothRenderer) renderer).getLoessFunction().setBandwidth(entryPanel.getDesiredSmoothnessPercentage());
                renderer.setCurvePaint(Color.black);
            }
            for (RangeAxis axis : gp.getGraph().getPlot().getRangeAxesList()) {
                axis.setAxisLinePaint(Color.black);

            }

            if (gPanel != null) {

                GridLinesType grid = gPanel.getGraph().getPlot().getGridLinesType();
                //      rPanel.getGp().getGraph().getPlot().setDataAreaOffset(15);
                gp.getGraph().getPlot().setGridLinesType(grid);
                gp.getGraph().getPlot().setPlotType(ComponentToBePrintedType.ECONOMY_REPORT);

                ArrayList<Renderer> rendererList = gp.getGraph().getPlot().getRendererList();
                double smooth = ((LoessSmoothRenderer) gPanel.getGraph().getPlot().getRenderer(0)).getLoessFunction().getBandwidth();
                double linearity = ((LoessSmoothRenderer) gPanel.getGraph().getPlot().getRenderer(0)).getLoessFunction().getDesiredLinearityFactor();
                for (Renderer renderer : rendererList) {
                    ((LoessSmoothRenderer) renderer).getLoessFunction().setBandwidthByBandwidth(smooth);
                    ((LoessSmoothRenderer) renderer).getLoessFunction().setDesiredLinearityFactor(linearity);
                }
            }
            gp.getGraph().getPlot().getDomainAxis().setAxisLineColor(Color.black);

            gp.setBounds(imX, imY, prnW, prnH);

            gp.setBackground(Color.white);

            f.getContentPane().setBackground(Color.white);
            f.getContentPane().add(gp);
            f.setVisible(true);
            f.getContentPane().getGraphics().clipRect(imX + 100, imY + 50, prnW, prnH);
            componentToBePrinted = gp;
        } else if (type == ComponentToBePrintedType.ECONOMY_REPORT) {
            ReportPanel rPanel = new ReportPanel(entryPanel, true);
            if (gPanel != null) {
                GridLinesType grid = gPanel.getGraph().getPlot().getGridLinesType();
                //      rPanel.getGp().getGraph().getPlot().setDataAreaOffset(15);
                rPanel.getGp().getGraph().getPlot().setGridLinesType(grid);
                rPanel.getGp().getGraph().getPlot().setPlotType(ComponentToBePrintedType.ECONOMY_REPORT);

                ArrayList<Renderer> rendererList = rPanel.getGp().getGraph().getPlot().getRendererList();
                double smooth = ((LoessSmoothRenderer) gPanel.getGraph().getPlot().getRenderer(0)).getLoessFunction().getBandwidth();
                double linearity = ((LoessSmoothRenderer) gPanel.getGraph().getPlot().getRenderer(0)).getLoessFunction().getDesiredLinearityFactor();
                for (Renderer renderer : rendererList) {
                    ((LoessSmoothRenderer) renderer).getLoessFunction().setBandwidthByBandwidth(smooth);
                    ((LoessSmoothRenderer) renderer).getLoessFunction().setDesiredLinearityFactor(linearity);
                }
            }
            rPanel.getGp().setCurveAndAxisPaintTheSame();
            rPanel.setBounds(imX, imY, prnW, prnH);
            rPanel.getGp().getGraph().getPlot().setPlotType(ComponentToBePrintedType.ECONOMY_REPORT);
            rPanel.setBackground(Color.white);
            rPanel.setFontNow(ReportPanel.PRINTABLE_LABEL_FONT);
            rPanel.getReportTable().setFont(ReportPanel.PRINTABLE_LABEL_FONT);
            rPanel.getReportTable().setRowHeight(14);
            rPanel.getReportTable().setForeground(Color.black);
            rPanel.getReportTable().getTableHeader().setForeground(Color.black);
            rPanel.getReportTable().setPreferredSize(new Dimension(rPanel.getWidth(), 80 + rPanel.getReportTable().getRowCount()
                    * rPanel.getReportTable().getRowHeight()));
            // rPanel.setMaximumSize(new Dimension(rPanel.getWidth(), rPanel.getReportTable().getRowCount()
            //       * rPanel.getReportTable().getRowHeight() + 80));//f.getContentPane().setBackground(Color.white);
            //rPanel.setPreferredSize(new Dimension(rPanel.getWidth(), rPanel.getReportTable().getRowCount()
            //      * rPanel.getReportTable().getRowHeight() + 80));
            f.getContentPane().add(rPanel);
            //f.setContentPane(rPanel);
            f.setVisible(true);
            componentToBePrinted = rPanel;
        } else {
            ReportPanel rPanel = new ReportPanel(entryPanel);
            rPanel.setBounds(imX, imY, prnW, prnH);
            rPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            rPanel.setBackground(Color.white);
            rPanel.setFontNow(ReportPanel.PRINTABLE_LABEL_FONT);
            rPanel.getReportTable().setRowHeight(15);
            rPanel.getReportTable().setPreferredSize(new Dimension(rPanel.getWidth(), 80 + rPanel.getReportTable().getRowCount()
                    * rPanel.getReportTable().getRowHeight()));
            rPanel.getReportTable().setForeground(Color.black);
            rPanel.getReportTable().getTableHeader().setForeground(Color.black);
            f.getContentPane().add(rPanel);
            f.setVisible(true);
            componentToBePrinted = rPanel;
        }
        printJob.setPrintable(this, pageFormat);
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = formatter.format(entryPanel.getDateChooser().getDate());
            String job = "" + entryPanel.getPumpTypeField().getText()+ "_"+ entryPanel.getSlNoField().getText()+"_"+ dateString;
            printJob.setJobName("" + job);
        } catch (Exception ex) {

        }
        if (printJob.printDialog()) {
            try {
                printJob.print();

            } catch (PrinterException pe) {
            }
        }
        f.dispose();

        gPanel.setSize(originalW, originalH);
        gPanel.setBounds(rect);
        //the graph panel has been removed from original OpeningFrame object when attaching to printable Frame.
        //Now we can restore it 
        if (type == ComponentToBePrintedType.GRAPH || type == ComponentToBePrintedType.ECONOMY_REPORT) {
            frame.add(gPanel);

            gPanel.repaint();
        }
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {

        if (pageIndex > 0) {
            return (NO_SUCH_PAGE);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            disableDoubleBuffering(componentToBePrinted);
            componentToBePrinted.paint(g2d);
            enableDoubleBuffering(componentToBePrinted);
            return (PAGE_EXISTS);
        }
    }

    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
}
