/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.graph.ComponentToBePrintedType;
import bala.graph.graph.GraphPanel;
import bala.graph.graph.GridLinesType;
import bala.graph.graph.LoessSmoothRenderer;
import bala.graph.graph.PumpValues;
import bala.graph.graph.Renderer;
import bala.graph.gui.ReadingEntryPanel;
import bala.graph.gui.ReportPanel;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import bala.graph.utilities.ModifiedPrintUtility;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Collections;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.util.ImageUtilities;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

public final class ViewGraphAction implements ActionListener {

    GraphPanel graphPanel;
    GraphTopComponent gTopComp;
    private JSlider smooth;
    private JSlider rough;
    final Action printGraphAction = new PrintGraphAction("Print the graph", ImageUtilities.loadImageIcon("org/pump/graph/core/print24.png", false), "Preview and print the graph");
    final Action saveObsValuesAction = new SaveObsValuesAction("Save the  observed values", ImageUtilities.loadImageIcon("org/pump/graph/core/gtk-save24.png", false), "Save the observed values to db");
    final Action noGridAction = new NoGridAction("No Grid Needed", ImageUtilities.loadImageIcon("org/pump/graph/core/NoGrid24.gif", false), "Remove the  Grid ");
    final Action wideGridAction = new WideGridAction("Wide GridSpacing", ImageUtilities.loadImageIcon("org/pump/graph/core/WideGrid24.gif", false), "Change Grid Spacing to Wide");
    // final Action medGridAction = new MedGridAction("Medium GridSpacing", new ImageIcon("resources//MedGrid24.gif"), "Change Grid Spacing to Medium");
    final Action narrowGridAction = new NarrowGridAction("Narrow GridSpacing", ImageUtilities.loadImageIcon("org/pump/graph/core/NarrowGrid24.gif", false), "Change Grid Spacing to Narrow");
    JToolBar toolBar;

    public void actionPerformed(ActionEvent e) {

        try {
            System.out.println("In my open action");
            Mode m = WindowManager.getDefault().findMode("editor");
            gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            // final GraphTopComponent gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
            //  GraphTopComponent gTopComp= GraphTopComponent.findInstance();
            ReadingEntryPanel entryPanel = gTopComp.getEntryPanel();
            graphPanel = new GraphPanel(new ReportPanel(entryPanel));
            gTopComp.setGraphPanel(graphPanel);
            smooth = new JSlider(0, 100, 0);
            smooth.setPreferredSize(new Dimension(150, 25));
            smooth.setMaximumSize(new Dimension(150, 25));
            rough = new JSlider(0, 100, 0);
            rough.setPreferredSize(new Dimension(150, 25));
            rough.setMaximumSize(new Dimension(150, 25));
            toolBar = new JToolBar();
            gTopComp.setSmooth(smooth);
            gTopComp.setRough(rough);
            toolBar.add(smooth);
            toolBar.add(rough);
            toolBar.add(noGridAction);
            toolBar.add(narrowGridAction);
            toolBar.add(wideGridAction);
            toolBar.add(printGraphAction);
            toolBar.add(saveObsValuesAction);

            smooth.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent ce) {
                    try {
                        Mode m = WindowManager.getDefault().findMode("editor");
                        gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
                        if (gTopComp.getGraphPanel() != null) {
                            for (Renderer renderer : gTopComp.getGraphPanel().getGraph().getPlot().getRendererList()) {
                                ((LoessSmoothRenderer) renderer).getLoessFunction().setBandwidth(gTopComp.getSmooth().getValue());
                                //  ((LoessSmoothRenderer) renderer).getLoessFunction().setBandwidthByBandwidth(getSmooth().getValue()*100.0);
                            }
                            //f.dispose();
                            gTopComp.setVisible(true);
                            gTopComp.validate();
                            gTopComp.repaint();

                        }
                    } catch (Exception e) {
                    }
                }
            });
            rough.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent ce) {
                    try {
                        Mode m = WindowManager.getDefault().findMode("editor");
                        gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
                        if (gTopComp.getGraphPanel() != null) {
                            for (Renderer renderer : gTopComp.getGraphPanel().getGraph().getPlot().getRendererList()) {
                                ((LoessSmoothRenderer) renderer).getLoessFunction().setDesiredLinearityFactor(gTopComp.getRough().getValue());

                            }
                            //f.dispose();
                            gTopComp.setVisible(true);
                            gTopComp.validate();
                            gTopComp.repaint();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            gTopComp.removeAll();
            gTopComp.invalidate();
            gTopComp.add(toolBar, BorderLayout.NORTH);
            gTopComp.add(graphPanel, BorderLayout.CENTER);
            gTopComp.getInstanceContent().set(Collections.singleton(graphPanel), null);
            gTopComp.validate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the smooth
     */
    public JSlider getSmooth() {
        return smooth;
    }

    /**
     * @return the rough
     */
    public JSlider getRough() {
        return rough;
    }

    class NarrowGridAction extends AbstractAction {

        public NarrowGridAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            putValue(AbstractAction.SHORT_DESCRIPTION, desc);
        }

        public void actionPerformed(ActionEvent ae) {
            Mode m = WindowManager.getDefault().findMode("editor");
            GraphTopComponent gTopComp1 = (GraphTopComponent) m.getSelectedTopComponent();
            if (gTopComp1.getGraphPanel() != null) {
                gTopComp1.getGraphPanel().getGraph().getPlot().setGridLinesType(GridLinesType.NARROW_GRID_SPACING);
            }
            // setVisible(true);
            gTopComp1.validate();
            gTopComp1.repaint();
        }
    }

    class WideGridAction extends AbstractAction {

        public WideGridAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            putValue(AbstractAction.SHORT_DESCRIPTION, desc);
        }

        public void actionPerformed(ActionEvent ae) {
            Mode m = WindowManager.getDefault().findMode("editor");
            gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            if (gTopComp.getGraphPanel() != null) {
                gTopComp.getGraphPanel().getGraph().getPlot().setGridLinesType(GridLinesType.WIDE_GRID_SPACING);
            }

            //    setVisible(true);
            gTopComp.validate();
            gTopComp.repaint();
        }
    }

    class NoGridAction extends AbstractAction {

        public NoGridAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            putValue(AbstractAction.SHORT_DESCRIPTION, desc);
        }

        public void actionPerformed(ActionEvent ae) {
            Mode m = WindowManager.getDefault().findMode("editor");
            gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            if (gTopComp.getGraphPanel() != null) {
                gTopComp.getGraphPanel().getGraph().getPlot().setGridLinesType(GridLinesType.NO_GRID_NEEDED);
            }
            //setVisible(true);
            gTopComp.validate();
            gTopComp.repaint();
        }
    }

    class PrintGraphAction extends AbstractAction {

        public PrintGraphAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);

        }

        public void actionPerformed(ActionEvent ae) {
            Mode m = WindowManager.getDefault().findMode("editor");
            gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            try {

                if (gTopComp.getGraphPanel() != null) {
                    new ModifiedPrintUtility(gTopComp.getGraphPanel(), ComponentToBePrintedType.GRAPH).print();
                }

               
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    class SaveObsValuesAction extends AbstractAction {

        public SaveObsValuesAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);

        }

        public float round(float d, int decimalPlace) {
            BigDecimal bd = new BigDecimal(Float.toString(d));
            bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
            return bd.floatValue();
        }

        @SuppressWarnings("CallToThreadDumpStack")
        public void actionPerformed(ActionEvent ae) {
           Mode m = WindowManager.getDefault().findMode("editor");
            gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            if (gTopComp.getGraphPanel() == null) {
                return;
            }
            try {
                PumpValues values = gTopComp.getGraphPanel().getGraph().getPlot().getObsValues();
                int sno = Integer.parseInt(values.getsNo());
                java.util.Date uDate = values.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String sqlDateString = sdf.format(uDate);
                java.sql.Date sqlDate = java.sql.Date.valueOf(sqlDateString);
                String type = values.getType();
                double rdisch = values.getDischarge();
                double rhead = values.getHead();
                double oaeff = values.getEfficiency();
                double mcurr = values.getMaxCurrent();

                Class.forName("com.mysql.jdbc.Driver");
                // Setup the connection with the DB
                Connection connect = null;
                if (AppConstants.standard == IndianStandard.IS9079) {
                    connect = DriverManager.getConnection("jdbc:mysql://localhost/db_for_bis_mb", "root", "bala");
                } else if (AppConstants.standard == IndianStandard.IS14220) {
                    connect = DriverManager.getConnection("jdbc:mysql://localhost/db_for_bis_sub", "root", "bala");
                }else if (AppConstants.standard == IndianStandard.IS8034) {
                    connect = DriverManager.getConnection("jdbc:mysql://localhost/db_for_bis_bws", "root", "bala");
                }

                java.sql.PreparedStatement statement = connect.prepareStatement(""
                        + "INSERT INTO `observed_values`"
                        + "(`sno`,`date`,`at_type`,`rdisch`,`rhead`,`oaeff`,`maxcurrent`)"
                        + "VALUES(?,?,?,?,?,?,?)");
                statement.setInt(1, sno);
                statement.setDate(2, sqlDate);
                statement.setString(3, type);
                statement.setFloat(4, round((float) rdisch, 2));
                statement.setFloat(5, round((float) rhead, 2));
                statement.setFloat(6, round((float) oaeff, 2));
                statement.setFloat(7, round((float) mcurr, 2));
                statement.executeUpdate();

            } catch (Exception ex) {

                ex.printStackTrace();

            }


        }
    }
}
