package bala.graph.gui;

import bala.graph.graph.*;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import bala.table.tame.GroupableTableHeader;
import bala.table.tame.GroupableHeaderPlusTableUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.miginfocom.swing.MigLayout;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bala
 */
public final class ReportPanel extends JPanel {

    private ReadingEntryPanel entryPanel;
    int reportTableHeight;
    double[] multFactors;
    Font labelFont;
    public static Font PRINTABLE_LABEL_FONT = new Font("Monospaced 12", Font.PLAIN, 8);
    Font DEFAULT_FONT = new Font("Monospaced 12", Font.PLAIN, 12);
    //used for economy print ReportPanel;
    //public ReportPanel(ReadingEntryPanel entryPanel, boolean everythingInAPage) constructor
    private GraphPanel gp;
    private int ENTRY_TABLE_COL_COUNT;// = AppConstants.ENTRY_TABLE_COL_COUNT;
    private int MAX_ROW_COUNT;//= AppConstants.MAX_ROWS_COUNT;
    private int REPORT_TABLE_COL_COUNT;// = AppConstants.REPORT_TABLE_COL_COUNT;
    private JTable reportTable;
    private JComponent header;
    private ArrayList<JLabel> jLabelList = new ArrayList<JLabel>();

    public ReportPanel(ReadingEntryPanel entryPanel) {
        this.entryPanel = entryPanel;
        labelFont = DEFAULT_FONT;
        go();
    }

    void go() {
        ENTRY_TABLE_COL_COUNT = entryPanel.appConstants.ENTRY_TABLE_COL_COUNT;
        MAX_ROW_COUNT = entryPanel.appConstants.MAX_ROWS_COUNT;
        REPORT_TABLE_COL_COUNT = entryPanel.appConstants.REPORT_TABLE_COL_COUNT;
        double[][] given = parseEntryTable();
        System.out.println("Given size rows=" + given.length + " ,cols=" + given[0].length);
        multFactors = calculateMultFactors();
        double[][] data = this.findReportValues(given);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.println("the data at " + i + "," + j + "is" + Double.toString(data[i][j]));
            }
        }
        setLayout(new MigLayout("",
                "[grow][grow][grow][grow]", "[][grow]20[]20[]20[]20[]20[]20[][grow]50[][]"));
        this.addDeclaredValues();
        this.getAndAddTable(data);
        this.addSignature();
        //       reportTable.setPreferredSize(new Dimension(getWidth(), reportTable.getRowCount() * (reportTable.getRowHeight())));

    }

    public ReportPanel(ReadingEntryPanel entryPanel, boolean everythingInAPage) {
        this.entryPanel = entryPanel;
        ENTRY_TABLE_COL_COUNT = entryPanel.appConstants.ENTRY_TABLE_COL_COUNT;
        MAX_ROW_COUNT = entryPanel.appConstants.MAX_ROWS_COUNT;
        REPORT_TABLE_COL_COUNT = entryPanel.appConstants.REPORT_TABLE_COL_COUNT;
        double[][] given = parseEntryTable();
        multFactors = calculateMultFactors();
        double[][] data = this.findReportValues(given);
        setLayout(new MigLayout("insets 0 0 0 0",
                "[grow][grow][grow][grow]", "[][][][][][][][][grow][bottom]40[bottom]"));
        //setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.addDeclaredValues();
        this.getAndAddTable(data);
        //  GraphPanel;
        gp = new GraphPanel(this);
        gp.getGraph().getPlot().setGridLinesType(GridLinesType.NARROW_GRID_SPACING);
        gp.getGraph().getPlot().getDomainAxis().setAxisLineColor(Color.black);
        List l = gp.getGraph().getPlot().getRangeAxesList();
        for (Object r : l) {
            RangeAxis axis = (RangeAxis) r;
            axis.setAxisLinePaint(Color.black);
        }

        this.add(gp, "gapleft 1px,grow,span,wrap");//,gapright 10px");//,height 400:420:440
        gp.setBorder(BorderFactory.createLineBorder(Color.black));
        gp.setPreferredSize(new Dimension(getWidth(), (int) gp.getPreferredSize().getHeight()));
        this.addSignature();
    }
    //   @Override

    public void setFontNow(Font font) {
        if (this.reportTable != null) {
            this.reportTable.setFont(font);
        }
        if (this.reportTable.getTableHeader() != null) {
            this.reportTable.getTableHeader().setFont(font);
        }
        if (this.jLabelList != null) {
            for (JLabel l : jLabelList) {
                l.setFont(font);
            }
        }
    }

    public int findRowCount() {

        JTable entryTable = getEntryPanel().getTable();
        int i = 0;
        for (i = 0; i < MAX_ROW_COUNT; i++) {
            String str = null;
            try {
                str = (entryTable.getValueAt(i, 1)).toString();
                if (str.trim().length() == 0) {
                    break;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }

        }  //System.out.println("Row count is " + i);
        return i;

    }

    public double[][] parseEntryTable() {
        int rows = findRowCount();
        int cols = ENTRY_TABLE_COL_COUNT;
        JTable entryTable = entryPanel.getTable();
        double[][] given = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                given[i][j] = Double.parseDouble((entryTable.getValueAt(i, j)).toString());
                System.out.println("In Parse Entry Table ,given[" + i + "," + j + "]  is   " + given[i][j]);

            }
        }
        return given;
    }

    public double[] calculateMultFactors() {
        double[] multFactors1 = new double[ENTRY_TABLE_COL_COUNT];
        JTable entryTable = entryPanel.getTable();
        multFactors1[0] = 0.0;
        //ENTRY_TABLE_COL_COUNT  is zero-based
        for (int i = 1; i < ENTRY_TABLE_COL_COUNT; i++) {
            multFactors1[i] = Double.parseDouble((entryTable.getValueAt(MAX_ROW_COUNT, i)).toString());
        }
        return multFactors1;
    }

    public double[][] findReportValues(double[][] given) {

        int rows = findRowCount();

        int cols = REPORT_TABLE_COL_COUNT;
        double diaDel = Double.parseDouble(entryPanel.getDelSizeField().getText());
        double diaSuction = 25.0;
        if (entryPanel.appConstants.currentStandard == IndianStandard.IS9079) {
            diaSuction = Double.parseDouble(entryPanel.getSuctionSizeField().getText());
        }
        double pipeConstantDel = 4000.0 * 4000.0 / (Math.PI * Math.PI * diaDel * diaDel * diaDel * diaDel * 2.0 * 9.81);
        double pipeConstantSuction = 0.0;
        if (entryPanel.appConstants.currentStandard == IndianStandard.IS9079) {
            pipeConstantSuction = 4000.0 * 4000.0 / (Math.PI * Math.PI * diaSuction * diaSuction * diaSuction
                    * diaSuction * 2.0 * 9.81);
        }
        double pipeConstant = pipeConstantDel - pipeConstantSuction;

        double data[][] = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            double rater = 50.0 / given[i][entryPanel.appConstants.entryTableConstants.FREQ_COL_INDEX];
            System.out.println("rater is " + rater);
            data[i][entryPanel.appConstants.reportTableConstants.SLNO_COL_INDEX] = i + 1;
            data[i][entryPanel.appConstants.reportTableConstants.FREQ_COL_INDEX] = given[i][entryPanel.appConstants.entryTableConstants.FREQ_COL_INDEX]
                    * multFactors[entryPanel.appConstants.entryTableConstants.FREQ_COL_INDEX];
            if (entryPanel.appConstants.currentStandard == IndianStandard.IS9079) {
                data[i][entryPanel.appConstants.reportTableConstants.SGR_COL_INDEX] = given[i][entryPanel.appConstants.entryTableConstants.SGR_COL_INDEX]
                        * multFactors[entryPanel.appConstants.entryTableConstants.SGR_COL_INDEX] * 0.0135;
            }
            data[i][entryPanel.appConstants.reportTableConstants.DGR_COL_INDEX] = given[i][entryPanel.appConstants.entryTableConstants.DGR_COL_INDEX]
                    * multFactors[entryPanel.appConstants.entryTableConstants.DGR_COL_INDEX];
            double disch = given[i][entryPanel.appConstants.entryTableConstants.DISCH_COL_INDEX] * multFactors[entryPanel.appConstants.entryTableConstants.DISCH_COL_INDEX];
            data[i][entryPanel.appConstants.reportTableConstants.VHC_COL_INDEX] = disch * disch * pipeConstant;
            data[i][entryPanel.appConstants.reportTableConstants.TH_COL_INDEX] = data[i][entryPanel.appConstants.reportTableConstants.DGR_COL_INDEX]
                    + Double.parseDouble((getEntryPanel().getGaugDistField()).getText())
                    + data[i][entryPanel.appConstants.reportTableConstants.VHC_COL_INDEX];
            if (entryPanel.appConstants.currentStandard == IndianStandard.IS9079) {
                data[i][entryPanel.appConstants.reportTableConstants.TH_COL_INDEX] += data[i][entryPanel.appConstants.reportTableConstants.SGR_COL_INDEX];
            }
            data[i][entryPanel.appConstants.reportTableConstants.DISCH_COL_INDEX] = given[i][entryPanel.appConstants.entryTableConstants.DISCH_COL_INDEX]
                    * multFactors[entryPanel.appConstants.entryTableConstants.DISCH_COL_INDEX];
            data[i][entryPanel.appConstants.reportTableConstants.VOL_COL_INDEX] = given[i][entryPanel.appConstants.entryTableConstants.VOL_COL_INDEX]
                    * multFactors[entryPanel.appConstants.entryTableConstants.VOL_COL_INDEX];

            data[i][entryPanel.appConstants.reportTableConstants.CURR_COL_INDEX] = given[i][entryPanel.appConstants.entryTableConstants.CURR_COL_INDEX]
                    * multFactors[entryPanel.appConstants.entryTableConstants.CURR_COL_INDEX];
            data[i][entryPanel.appConstants.reportTableConstants.MINPUT_COL_INDEX] = given[i][entryPanel.appConstants.entryTableConstants.POWER_COL_INDEX]
                    * multFactors[entryPanel.appConstants.entryTableConstants.POWER_COL_INDEX];
            data[i][entryPanel.appConstants.reportTableConstants.RDISCH_COL_INDEX] = rater * data[i][entryPanel.appConstants.reportTableConstants.DISCH_COL_INDEX];
            data[i][entryPanel.appConstants.reportTableConstants.RHEAD_COL_INDEX] = rater * rater * data[i][entryPanel.appConstants.reportTableConstants.TH_COL_INDEX];
            data[i][entryPanel.appConstants.reportTableConstants.RINPUT_COL_INDEX] = rater * rater * rater * data[i][entryPanel.appConstants.reportTableConstants.MINPUT_COL_INDEX];
            data[i][entryPanel.appConstants.reportTableConstants.POP_COL_INDEX] = data[i][entryPanel.appConstants.reportTableConstants.RDISCH_COL_INDEX]
                    * data[i][entryPanel.appConstants.reportTableConstants.RHEAD_COL_INDEX] / 102.00;
            data[i][entryPanel.appConstants.reportTableConstants.EFF_COL_INDEX] = data[i][entryPanel.appConstants.reportTableConstants.POP_COL_INDEX] / data[i][entryPanel.appConstants.reportTableConstants.RINPUT_COL_INDEX] * 100.00;

        }
        return data;
    }

    public void getAndAddTable(double[][] data) {
        try {
            Object[] columnNames;

            if (entryPanel.appConstants.currentStandard == IndianStandard.IS9079) {
                columnNames = new Object[]{
                    "Sl.No", "<html><font color = black>Actual<BR>Frequency,Hz</html>", "<html><font color = black>Suction Gauge<BR>Reading</html> ",
                    "<html><font color = black>Delivery Gauge<BR>Reading", "<html><font color = black>Velocity Head<BR>Correction</html>",
                    "<html><font color = black>Total<BR>Head</html>", "Discharge,lps",
                    "Voltage,V", "Current,A", "<html><font color = black>Motor<BR>Input,kW",
                    "<html><font color = black>Discharge,lps</html>", "<html><font color = black>Total<BR>Head,mWC</html>",
                    "<html><font color = black>Motor<BR>Input,kW</html>", "<html><font color = black>Pump<BR>Output,kW</html>",
                    "<html><font color = black>Overall<BR>Efficiency,%</html>"};
            } else {
                columnNames = new Object[]{
                    "Sl.No", "<html><font color = black>Actual<BR>Frequency,Hz</html>", "<html><font color = black>Delivery Gauge<BR>Reading",
                    "<html><font color = black>Velocity Head<BR>Correction</html>", "<html><font color = black>Total<BR>Head</html>", "Discharge,lps",
                    "Voltage,V", "Current,A", "<html><font color = black>Motor<BR>Input,kW</html>",
                    "<html><font color = black>Discharge,lps</html>", "<html><font color = black>Total<BR>Head,mWC</html>",
                    "<html><font color = black>Motor<BR>Input,kW</html>", "<html><font color = black>Pump<BR>Output,kW</html>",
                    "<html><font color = black>Overall<BR>Efficiency,%</html>"};
            }
            //First Row is for units.So the total number of rows is result of findRowCount + 2;(findrowCounts is zero based);
            //Object[][] dataObj = new Object[this.findRowCount() + 1][15];
            Object[][] dataObj = new Object[this.findRowCount()][15];
            //dataObj[0] = new Object[]{"", "Hz", "m", "m", "m", "m", "lps", "V", "A", "kW", "lps", "m", "kW", "kW", "%"};
            //  for (int i = 1; i < findRowCount() + 1; i++) {
            for (int i = 0; i < findRowCount(); i++) {
                for (int j = 0; j < REPORT_TABLE_COL_COUNT; j++) {
                    //  dataObj[i][j] = String.format("%,.2f", (float) (data[i - 1][j]));
                    dataObj[i][j] = String.format("%,.2f", (float) (data[i][j]));
                    if (j == 0) {
                        // dataObj[i][j] = String.format("%,.0f", (float) (data[i - 1][j]));
                        dataObj[i][j] = "  " + String.format("%,.0f", (float) (data[i][j]));

                    }
                }
            }

            TableModel tm = new DefaultTableModel(dataObj, columnNames) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
//reportTable contains both header and table;the preferred height is desired header size plus table size.
//i.e,125+rowheight*rowcount;
            reportTable = new JTable(tm) {

                protected JTableHeader createDefaultTableHeader() {
                    return new GroupableTableHeader(columnModel);
                }
            };
            bala.table.tame.ColumnGroup group = new bala.table.tame.ColumnGroup("HEAD ,  mWC");
            TableColumnModel columnModel = reportTable.getColumnModel();
            if (entryPanel.appConstants.currentStandard == IndianStandard.IS9079) {
                group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.SGR_COL_INDEX));
            }
            group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.DGR_COL_INDEX));
            group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.VHC_COL_INDEX));
            group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.TH_COL_INDEX));
            ((GroupableTableHeader) reportTable.getTableHeader()).addColumnGroup(group);
            group = new bala.table.tame.ColumnGroup("PERFORMANCE AT RATED FREQUENCY");
            group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.RDISCH_COL_INDEX));
            group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.EFF_COL_INDEX));
            group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.RHEAD_COL_INDEX));
            group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.RINPUT_COL_INDEX));
            group.add(columnModel.getColumn(entryPanel.appConstants.reportTableConstants.POP_COL_INDEX));
            ((GroupableTableHeader) reportTable.getTableHeader()).addColumnGroup(group);
            //SlNo  column needs minimum space only.
            reportTable.getColumnModel().getColumn(entryPanel.appConstants.reportTableConstants.SLNO_COL_INDEX).setPreferredWidth(30);

            reportTable.setUI(new GroupableHeaderPlusTableUI(reportTable));
            reportTable.setFont(DEFAULT_FONT);
            reportTable.setBackground(Color.white);
            reportTable.setRowHeight(27);
            reportTable.setRowMargin(3);
            reportTable.setGridColor(Color.black);
            reportTable.setForeground(Color.blue);
            reportTable.getTableHeader().setForeground(Color.black);
            reportTable.setShowHorizontalLines(false);
            reportTable.setShowVerticalLines(true);
            reportTable.setPreferredSize(new Dimension(getWidth(), 125 + reportTable.getRowHeight() * reportTable.getRowCount()));
            reportTable.setGridColor(Color.black);
            //   ((GroupableHeaderPlusTableUI) reportTable.getUI()).addListener();
            add(reportTable, "grow,span,wrap");

        } catch (Exception ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateExcelReport(GraphPanel gp) {
        try {
            WritableFont times = new WritableFont(WritableFont.TIMES, 11, WritableFont.NO_BOLD);
            WritableCellFormat wcf = new WritableCellFormat(times);

            File in = new File("resources\\xlReport.xls");
            File out = new File("resources\\xlReportFilled.xls");
            Workbook wb = Workbook.getWorkbook(in);
            WritableWorkbook copy = Workbook.createWorkbook(out, wb);
            WritableSheet sheet = copy.getSheet(0);
            Label lbl = new Label(5, 3, ": " + entryPanel.getPumpTypeField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(5, 4, ": " + entryPanel.getSlNoField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(5, 5, ": " + entryPanel.getSuctionSizeField().getText()
                    + " x " + entryPanel.getDelSizeField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(5, 6, ": " + entryPanel.getHeadField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(5, 7, ": " + entryPanel.getDischField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);

            lbl = new Label(10, 3, ": " + entryPanel.gethRangeLwrField().getText()
                    + "/" + entryPanel.gethRangeUprField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);

            lbl = new Label(10, 4, ": " + entryPanel.getEffField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(10, 5, ": " + entryPanel.getRatingField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(10, 6, ": " + entryPanel.getVoltField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(10, 7, ": " + entryPanel.getCurrField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(15, 6, ": " + entryPanel.getGaugDistField().getText());
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);

            double[][] given = parseEntryTable();
            double[][] data = this.findReportValues(given);
            for (int i = 0; i < findRowCount(); i++) {
                int colCount = 15;
                for (int j = 0; j < colCount; j++) {
                    int iNew = i + 16;
                    int jNew = j + 1;
                    //Label lbl;

                    if (j == 0 || j == 7) {
                        lbl = new Label(jNew, iNew, String.format("%,.0f", (float) (data[i][j])));
                        wcf = new WritableCellFormat(times);
                        wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
                        wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
                    } else if (j == 10) {
                        lbl = new Label(jNew, iNew, String.format("%,.2f", (float) (data[i][entryPanel.appConstants.reportTableConstants.RINPUT_COL_INDEX])));
                        wcf = new WritableCellFormat(times);
                        wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
                    } else if (j == 12) {
                        lbl = new Label(jNew, iNew, String.format("%,.2f", (float) (data[i][10])));
                        wcf = new WritableCellFormat(times);
                        wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
                    } else {
                        lbl = new Label(jNew, iNew, String.format("%,.2f", (float) (data[i][j])));
                        wcf = new WritableCellFormat(times);
                        wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
                    }
                    //top horizontal line of table
                    if (i == 0) {
                        wcf.setBorder(Border.TOP, BorderLineStyle.THIN);
                    }

                    //bottom horizontal line of table
                    if (i == (this.findRowCount() - 1)) {
                        wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
                    }
                    lbl.setCellFormat(wcf);
                    sheet.addCell(lbl);
                }
            }
            //   GraphPanel gp = new GraphPanel(new ReportPanel(entryPanel, w, h), w, h - 25);

            ArrayList<Renderer> rendererList = gp.getGraph().getPlot().getRendererList();
            for (Renderer renderer : rendererList) {
                renderer.setCurvePaint(Color.BLACK);
                renderer.setStroke(new BasicStroke(1.5f));
            }
            ArrayList<RangeAxis> rangeAxisList = gp.getGraph().getPlot().getRangeAxesList();
            Font old = null;
            for (RangeAxis rAxis : rangeAxisList) {
                rAxis.setAxisLinePaint(Color.BLACK);
                old = rAxis.getFont();
                rAxis.setFont(new Font(old.getFontName(), Font.BOLD, 12));
            }

            Plot p = gp.getGraph().getPlot();
            p.sethExrColor(Color.BLACK);
            Stroke s = new BasicStroke(1.0f);
            p.sethExrStroke(s);
            p.sethExrColor(Color.BLACK);
            p.seteExrStroke(s);
            p.seteExrColor(Color.BLACK);
            p.setcExrStroke(s);
            p.setcExrColor(Color.BLACK);

            gp.getGraph().getPlot().getDomainAxis().setAxisLineColor(Color.black);
            // Font old = gp.getGraph().getPlot().getDomainAxis().getFont();

            gp.getGraph().getPlot().getDomainAxis().setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
            //gp.getGraph().getPlot().
            gp.getGraph().getPlot().getDomainAxis().setAxisLineColor(Color.black);
            // Font old = gp.getGraph().getPlot().getDomainAxis().getFont();
            //  gp.getGraph().getPlot().getDomainAxis().setFont(new Font(old.getFontName(),Font.BOLD,12));
            //gp.getGraph().getPlot().
            BufferedImage image = new BufferedImage(
                    45 - 8, 145 - 118, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2D = (Graphics2D) image.getGraphics();
            // g2D.scale(1.0, 1);
            gp.paint(g2D);
            File file = null;
            try {
                file = new File("GraphOut.png");
                ImageIO.write(image, "png", file);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            WritableImage wImage = new WritableImage(1, 25, 15, 19, file);
            sheet.addImage(wImage);
            PumpValues obs = gp.getGraph().getPlot().getObsValues();
            PumpValues decl = gp.getDeclaredValues();
            lbl = new Label(1, 46, "Type : " + entryPanel.getPumpTypeField().getText());
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            // wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(8, 46, entryPanel.getSlNoField().getText());
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(1, 47, "Head Range , mWC : " + entryPanel.gethRangeLwrField().getText() + " / " + entryPanel.gethRangeUprField().getText());
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            // wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = formatter.format(entryPanel.getDateChooser().getDate());
            lbl = new Label(1, 48, "Date : " + dateString);
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);

            lbl = new Label(11, 46, String.format("%.2f", decl.getDischarge()));
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);

            sheet.addCell(lbl);
            lbl = new Label(12, 46, String.format("%.2f", decl.getHead()));
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(14, 46, String.format("%.2f", decl.getEfficiency()));
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(15, 46, String.format("%.2f", decl.getMaxCurrent()));
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);

            lbl = new Label(11, 47, String.format("%.2f", obs.getDischarge()));
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(12, 47, String.format("%.2f", obs.getHead()));
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(14, 47, String.format("%.2f", obs.getEfficiency()));
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(15, 47, String.format("%.2f", obs.getMaxCurrent()));
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);

            lbl = new Label(11, 48, obs.getDischResult().toString());
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(12, 48, obs.getHeadResult().toString());
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(14, 48, obs.getEffResult().toString());
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            lbl = new Label(15, 48, obs.getCurrResult().toString());
            wcf = new WritableCellFormat(times);
            wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
            wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            lbl.setCellFormat(wcf);
            sheet.addCell(lbl);
            copy.write();
            copy.close();
            String fileName = "resources\\xlReportFilled.xls";
            String[] commands = {"cmd", "/c", "start", "\"DummyTitle\"", fileName};//
            Runtime.getRuntime().exec(commands);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public PumpValues getDeclaredValues() {
        PumpValues values = new PumpValues();
        values.setsNo(this.entryPanel.getSlNoField().getText().trim());
        values.setType(this.entryPanel.getPumpTypeField().getText());
        values.setDate(this.entryPanel.getDateChooser().getDate());
        values.setDischarge(Double.parseDouble(getEntryPanel().getDischField().getText()));
        values.setHead(Double.parseDouble(getEntryPanel().getHeadField().getText()));
        values.setEfficiency(Double.parseDouble(getEntryPanel().getEffField().getText()));
        values.setMaxCurrent(Double.parseDouble(getEntryPanel().getCurrField().getText()));
        values.setHeadRangeMax(Double.parseDouble(getEntryPanel().gethRangeUprField().getText()));
        values.setHeadRangeMin(Double.parseDouble(getEntryPanel().gethRangeLwrField().getText()));
        return values;
    }

    public MaxValuesForScale getValuesForScale() {

        MaxValuesForScale values = new MaxValuesForScale();
        values.setDischMax(Double.parseDouble(getEntryPanel().getDischMaxForScaleField().getText()));
        values.setHeadMax(Double.parseDouble(getEntryPanel().getHeadMaxForScaleField().getText()));
        values.setCurrMax(Double.parseDouble(getEntryPanel().getCurrMaxForScaleField().getText()));
        values.setEffMax(Double.parseDouble(getEntryPanel().getEffMaxForScaleField().getText()));
        return values;
    }

    public Dataset getDataset(DatasetAndCurveType type) {

        int rows = findRowCount();
        Dataset dataset = null;
        switch (type) {

            case DISCHARGE_VS_CURRENT: {

                double[] currents = new double[rows];
                double[] discharges = new double[rows];
                double[][] data = this.findReportValues(this.parseEntryTable());
                for (int i = 0; i < rows; i++) {
                    currents[i] = data[i][entryPanel.appConstants.reportTableConstants.CURR_COL_INDEX];
                    discharges[i] = data[i][entryPanel.appConstants.reportTableConstants.RDISCH_COL_INDEX];
                }
                dataset = new Dataset(discharges, currents, DatasetAndCurveType.DISCHARGE_VS_CURRENT);
                break;
            }

            case DISCHARGE_VS_EFFICIENCY: {

                double[] efficiencies = new double[rows];
                double[] discharges = new double[rows];
                double[][] data = this.findReportValues(this.parseEntryTable());
                for (int i = 0; i < rows; i++) {
                    efficiencies[i] = data[i][entryPanel.appConstants.reportTableConstants.EFF_COL_INDEX];
                    discharges[i] = data[i][entryPanel.appConstants.reportTableConstants.RDISCH_COL_INDEX];
                }
                dataset = new Dataset(discharges, efficiencies, DatasetAndCurveType.DISCHARGE_VS_EFFICIENCY);
                break;
            }

            case DISCHARGE_VS_HEAD: {

                double[] heads = new double[rows];
                double[] discharges = new double[rows];
                double[][] data = this.findReportValues(this.parseEntryTable());
                for (int i = 0; i < rows; i++) {
                    heads[i] = data[i][entryPanel.appConstants.reportTableConstants.RHEAD_COL_INDEX];
                    discharges[i] = data[i][entryPanel.appConstants.reportTableConstants.RDISCH_COL_INDEX];
                }
                dataset = new Dataset(discharges, heads, DatasetAndCurveType.DISCHARGE_VS_HEAD);
                break;
            }
        }
        return dataset;
    }

    /**
     * @return the entryPanel
     */
    public ReadingEntryPanel getEntryPanel() {
        return entryPanel;
    }

    private void addDeclaredValues() {
        this.setBackground(Color.white);
        Font cFont = new Font("Monospaced 12", Font.BOLD, 12);
        JLabel compLabel = new JLabel(AppConstants.COMPANY_NAME + " , " + AppConstants.SHORT_COMPANY_ADDRESS);
        compLabel.setFont(cFont);
        this.add(compLabel, "grow,wrap,span,gapleft 2px");
        cFont = new Font("Monospaced 12", Font.BOLD, 12);
        compLabel = new JLabel("Test Report of " + entryPanel.appConstants.IS_NAME + ",IS " + entryPanel.appConstants.IS_REF);// 2, 45);
        compLabel.setFont(cFont);
        this.add(compLabel, "grow,wrap,span,gapleft 2px");
        //  labelFont = new Font("Monospaced 12", Font.PLAIN, 8);
        JLabel slNoLabel = new JLabel("Sl. No : ");
        slNoLabel.setFont(labelFont);
        this.add(slNoLabel, "grow,gapleft 2px");
        jLabelList.add(slNoLabel);
        JLabel enteredSlNoLabel = new JLabel(entryPanel.getSlNoField().getText());
        enteredSlNoLabel.setFont(labelFont);
        this.add(enteredSlNoLabel, "grow");
        jLabelList.add(enteredSlNoLabel);

        JLabel ipNoLabel = new JLabel("Inpass No. : ");
        this.add(ipNoLabel, "grow");
        ipNoLabel.setFont(labelFont);
        jLabelList.add(ipNoLabel);
        JLabel enteredIpNoLabel = new JLabel(entryPanel.getIpNoField().getText());
        this.add(enteredIpNoLabel, "grow");
        enteredIpNoLabel.setFont(labelFont);
        jLabelList.add(enteredIpNoLabel);
        JLabel dateLabel = new JLabel("Date : ");
        this.add(dateLabel, "grow");
        jLabelList.add(dateLabel);
        dateLabel.setFont(labelFont);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = formatter.format(entryPanel.getDateChooser().getDate());
        JLabel enteredDateLabel = new JLabel(dateString);
        jLabelList.add(enteredDateLabel);
        this.add(enteredDateLabel, "grow");
        enteredDateLabel.setFont(labelFont);

        JLabel typeLabel = new JLabel("Pump Type : ");
        this.add(typeLabel, "grow");
        typeLabel.setFont(labelFont);
        jLabelList.add(typeLabel);
        JLabel enteredTypeLabel = new JLabel(entryPanel.getPumpTypeField().getText());
        this.add(enteredTypeLabel, "grow,wrap");
        enteredTypeLabel.setFont(labelFont);
        jLabelList.add(enteredTypeLabel);
        JLabel ratingLabel = new JLabel("Rating (kW/HP)     :");
        this.add(ratingLabel, "grow,gapleft 2px");
        jLabelList.add(ratingLabel);

        ratingLabel.setFont(labelFont);

        JLabel enteredRatingLabel = new JLabel(entryPanel.getRatingField().getText());
        this.add(enteredRatingLabel, "grow");
        jLabelList.add(enteredRatingLabel);
        enteredRatingLabel.setFont(labelFont);

        JLabel headLabel = new JLabel("Total Head(m) : ");
        this.add(headLabel, "grow");
        jLabelList.add(headLabel);
        headLabel.setFont(labelFont);
        JLabel enteredHeadLabel = new JLabel(entryPanel.getHeadField().getText());
        this.add(enteredHeadLabel, "grow");
        jLabelList.add(enteredHeadLabel);
        enteredHeadLabel.setFont(labelFont);
        JLabel dischLabel = new JLabel("Discharge (lps) : ");
        this.add(dischLabel, "grow");
        jLabelList.add(dischLabel);

        dischLabel.setFont(labelFont);
        JLabel enteredDischLabel = new JLabel(entryPanel.getDischField().getText());
        this.add(enteredDischLabel, "grow");
        jLabelList.add(enteredDischLabel);
        enteredDischLabel.setFont(labelFont);

        JLabel effLabel = new JLabel("Overall Eff.(%) : ");
        this.add(effLabel, "grow");
        jLabelList.add(effLabel);
        effLabel.setFont(labelFont);
        JLabel enteredEffLabel = new JLabel(entryPanel.getEffField().getText());
        this.add(enteredEffLabel, "grow,wrap");
        jLabelList.add(enteredEffLabel);
        enteredEffLabel.setFont(labelFont);
        JLabel currLabel = new JLabel("Max.Current (A) : ");
        this.add(currLabel, "grow,gapleft 2px");
        jLabelList.add(currLabel);
        currLabel.setFont(labelFont);
        JLabel enteredCurrLabel = new JLabel(entryPanel.getCurrField().getText());
        this.add(enteredCurrLabel, "grow");
        jLabelList.add(enteredCurrLabel);
        enteredCurrLabel.setFont(labelFont);
        JLabel headRangeLabel = new JLabel("Head Range (m) : ");
        this.add(headRangeLabel, "grow");
        jLabelList.add(headRangeLabel);
        headRangeLabel.setFont(labelFont);
        JLabel enteredHeadRangeLabel = new JLabel(entryPanel.gethRangeLwrField().getText() + " / " + entryPanel.gethRangeUprField().getText());
        this.add(enteredHeadRangeLabel, "grow");
        jLabelList.add(enteredHeadRangeLabel);

        enteredHeadRangeLabel.setFont(labelFont);
        JLabel voltLabel = new JLabel("Voltage (V) : ");
        this.add(voltLabel, "grow");
        jLabelList.add(voltLabel);
        voltLabel.setFont(labelFont);
        JLabel enteredVoltLabel = new JLabel(entryPanel.getVoltField().getText());
        this.add(enteredVoltLabel, "grow");
        jLabelList.add(enteredVoltLabel);
        enteredVoltLabel.setFont(labelFont);
        JLabel phaseLabel = new JLabel("Phase  : ");
        this.add(phaseLabel, "grow");
        jLabelList.add(phaseLabel);
        phaseLabel.setFont(labelFont);
        JLabel enteredPhaseLabel = new JLabel(entryPanel.getPhaseField().getText());
        this.add(enteredPhaseLabel, "grow,wrap");
        jLabelList.add(enteredPhaseLabel);
        enteredPhaseLabel.setFont(labelFont);
        JLabel freqLabel = new JLabel("Frequency (Hz) : ");
        this.add(freqLabel, "grow,gapleft 2px");
        jLabelList.add(freqLabel);
        freqLabel.setFont(labelFont);
        JLabel enteredFreqLabel = new JLabel(entryPanel.getFreqField().getText());
        this.add(enteredFreqLabel, "grow");
        jLabelList.add(enteredFreqLabel);
        enteredFreqLabel.setFont(labelFont);
        if (entryPanel.appConstants.currentStandard == IndianStandard.IS9079) {
            JLabel pipeSizeLabel = new JLabel("Pipe Size(mm)  : ");
            add(pipeSizeLabel, "grow");
            jLabelList.add(pipeSizeLabel);
            pipeSizeLabel.setFont(labelFont);
            JLabel enteredPipeSizeLabel = new JLabel(entryPanel.getSuctionSizeField().getText() + " x " + entryPanel.getDelSizeField().getText());
            add(enteredPipeSizeLabel, "grow");
            jLabelList.add(enteredPipeSizeLabel);
            enteredPipeSizeLabel.setFont(labelFont);
        }
        if (entryPanel.appConstants.currentStandard != IndianStandard.IS9079) {
            JLabel pipeSizeLabel = new JLabel("Del Size(mm)  : ");
            add(pipeSizeLabel, "grow");
            jLabelList.add(pipeSizeLabel);
            pipeSizeLabel.setFont(labelFont);
            JLabel enteredPipeSizeLabel = new JLabel(entryPanel.getDelSizeField().getText());
            add(enteredPipeSizeLabel, "grow");
            jLabelList.add(enteredPipeSizeLabel);
            enteredPipeSizeLabel.setFont(labelFont);
        }
        JLabel stagesLabel = new JLabel("No.  of  Stages : ");
        this.add(stagesLabel, "grow");
        jLabelList.add(stagesLabel);
        stagesLabel.setFont(labelFont);
        JLabel enteredStagesLabel = new JLabel(entryPanel.getStagesField().getText());
        this.add(enteredStagesLabel, "grow");
        jLabelList.add(enteredStagesLabel);
        enteredStagesLabel.setFont(labelFont);
        if (entryPanel.appConstants.currentStandard == IndianStandard.IS8034) {
            JLabel boreSizeLabel = new JLabel("Min Bore Size(mm)  : ");
            add(boreSizeLabel, "grow");
            jLabelList.add(boreSizeLabel);
            boreSizeLabel.setFont(labelFont);
            JLabel enteredBoreSizeLabel = new JLabel(entryPanel.getDelSizeField().getText());
            add(enteredBoreSizeLabel, "grow,wrap");
            jLabelList.add(enteredBoreSizeLabel);
            enteredBoreSizeLabel.setFont(labelFont);
        }
        JLabel gDistLabel = new JLabel("Gauge Distance (m) : ");
        this.add(gDistLabel, "grow");
        jLabelList.add(gDistLabel);
        gDistLabel.setFont(labelFont);
        JLabel enteredGDistLabel = new JLabel(entryPanel.getGaugDistField().getText());
        if(entryPanel.appConstants.currentStandard != IndianStandard.IS8034){
        this.add(enteredGDistLabel, "grow,wrap");}else{
             this.add(enteredGDistLabel, "grow");
        }
        jLabelList.add(enteredGDistLabel);
        enteredGDistLabel.setFont(labelFont);
        JLabel remarksLabel = new JLabel("Remarks : ");
        //if (entryPanel.appConstants.currentStandard != IndianStandard.IS8034) {
            this.add(remarksLabel, "grow");
        //} else {
           // this.add(remarksLabel, "grow");
        //}
        jLabelList.add(remarksLabel);
        remarksLabel.setFont(labelFont);
        JLabel enteredRemarksLabel = new JLabel(entryPanel.getRemarksField().getText());
        this.add(enteredRemarksLabel, "grow,wrap");
        jLabelList.add(enteredRemarksLabel);
        enteredRemarksLabel.setFont(labelFont);
    }

    public void addSignature() {
        JLabel compLabel = new JLabel("Casing Pressure Test : Casing withstood 1.5 times the max. discharge pressure for 2 mins.");
        compLabel.setFont(labelFont);
        add(compLabel, "grow,wrap,span,gapleft 2px");
        jLabelList.add(compLabel);
        compLabel = new JLabel("Signature");// 2, 45);
        compLabel.setFont(labelFont);
        add(compLabel, "grow,span,gapleft 2px,gapbottom 0px");
        jLabelList.add(compLabel);
    }

    public static void showErrorMessage() {
        JFrame f = new JFrame();
        f.setBounds(400, 200, 600, 400);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);

        JOptionPane.showMessageDialog(f,
                "Enter only  numbers as values and ensure that no two discharge values are equal.");
        return;
    }

    /**
     * @return the gp
     */
    public GraphPanel getGp() {
        return gp;
    }
//queried in graph

    public String getPipeSize() {
        if (entryPanel.appConstants.currentStandard == IndianStandard.IS9079) {
            return this.entryPanel.getSuctionSizeField().getText() + "mm " + " x "
                    + this.entryPanel.getDelSizeField().getText() + "mm.";
        }
        return this.entryPanel.getDelSizeField().getText() + "mm.";
    }

    /**
     * @return the reportTable
     */
    public JTable getReportTable() {
        return reportTable;
    }

    /**
     * @return the header
     */
    public JComponent getRportTableHeader() {
        return header;
    }

    /**
     * @return the jLabelList
     */
    public ArrayList<JLabel> getjLabelList() {
        return jLabelList;
    }
}

class MyHeaderRenderer extends JLabel implements
        TableCellRenderer, Serializable {

    String text = null;
    int row = 0;
    int column = 0;

    public MyHeaderRenderer(Color gridColorIn) {
        //  this.setBorder(BorderFactory.createLineBorder(gridColorIn, 1));
        this.setFont(new Font("Monospaced 12", Font.PLAIN, 8));
        this.setAlignmentY(70);
    }

    public MyHeaderRenderer(Color gridColorIn, int row, int column, String text) {
        //  this.setBorder(BorderFactory.createLineBorder(gridColorIn, 1));
        this.row = row;
        this.column = column;
        this.setFont(new Font("Monospaced 12", Font.PLAIN, 8));
        this.text = text;
        //this.setAlignmentY(70);
    }

    /*  @Override
     public void paintComponent(Graphics g) {
     //  super.paintComponent(g);
     Graphics2D g2D = (Graphics2D) g;
     //   g2D.drawString("ok"+text,10,10);
     AffineTransform transform = new AffineTransform();
     transform.setToRotation(-Math.PI / 2.0);
     Font derivedFont = g2D.getFont().deriveFont(transform);
     g2D.setFont(derivedFont);
     FontMetrics fm = g2D.getFontMetrics();
     // g2D.rotate(-Math.PI/2.0);
     //g2D.drawOval(25, 25, 10, 10);
     String[] sarray = this.text.split("<BR>");
     float offset = this.getWidth() / 4;//fm.getHeight();//float)(this.getWidth()/3.0);
     for (int i = 0; i < sarray.length; i++) {
     String s = sarray[i].replace("<html><font color = black>", "");
     s = s.replace("</html>", "");
     g2D.drawString(s, offset * (i + 1), this.getHeight() - 2);
     g2D.drawRect(0, 1, getWidth() - 1, getHeight() - 2);
     }
     }*/
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {
        this.text = value.toString();
        this.row = row;
        this.column = column;
        this.setText(value.toString());
        this.setBorder(new CustomBorder(CustomBorder.RIGHT, 1.0f));
        return this;
    }
}

class CustomBorder extends AbstractBorder {

    public static int TOP = 1; //0001
    public static int BOTTOM = 2; //0010
    public static int LEFT = 4; // 0100   
    public static int RIGHT = 8; // 1000
    public static int ALL = 15; // 1111
    private final int borderType;
    private final float strokeSize;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2D = (Graphics2D) g;
        Stroke savedStroke = g2D.getStroke();
        Color savedColor = g2D.getColor();
        g2D.setStroke(new BasicStroke(strokeSize));
        g2D.setColor(Color.black);
        if ((borderType & TOP) == TOP) {
            g2D.drawLine(x, 0, x + width, 0);
        }
        if ((borderType & BOTTOM) == BOTTOM) {
            g2D.drawLine(x, y + height, x + width - 1, y + height);
        }
        if ((borderType & LEFT) == LEFT) {
            g2D.drawLine(x, y, x, y + height);
        }
        if ((borderType & RIGHT) == RIGHT) {
            g2D.drawLine(x + width, y, x + width, y + height);
        }
        g2D.setStroke(savedStroke);
        g2D.setColor(savedColor);
    }

    public CustomBorder(int borderType, float strokeSize) {
        this.borderType = borderType;
        this.strokeSize = strokeSize;
    }
}
