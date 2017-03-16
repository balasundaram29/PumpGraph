/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.graph.GraphPanel;
import bala.graph.graph.LoessSmoothRenderer;
import bala.graph.graph.Renderer;
import bala.graph.gui.ReadingEntryPanel;
import bala.graph.gui.ReportPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

public final class ViewEntryPanelAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        try {
            System.out.println("In my open action");
            Mode m = WindowManager.getDefault().findMode("editor");
            final GraphTopComponent gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            // final GraphTopComponent gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
            //  GraphTopComponent gTopComp= GraphTopComponent.findInstance();
            ReadingEntryPanel entryPanel = gTopComp.getEntryPanel();
            gTopComp.removeAll();
            gTopComp.invalidate();
            //  gTopComp.add(toolBar, BorderLayout.NORTH);
            gTopComp.add(entryPanel, BorderLayout.CENTER);
            gTopComp.validate();
            gTopComp.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
