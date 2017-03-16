/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pump.graph.core;

import java.util.Collection;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author ANNAIENG
 */

public class OpenTab extends Children.Keys<GraphTopComponent> {

    @Override
    protected Node[] createNodes(GraphTopComponent c) {

        System.out.println("creating node");
        return new Node[]{new TabNode(c)};
    }
void refreshList(Collection l){
   setKeys(l);
}

}
