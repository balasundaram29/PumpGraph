/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.gui.ReadingEntryPanel;
import bala.graph.persistence.DirectoryPathPersistence;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

@ActionID(
        category = "File",
        id = "org.pump.graph.core.SavePumpTypeAction"
)
@ActionRegistration(
        iconBase = "org/pump/graph/core/gtk-save.png",
        displayName = "#CTL_SavePumpTypeAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 2575, separatorAfter = 2587),
    @ActionReference(path = "Toolbars/Memory", position = 50)
})
@Messages("CTL_SavePumpTypeAction=Save PumpType Values in a file")
public final class SavePumpTypeAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ObjectOutputStream os = null;
        try {
            // TODO implement action body
            Mode m = WindowManager.getDefault().findMode("editor");
            GraphTopComponent gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            // final GraphTopComponent gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
            //  GraphTopComponent gTopComp= GraphTopComponent.findInstance();
            ReadingEntryPanel entryPanel = gTopComp.getEntryPanel();
            JFileChooser chooser = null;// new JFileChooser();
            if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 9079")) {
                chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("MBTypeFileDir"));
            } else if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 14220")) {
                chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("OWSTypeFileDir"));
            }   if (entryPanel.getIsRefField().getText().equalsIgnoreCase("IS 8034")) {
            chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("BWSTypeFileDir"));
        }   chooser.showSaveDialog(null);
        if (chooser.getSelectedFile() == null) {
            return;
        }   os = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
        os.writeObject(new bala.graph.persistence.v1.TypeValues(entryPanel));
           
            if (AppConstants.standard == IndianStandard.IS9079) {
                DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "MBTypeFileDir");
            } else  if (AppConstants.standard == IndianStandard.IS14220){
                DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "OWSTypeFileDir");
            } if (AppConstants.standard == IndianStandard.IS8034){
               DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "BWSTypeFileDir"); 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
