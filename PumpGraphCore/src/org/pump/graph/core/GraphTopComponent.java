/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.graph.GraphPanel;
import bala.graph.gui.ReadingEntryPanel;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.cookies.SaveCookie;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.pump.graph.core//Graph//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "GraphTopComponent",
        iconBase = "/org/pump/graph/core/pumpfile.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)

public final class GraphTopComponent extends TopComponent {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    private Saver saver = new Saver();
    public boolean savePending;
    private JSlider smooth;
    private JSlider rough;
    private InstanceContent content = new InstanceContent();
    static int count = 0;
    private int id;
    private static GraphTopComponent instance;
    private ReadingEntryPanel entryPanel;
    private GraphPanel graphPanel;
    private File openFile = null;
    /**
     * path to the icon used by the component and its open action
     */
    //static final String ICON_PATH = "/org/pump/core/Print1624.gif";
    private static final String PREFERRED_ID = "GraphTopComponent";

    public void modifyEntryPanel(String s) {
        this.entryPanel.getSlNoField().setText(s);
    }

    /**
     * @return the graphPanel
     */
    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    /**
     * @param graphPanel the graphPanel to set
     */
    public void setGraphPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }

    /**
     * @return the smooth
     */
    public JSlider getSmooth() {
        return smooth;
    }

    /**
     * @param smooth the smooth to set
     */
    public void setSmooth(JSlider smooth) {
        this.smooth = smooth;
    }

    /**
     * @return the rough
     */
    public JSlider getRough() {
        return rough;
    }

    /**
     * @param rough the rough to set
     */
    public void setRough(JSlider rough) {
        this.rough = rough;
    }

    private class Saver implements SaveCookie {

        public void save() throws IOException {
            //throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public GraphTopComponent() {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.SEVERE);
         String pathToScan = ".";
    String fileThatYouWantToFilter;
    File folderToScan = new File(pathToScan); // import -> import java.io.File;
    File[] listOfFiles = folderToScan.listFiles();

    for (int i = 0; i < listOfFiles.length; i++) {

        if (listOfFiles[i].isFile()) {
            fileThatYouWantToFilter = listOfFiles[i].getName();
            if (fileThatYouWantToFilter.startsWith("PumpGraphLog.txt")
                    && !fileThatYouWantToFilter.endsWith(".txt")) {
              listOfFiles[i].delete();
                //System.out.println("found" + " " + fileThatYouWantToFilter);
            }
        }
    }
        try {
            fileTxt = new FileHandler("PumpGraphLog.txt");
            // fileHTML = new FileHandler("Logging.html");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SecurityException ex) {
            Exceptions.printStackTrace(ex);
        }

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

//initComponents();
        setLayout(new BorderLayout());
        // GraphTopComponent tc=new GraphTopComponent();
        entryPanel = new ReadingEntryPanel(new AppConstants(AppConstants.defaultStandard));
        setEntryPanel(entryPanel);
        // add (new JPanel(),BorderLayout.CENTER);       
        add(new JScrollPane(entryPanel), BorderLayout.CENTER);
        setName(NbBundle.getMessage(GraphTopComponent.class, "CTL_GraphTopComponent"));
        setToolTipText(NbBundle.getMessage(GraphTopComponent.class, "HINT_GraphTopComponent"));
        //setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        id = ++count;
        associateLookup(new AbstractLookup(content));
        content.add(saver);
        setDisplayName("Graph Tab" + getId());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files
     * only, i.e. deserialization routines; otherwise you could get a
     * non-deserialized instance. To obtain the singleton instance, use
     * {@link #findInstance}.
     */
    public static synchronized GraphTopComponent getDefault() {
        if (instance == null) {
            instance = new GraphTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the GraphTopComponent instance. Never call {@link #getDefault}
     * directly!
     */
    public static synchronized GraphTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(GraphTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof GraphTopComponent) {
            return (GraphTopComponent) win;
        }
        Logger.getLogger(GraphTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        DynamicLookup.getDefault().add(this);
    }

    @Override
    public void componentClosed() {
        DynamicLookup.getDefault().remove(this);
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    /**
     * @return the entryPanel
     */
    public ReadingEntryPanel getEntryPanel() {
        return entryPanel;
    }

    /**
     * @return the content
     */
    public InstanceContent getInstanceContent() {
        return content;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param entryPanel the entryPanel to set
     */
    public void setEntryPanel(ReadingEntryPanel entryPanel) {
        this.entryPanel = entryPanel;
    }

    /**
     * @return the openFile
     */
    public File getOpenFile() {
        return openFile;
    }

    /**
     * @param openFile the openFile to set
     */
    public void setOpenFile(File openFile) {
        try {
            this.openFile = openFile;
            this.setDisplayName(openFile.getName());
            setToolTipText(openFile.getCanonicalPath());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
