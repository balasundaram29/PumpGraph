/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author ANNAIENG
 */
public class TabNode extends AbstractNode {

    OpenAction theAction = new OpenAction();

    private class OpenAction extends AbstractAction {

        public OpenAction() {
            putValue(NAME, "Go To Tab");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            tc.requestActive();
           if(tc.getOpenFile()!=null){
            System.out.println("file : "+tc.getOpenFile().getName());
             setDisplayName(tc.getOpenFile().getName());
        }
        }
    }
    GraphTopComponent tc;
public void refreshDisplay(){
    if (tc.getOpenFile() == null) {
            setDisplayName("Graph Tab" + tc.getId());
        } else {
            setDisplayName(tc.getOpenFile().getName());
        }
}
    public TabNode(GraphTopComponent c) {

        super(Children.LEAF);
        tc = c;
        if (tc.getOpenFile() == null) {
            setDisplayName("Graph Tab" + c.getId());
        } else {
            setDisplayName(tc.getOpenFile().getName());
        }
        setIconBaseWithExtension("/org/pump/graph/core/pumpfile.png");
    }

    @Override
    public Action getPreferredAction() {
        return theAction;
    }

    @Override
    public Action[] getActions(boolean context) {

        return new Action[]{theAction};
    }
}
