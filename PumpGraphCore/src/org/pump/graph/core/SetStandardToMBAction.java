/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class SetStandardToMBAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        AppConstants.defaultStandard=IndianStandard.IS9079;
    }
}
