/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.logging.Logger;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.pump.graph.core//TabExplorer//EN",
autostore = false)
public final class TabExplorerTopComponent extends TopComponent implements ExplorerManager.Provider, LookupListener {

    private final ExplorerManager mgr = new ExplorerManager();
    private static TabExplorerTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "TabExplorerTopComponent";
    Lookup.Result<GraphTopComponent> result1;
    OpenTab root = new OpenTab();

    public TabExplorerTopComponent() {
        //initComponents();
        WindowManager.getDefault().getMainWindow().setTitle("Annai - Pump Graphs");
        setLayout(new BorderLayout());
        add(new BeanTreeView(), BorderLayout.CENTER);
        setName(NbBundle.getMessage(TabExplorerTopComponent.class, "CTL_TabExplorerTopComponent"));
        setToolTipText(NbBundle.getMessage(TabExplorerTopComponent.class, "HINT_TabExplorerTopComponent"));
        mgr.setRootContext(new AbstractNode(root));//new AbstractNode(Children.LEAF));
        mgr.getRootContext().setDisplayName("Open Tabs");
        //        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        result1 = DynamicLookup.getDefault().lookupResult(GraphTopComponent.class);


        result1.addLookupListener(this);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized TabExplorerTopComponent getDefault() {
        if (instance == null) {
            instance = new TabExplorerTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the TabExplorerTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized TabExplorerTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(TabExplorerTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof TabExplorerTopComponent) {
            return (TabExplorerTopComponent) win;
        }
        Logger.getLogger(TabExplorerTopComponent.class.getName()).warning(
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
    }

    @Override
    public void componentClosed() {
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

    @Override
    public ExplorerManager getExplorerManager() {
        return mgr;
    }

    public void refreshDisplay() {
        Collection<? extends GraphTopComponent> list = result1.allInstances();
        root.refreshList(list);
        if (root.getNodes() == null) {
            return;
        }
        Node[] ns = root.getNodes();
        for (int i = 0; i < ns.length; i++) {
            TabNode tn = (TabNode) ns[i];
            System.out.println("refreshing display");
            tn.refreshDisplay();
        }
    }

    @Override
    public void resultChanged(LookupEvent le) {
        Collection<? extends GraphTopComponent> list = result1.allInstances();
        root.refreshList(list);
        if (root.getNodes() == null) {
            return;
        }
        Node[] ns = root.getNodes();
        for (int i = 0; i < ns.length; i++) {
            TabNode tn = (TabNode) ns[i];
            System.out.println("refreshing display");
            tn.refreshDisplay();
        }
    }
}