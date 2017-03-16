/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bala.graph.graph;

import bala.graph.gui.ReadingEntryPanel;
import bala.graph.persistence.DirectoryPathPersistence;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.openide.util.Lookup;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;
import org.pump.graph.core.DynamicLookup;
import org.pump.graph.core.GraphTopComponent;
import org.pump.graph.core.TabExplorerTopComponent;

public final class OpenAction implements ActionListener {
 private  final static Logger LOGGER=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        //System.out.println("In my open action");
       try {
        Mode m = WindowManager.getDefault().findMode("editor");
        GraphTopComponent gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
        //  GraphTopComponent gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
        //  GraphTopComponent gTopComp= GraphTopComponent.findInstance();
        ReadingEntryPanel entryPanel = gTopComp.getEntryPanel();
        JFileChooser chooser = null;
        
            if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 9079")) {
                chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("MBPumpDir"));
            } else if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 14220")) {
                chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("OWSPumpDir"));
            }else if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 8034")) {
                chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("BWSPumpDir"));
            }

            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() == null) {
                return;
            }

           // bala.graph.persistence.EntryValuesInputStream is = new bala.graph.persistence.EntryValuesInputStream(new FileInputStream(chooser.getSelectedFile()));
             ObjectInputStream is = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
             LOGGER.setLevel(Level.INFO);
             LOGGER.info("Opening file  "+chooser.getSelectedFile().getCanonicalPath());
            bala.graph.persistence.v1.EntryValues ev = (bala.graph.persistence.v1.EntryValues) is.readObject();

            if (ev.getIsRefFieldString().equalsIgnoreCase("IS 9079")) {
                System.out.println("stanarsd=" + "is 9079");
                entryPanel = new ReadingEntryPanel(new AppConstants(IndianStandard.IS9079));
                // AppConstants.setStandard(IndianStandard.IS9079);
               // chooser.setCurrentDirectory(chooser.getSelectedFile());
                DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "MBPumpDir");
            }
            if (ev.getIsRefFieldString().equalsIgnoreCase("IS 14220")) {
                entryPanel = new ReadingEntryPanel(new AppConstants(IndianStandard.IS14220));
                //  AppConstants.setStandard(IndianStandard.IS14220);
                   // chooser.setCurrentDirectory(chooser.getSelectedFile());
                DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "OWSPumpDir");
            }
 if (ev.getIsRefFieldString().equalsIgnoreCase("IS 8034")) {
                entryPanel = new ReadingEntryPanel(new AppConstants(IndianStandard.IS8034));
                //  AppConstants.setStandard(IndianStandard.IS14220);
                   // chooser.setCurrentDirectory(chooser.getSelectedFile());
                DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "BWSPumpDir");
            }
            gTopComp.setOpenFile(chooser.getSelectedFile());
            gTopComp.removeAll();
            gTopComp.invalidate();
            // gTopComp.add(toolBar, BorderLayout.NORTH);
            gTopComp.setEntryPanel(entryPanel);
            gTopComp.add(entryPanel, BorderLayout.CENTER);
            gTopComp.validate();
            entryPanel.useSavedValues(ev);
            TabExplorerTopComponent ex = TabExplorerTopComponent.findInstance();
            ex.refreshDisplay();//resultChanged(null);

//because the values have been enterd by usesavedValues() method, savePending has been changed
            //we want it set only for user changes(typing).Reset it .

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
