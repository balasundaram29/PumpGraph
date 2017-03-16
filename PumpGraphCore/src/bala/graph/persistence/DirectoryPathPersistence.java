/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bala.graph.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author ANNAIENG
 */
public class DirectoryPathPersistence {
 public static File getFileDirectory(String name) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(name)));
            File directory = (File) ois.readObject();
            return directory;
        } catch (Exception ex) {
        }
        return null;
    }

    public static void saveFileDirectory(File directory,String name) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(name)));
            oos.writeObject(directory);

        } catch (Exception ex) {
        }
        //return null;
    }
}
