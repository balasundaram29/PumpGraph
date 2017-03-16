/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.gui.ReadingEntryPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

public final class SaveAction implements ActionListener {

    GraphTopComponent gTopComp;

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        try {
            System.out.println("In my open action");
         //  new bala.graph.persistence.EntryValuesConverter().convert();
            
            Mode m = WindowManager.getDefault().findMode("editor");
            gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
            // final GraphTopComponent gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
            //  GraphTopComponent gTopComp= GraphTopComponent.findInstance();
            ReadingEntryPanel entryPanel = gTopComp.getEntryPanel();
            JFileChooser chooser = null;
            File openFile = gTopComp.getOpenFile();
            if (openFile == null) {
                chooser = new JFileChooser();

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

                chooser.setSelectedFile(new File(gTopComp.getEntryPanel().getPumpTypeField().getText() + "_"
                        + gTopComp.getEntryPanel().getSlNoField().getText() + "_"
                        + dayString + "_"
                        + monthString + "_"
                        + (gTopComp.getEntryPanel().getDateChooser().getDate().getYear() + 1900)
                        + ".evf"));
                chooser.showSaveDialog(null);
                if (chooser.getSelectedFile() == null) {
                    return;
                }
                openFile = chooser.getSelectedFile();
            }
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(openFile));

            os.writeObject(new bala.graph.persistence.v1.EntryValues(entryPanel));

            gTopComp.savePending = false;
            gTopComp.setOpenFile(openFile);
            TabExplorerTopComponent ex = TabExplorerTopComponent.findInstance();
            ex.refreshDisplay();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot save . Please check filename");
        }
    }
}
