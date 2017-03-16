/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.gui.ReadingEntryPanel;
import bala.graph.persistence.DirectoryPathPersistence;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

public class SaveAsAction implements ActionListener {

    GraphTopComponent gTopComp;
    private  final static Logger LOGGER=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            //  System.out.println("In my open action");
            Mode m = WindowManager.getDefault().findMode("editor");
            gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            // final GraphTopComponent gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
            //  GraphTopComponent gTopComp= GraphTopComponent.findInstance();
            ReadingEntryPanel entryPanel = gTopComp.getEntryPanel();
            JFileChooser chooser = null;// new JFileChooser();

            if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 9079")) {
                chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("MBPumpDir"));
            } else if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 14220")) {
                chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("OWSPumpDir"));
            } else if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 8034")) {
                chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("BWSPumpDir"));
            }

            //  FileNameExtensionFilter filter = new FileNameExtensionFilter("Pump Test Files","ptf");
            //chooser.setFileFilter(filter);
            int day = entryPanel.getDateChooser().getDate().getDate();
            String dayString;
            if (day < 10) {
                dayString = "" + "0" + day;
            } else {
                dayString = "" + day;
            }
            int month = entryPanel.getDateChooser().getDate().getMonth();
            String monthString;
            if (month + 1 < 10) {
                monthString = "" + "0" + (month + 1);
            } else {
                monthString = "" + (month + 1);
            }

            chooser.setSelectedFile(new File(gTopComp.getEntryPanel().getPumpTypeField().getText().replace("/","_") + "_"
                    + gTopComp.getEntryPanel().getSlNoField().getText() + "_"
                    + dayString + "_"
                    + monthString + "_"
                    + (gTopComp.getEntryPanel().getDateChooser().getDate().getYear() + 1900)
                    + ".evf"));
            chooser.showSaveDialog(null);
            if (chooser.getSelectedFile() == null) {
                return;
            }

            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));

            os.writeObject(new bala.graph.persistence.v1.EntryValues(entryPanel));
            LOGGER.setLevel(Level.INFO);
            LOGGER.info("Saving File "+chooser.getSelectedFile().getCanonicalPath());
            File openFile = chooser.getSelectedFile();
            if (AppConstants.standard == IndianStandard.IS9079) {
                DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "MBPumpDir");
            } else if (AppConstants.standard == IndianStandard.IS14220) {
                DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "OWSPumpDir");
            }
            if (AppConstants.standard == IndianStandard.IS8034) {
                DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "BWSPumpDir");
            }
                gTopComp.savePending = false;
                gTopComp.setOpenFile(chooser.getSelectedFile());
                TabExplorerTopComponent ex = TabExplorerTopComponent.findInstance();
                ex.refreshDisplay();//resultCha
                //OpeningFrame.this.saveMarker = "";
                //OpeningFrame.this.setTitle(AppConstants.SOFTWARE_TITLE + "  :  " + openFile.getName());

            // OpeningFrame.this.setTitle(AppConstants.SOFTWARE_TITLE + "  :  " + openFile.getName());
                //OpeningFrame.this.saveFileDirectory(chooser.getCurrentDirectory());
            
        } catch (Exception ex) {
             LOGGER.log(Level.SEVERE, "In SaveAsAction", ex);
            //x.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot save . Please check filename");
        }
    }
}
