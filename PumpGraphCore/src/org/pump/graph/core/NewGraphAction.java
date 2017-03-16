/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class NewGraphAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
         GraphTopComponent tc= new GraphTopComponent();

        tc.open();
        tc.requestActive();
    }
}
