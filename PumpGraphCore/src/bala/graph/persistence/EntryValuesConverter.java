/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bala.graph.persistence;

import bala.graph.gui.ReadingEntryPanel;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author bala
 */
public class EntryValuesConverter {

    public  void convert() throws Exception {
        // AppConstants.setStandard(IndianStandard.IS14220);
        JFileChooser chooser = new JFileChooser();
        System.out.println("Entry Vaules Convertor : Opening the file dialog");
        int choosen = chooser.showOpenDialog(null);
        if (choosen == JFileChooser.APPROVE_OPTION) {
            System.out.println(chooser.getCurrentDirectory().toString() + "\\" + chooser.getSelectedFile().getName());
        }
        File[] filesInDirectory = chooser.getCurrentDirectory().listFiles();
        //  ReadingEntryPanel entryPanel = new ReadingEntryPanel();
        int num = 0;
        // boolean done=false;
        for (File file : filesInDirectory) {
            /* System.out.println("The parnet is " + file.getParent());
             System.out.println("The cananical path is " + file.getCanonicalPath());
             System.out.println("The absolute path is " + file.getAbsolutePath());
             System.out.println("The filename is  " + file.getName());
             if (true) {
             return;
             }*/
            if (file.getName().startsWith("ae") | file.getName().startsWith("AE")) {
                new AppConstants(IndianStandard.IS9079);
                // bala.graph.persistence.mb.EntryValuesInputStream is = null;
                ReadingEntryPanel entryPanel = new ReadingEntryPanel(new AppConstants(IndianStandard.IS9079));
                try {

                    System.out.println("Writing file : " + num++);
                    //is = new bala.graph.persistence.mb.EntryValuesInputStream(new FileInputStream(file));
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                    //bala.graph.persistence.mb.EntryValuesInputStream;
                    bala.graph.persistence.EntryValues ev = (bala.graph.persistence.EntryValues) is.readObject();
                   // entryPanel.useSavedValues(ev);
                    bala.graph.persistence.v1.EntryValues evNew = new bala.graph.persistence.v1.EntryValues(entryPanel);
                    File fileOut = new File(file.getParent() + "\\new\\" + file.getName());//.replace("ptf", "evf").replace("mtf","evf"));
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileOut));
                    os.writeObject(evNew);
                    os.close();
                    is.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // done=false;
                    //  Logger.getLogger(EntryValuesConverter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        for (File file : filesInDirectory) {
            System.out.println("The cananical path is " + file.getCanonicalPath());
            System.out.println("The absolute path is " + file.getAbsolutePath());
            System.out.println("The filename is  " + file.getName());
            /*if (true) {
             return;
             }*/
            if (file.getName().startsWith("at") | file.getName().startsWith("AT")) {
                 new AppConstants(IndianStandard.IS14220);
                //bala.graph.persistence.owsub.EntryValuesInputStream is = null;
                ReadingEntryPanel entryPanel = new ReadingEntryPanel(new AppConstants(IndianStandard.IS14220));
                try {

                    System.out.println("Writing file : " + num++);
                    //is = new bala.graph.persistence.owsub.EntryValuesInputStream(new FileInputStream(file));
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                    bala.graph.persistence.EntryValues ev = (bala.graph.persistence.EntryValues) is.readObject();
                  //  entryPanel.useSavedValues(ev);
                    bala.graph.persistence.v1.EntryValues evNew = new bala.graph.persistence.v1.EntryValues(entryPanel);
                    //File fileOut = new File(file.getCanonicalPath().replace("ptf", "evf"));
                    File fileOut = new File(file.getParent() + "\\new\\" + file.getName());//.replace("ptf", "evf").replace("mtf","evf"));
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileOut));
                    os.writeObject(evNew);
                    os.close();
                    is.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // done=false;
                    Logger.getLogger(EntryValuesConverter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}
