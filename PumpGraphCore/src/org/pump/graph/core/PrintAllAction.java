/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.graph.ComponentToBePrintedType;
import bala.graph.graph.GraphPanel;
import bala.graph.graph.LoessSmoothRenderer;
import bala.graph.graph.Renderer;
import bala.graph.utilities.ModifiedPrintUtility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

public final class PrintAllAction implements ActionListener {

    private Lookup.Result<GraphPanel> result = null;
    GraphPanel graphPanel = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("In Printall");
Mode m = WindowManager.getDefault().findMode("editor");
       GraphTopComponent     gTopComp = (GraphTopComponent) m.getSelectedTopComponent();
       // result = Utilities.actionsGlobalContext().lookupResult(GraphPanel.class);
GraphPanel graphPanel=gTopComp.getGraphPanel();
      //  Collection<? extends GraphPanel> gps = result.allInstances();
      //  if (!gps.isEmpty()) {
           // graphPanel = gps.iterator().next();
                 
            try {
                new ModifiedPrintUtility(graphPanel.getReportPanel().getEntryPanel(), graphPanel, ComponentToBePrintedType.ECONOMY_REPORT).print();
            } catch (Exception ex) {
            }
      //  }
         //gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();
         gTopComp.validate();
         gTopComp.repaint();
       /* double smooth = ((LoessSmoothRenderer) graphPanel.getGraph().getPlot().getRenderer(0)).getLoessFunction().getBandwidth();
        double linearity = ((LoessSmoothRenderer) graphPanel.getGraph().getPlot().getRenderer(0)).getLoessFunction().getDesiredLinearityFactor();
        GraphTopComponent gTopComp = (GraphTopComponent) GraphTopComponent.getRegistry().getActivated();

        ViewGraphAction act = new ViewGraphAction();
        act.actionPerformed(null);
        Renderer renderer = graphPanel.getGraph().getPlot().getRendererList().get(0);
        double lf = ((LoessSmoothRenderer) renderer).getLoessFunction().getDesiredLinearityFactor();//(getRough().getValue())
       double bw = ((LoessSmoothRenderer) renderer).getLoessFunction().getBandwidth();
        act.getRough().setValue((int) (lf));
        act.getSmooth().setValue((int) (bw*100.0));
//act.getSmooth().setValue((int)(smooth*100.0));

        //gTopComp.add(graphPanel);
        //gTopComp.repaint();*/
    }
}
