package bala.graph.graph;

import bala.graph.gui.*;
import bala.graph.settings.current.AppConstants;
import bala.table.GroupableColumnsTable;
import bala.table.MinGridTableUI;
import bala.table.tame.GroupableHeaderPlusTableUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import net.miginfocom.swing.MigLayout;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.pump.graph.core.GraphTopComponent;
//import org.pump.graph.core.OutputTopComponent;

/**
 * Write a description of class GraphPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public final class GraphPanel extends JPanel implements GraphDrawListener {

    private GraphDrawingPanel drawingPanel;
    private Graph graph;
    private ReportPanel reportPanel;
    private int w;
    private int h;
    private DefaultTableModel model;
    private JTable jtable;
    //table conatining the jtable and its header
    private GroupableColumnsTable table;
    // private MergableColumnsTable panel;
    Plot myPlot;
    private JPanel tableScrollerPanel;
    private PumpValues declaredValues;
    public static int COMP_COL = 0;
    public static int TYPE_COL = 1;
    public static int DUTY_POINT_COL = 2;
    public static int DISCH_COL = 3;
    public static int TOTAL_HEAD_COL = 4;
    public static int EFF_COL = 5;
    public static int IMAX_COL = 6;

    /**
     * Constructor for objects of class GraphPanel
     */
    public GraphPanel(ReportPanel reportPanel) {
        // this.w = w;
        //this.h = h-35;
        this.reportPanel = reportPanel;
        makeDrawingPanel();
        makeTableScrollerPanel();
        setLayout(new MigLayout("", "[grow,right][]", "[grow][]"));
        setBackground(Color.white);
        add(drawingPanel, "grow,span,wrap");
        add(jtable, "grow,span,width : :494,height : :90,gapright 0px,gapbottom 5px");
    }

    //to change the size of the already existing  GraphPanel following constructor is used
    public GraphPanel(GraphPanel gp) {
        this.reportPanel = gp.reportPanel;
        makeDrawingPanel();
        makeTableScrollerPanel();
        setBackground(Color.white);
        add(drawingPanel);
        add(jtable);
    }

    void makeDrawingPanel() {
        Dataset headDataset = reportPanel.getDataset(DatasetAndCurveType.DISCHARGE_VS_HEAD);
        Dataset currDataset = reportPanel.getDataset(DatasetAndCurveType.DISCHARGE_VS_CURRENT);
        Dataset effDataset = reportPanel.getDataset(DatasetAndCurveType.DISCHARGE_VS_EFFICIENCY);
        Graph myGraph = new Graph("Graph Software", "Discharge , lps", "Total Head , m", headDataset);
        graph = myGraph;
        myPlot = myGraph.getPlot();
        myPlot.addGraphDrawListener(this);
        DomainAxis xAxis = myPlot.getDomainAxis();
        xAxis.setDataset(headDataset);
        xAxis.setAxisLineColor(Color.blue);
        xAxis.setMaxAxisValue(reportPanel.getValuesForScale().getDischMax());
        RangeAxis theFirst = myPlot.getRangeAxis(0);
        theFirst.setAxisLinePaint(Color.magenta);
        theFirst.setDataset(headDataset);
        theFirst.setMaxAxisValue(reportPanel.getValuesForScale().getHeadMax());
        LoessSmoothRenderer renderer1 = new LoessSmoothRenderer();
        myPlot.setRenderer(0, renderer1);
        renderer1.setDataset(headDataset);
        RangeAxis axis2 = new RangeAxis("Overall Efficiency , %", AxisPosition.LEFT);
        axis2.setAxisLinePaint(Color.red);
        axis2.setDataset(effDataset);
        axis2.setMaxAxisValue(reportPanel.getValuesForScale().getEffMax());

        xAxis.setDataset(effDataset);
        RangeAxis axis3 = new RangeAxis("Current , A", AxisPosition.LEFT);
        axis3.setAxisLinePaint(Color.blue);
        axis3.setDataset(currDataset);
        axis3.setMaxAxisValue(reportPanel.getValuesForScale().getCurrMax());
        xAxis.setDataset(currDataset);

        myPlot.setRangeAxis(1, axis2);
        myPlot.setRangeAxis(2, axis3);

        LoessSmoothRenderer renderer2 = new LoessSmoothRenderer();
        renderer2.setDataset(effDataset);

        myPlot.setRenderer(1, renderer2);

        LoessSmoothRenderer renderer3 = new LoessSmoothRenderer();
        renderer3.setDataset(currDataset);
        myPlot.setRenderer(2, renderer3);
        declaredValues = this.reportPanel.getDeclaredValues();
        myPlot.setDeclaredValues(declaredValues);
        //setLayout(null);
        drawingPanel = new GraphDrawingPanel(myGraph);
        setCurveAndAxisPaintTheSame();

    }

    private void makeTableScrollerPanel() {
        model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn(" " + AppConstants.COMPANY_NAME);
        model.addColumn("Type : " + reportPanel.getEntryPanel().getPumpTypeField().getText());
        model.addColumn("Duty Point");
        model.addColumn("Q(lps)");
        model.addColumn("TH(mWC)");
        model.addColumn("OAE(%)");
        model.addColumn("I-Max(Amps)");
        ReadingEntryPanel entryPanel = reportPanel.getEntryPanel();
        Object[] rowData1 = {" IS " + entryPanel.appConstants.IS_REF, "S.No : " + entryPanel.getSlNoField().getText(), "Guaranteed",String.format("%,.2f", declaredValues.getDischarge()),
            String.format("%,.2f",declaredValues.getHead()), String.format("%,.2f",declaredValues.getEfficiency()),String.format("%,.2f",declaredValues.getMaxCurrent())};

        Object[] rowData2 = {" Head Range : " + declaredValues.getHeadRangeMin() + "/" + declaredValues.getHeadRangeMax() + "  (m)",
            "Size :" + reportPanel.getPipeSize(), "Actual", " ", " ", " ", ""};
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = formatter.format(entryPanel.getDateChooser().getDate());
        Object[] rowData3 = {" Date : " + dateString, "Frequency : 50 Hz", "Result", " ", "", " ", " "};
        model.addRow(rowData1);
        model.addRow(rowData2);
        model.addRow(rowData3);
        
        jtable = new ZoomableJTable(model);
        jtable.setUI(new GroupableHeaderPlusTableUI(jtable));
        jtable.getTableHeader().setFont(new Font("Monospaced 12", Font.PLAIN, 8));
        jtable.setFont(new Font("Monospaced 12", Font.PLAIN, 8));
        jtable.getTableHeader().setPreferredSize(new Dimension(jtable.getColumnModel().getTotalColumnWidth(), 20));
        jtable.getTableHeader().setBackground(Color.WHITE);

        jtable.setRowHeight(17);
        jtable.setRowMargin(3);
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        Font font = new Font("Monospaced 12", Font.PLAIN, 8);
//        table.setFont(font);
        FontMetrics fm = jtable.getFontMetrics(font);
        TableColumn col = jtable.getColumnModel().getColumn(COMP_COL);
        col.setPreferredWidth(300);
        jtable.getColumnModel().getColumn(DUTY_POINT_COL).setMinWidth(fm.stringWidth(" Guaranteed "));
        jtable.getColumnModel().getColumn(TYPE_COL).setPreferredWidth(200);
        jtable.getColumnModel().getColumn(IMAX_COL).setMinWidth(fm.stringWidth("I-Max(Amps)"));
        jtable.setGridColor(Color.black);
        jtable.setPreferredSize(new Dimension(getWidth(), 20 + jtable.getRowCount() * jtable.getRowHeight()));
    }

    private void setTableCellAlignment(int alignment, JTable table) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(alignment);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.setDefaultRenderer(table.getColumnClass(i), renderer);

        }
        table.updateUI();
    }

    public void graphDrawn(GraphDrawEvent ev) {

        PumpValues obsValues = myPlot.getObsValues();
        String s = String.format("%,.2f", obsValues.getDischarge());
        jtable.setValueAt(s, 1, 3);
        s = String.format("%,.2f", obsValues.getHead());
        jtable.setValueAt(s, 1, 4);
        s = String.format("%,.2f", obsValues.getEfficiency());
        jtable.setValueAt(s, 1, 5);
        s = String.format("%,.2f", obsValues.getMaxCurrent());
        jtable.setValueAt(s, 1, 6);
        jtable.setValueAt(obsValues.getDischResult().toString(), 2, 3);
        jtable.setValueAt(obsValues.getHeadResult().toString(), 2, 4);
        jtable.setValueAt(obsValues.getEffResult().toString(), 2, 5);
        jtable.setValueAt(obsValues.getCurrResult().toString(), 2, 6);

        jtable.doLayout();
        jtable.validate();
        jtable.repaint();
    }

    public void setCurveAndAxisPaintTheSame() {
        for (Renderer renderer : myPlot.getRendererList()) {
            renderer.setCurvePaint(renderer.getDataset().getRangeAxis().getAxisLinePaint());
        }
    }

    /**
     * @return the graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @param graph the graph to set
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * @return the w
     */
    public int getW() {
        return w;
    }

    /**
     * @param w the w to set
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * @return the h
     */
    public int getH() {
        return h;
    }

    /**
     * @param h the h to set
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * @return the declaredValues
     */
    public PumpValues getDeclaredValues() {
        return declaredValues;
    }

    /**
     * @return the reportPanel
     */
    public ReportPanel getReportPanel() {
        return reportPanel;
    }

    class ZoomableJTable extends JTable implements MouseListener, MouseMotionListener {

        JFrame zTableFrame = null;

        public ZoomableJTable(TableModel tm) {
            super(tm);
            addMouseListener(this);
            addMouseMotionListener(this);

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                TableModel tm = jtable.getModel();
                JTable zTable = new JTable(tm);
                zTable.setUI(new GroupableHeaderPlusTableUI(zTable));
                Font f1 = new Font("Century Schoolbook", Font.PLAIN, 15);
                zTable.setFont(f1);
                FontMetrics fm = zTable.getFontMetrics(f1);
                zTable.getColumnModel().getColumn(COMP_COL).setMinWidth(fm.stringWidth(AppConstants.COMPANY_NAME) + 5);
                zTable.getColumnModel().getColumn(DUTY_POINT_COL).setMinWidth(fm.stringWidth(" Guaranteed "));
                zTable.getColumnModel().getColumn(TYPE_COL).setPreferredWidth(200);
                zTable.getColumnModel().getColumn(IMAX_COL).setMinWidth(fm.stringWidth("I-Max(Amps)"));
                zTable.setRowHeight(35);
               // zTable.setPreferredSize(new Dimension(GraphPanel.this.getWidth(),
                       // zTable.getRowHeight() * (zTable.getRowCount())));
              /*  zTableFrame = new JFrame();
                zTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                zTableFrame.setAlwaysOnTop(true);
                zTableFrame.add(zTable, BorderLayout.CENTER);
                zTableFrame.pack();
                zTableFrame.setVisible(true);
                zTableFrame.setResizable(false);
                zTableFrame.setLocationRelativeTo(jtable);*/
                Mode m = WindowManager.getDefault().findMode("output");
                //   GraphTopComponent     gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
                TopComponent outTopComp = new TopComponent();
                outTopComp.setLayout(new BorderLayout());
                 // outTopComp.setSize(zTable.getPreferredSize());
                
                if(m!=null){
                    m.dockInto(outTopComp);
                }
               outTopComp.setDisplayName("Result-"+zTable.getValueAt(0,1));//sno
               outTopComp.openAtTabPosition(0);
               outTopComp.requestActive();
               outTopComp.add(zTable,BorderLayout.CENTER);
             
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}
