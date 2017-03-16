/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "org.pump.graph.core.SetStandardToBorewellSubAction"
)
@ActionRegistration(
        iconBase = "bala/graph/graph/bws24.png",
        displayName = "#CTL_SetStandardToBorewellSubAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = -37, separatorAfter = -31),
    @ActionReference(path = "Toolbars/File", position = 155)
})
@Messages("CTL_SetStandardToBorewellSubAction=Set Standard To Borewell Sub - IS8034")
public final class SetStandardToBorewellSubAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        AppConstants.defaultStandard=IndianStandard.IS8034;
    }
}
