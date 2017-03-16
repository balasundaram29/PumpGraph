/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.graph.ComponentToBePrintedType;
import bala.graph.gui.ReadingEntryPanel;
import bala.graph.gui.ReportPanel;
import bala.graph.utilities.ModifiedPrintUtility;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import org.openide.util.ImageUtilities;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;
import org.pump.graph.core.ViewGraphAction.PrintGraphAction;

public final class ViewPumpReportAction implements ActionListener {
 final Action printReportAction = new PrintGraphAction("Print the report", ImageUtilities.loadImageIcon("org/pump/graph/core/print.png", false), "Preview and print the report");
 ReportPanel reportPanel;
   ReadingEntryPanel entryPanel;
    GraphTopComponent gTopComp;
 public void actionPerformed(ActionEvent e) {
        // TODO implement action body
         try {
                Mode m = WindowManager.getDefault().findMode("editor");
            gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
                    //(GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
            //  GraphTopComponent gTopComp= GraphTopComponent.findInstance();
             entryPanel = gTopComp.getEntryPanel();
            reportPanel = new ReportPanel(entryPanel);
            gTopComp.removeAll();
            gTopComp.invalidate();
             JToolBar toolBar = new JToolBar();

            toolBar.add(printReportAction);
             gTopComp.add(toolBar, BorderLayout.NORTH);
            gTopComp.add(new JScrollPane(reportPanel), BorderLayout.CENTER);
            gTopComp.validate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
  class PrintGraphAction extends AbstractAction {

        public PrintGraphAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);

        }

        public void actionPerformed(ActionEvent ae) {

            try {
  gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
            //  GraphTopComponent gTopComp= GraphTopComponent.findInstance();
             entryPanel = gTopComp.getEntryPanel();
               reportPanel = new ReportPanel(entryPanel);
                if (reportPanel != null) {
                    new ModifiedPrintUtility(entryPanel, ComponentToBePrintedType.REPORT).print();                }
                gTopComp.validate();
                gTopComp.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
