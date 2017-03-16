/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pump.graph.core;

import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.pump.graph.core//TorqueCalculator//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TorqueCalculatorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.pump.graph.core.TorqueCalculatorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TorqueCalculatorAction",
        preferredID = "TorqueCalculatorTopComponent"
)
@Messages({
    "CTL_TorqueCalculatorAction=TorqueCalculator",
    "CTL_TorqueCalculatorTopComponent=TorqueCalculator Window",
    "HINT_TorqueCalculatorTopComponent=This is a TorqueCalculator window"
})
public final class TorqueCalculatorTopComponent extends TopComponent {

    public TorqueCalculatorTopComponent() {
        initComponents2();
        setName(Bundle.CTL_TorqueCalculatorTopComponent());
        setToolTipText(Bundle.HINT_TorqueCalculatorTopComponent());

    }

    private void initComponents2() {
        // bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel1 = new javax.swing.JLabel();
        hpField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ratedVoltageField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ratedSpeedField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        t1Field = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        t2Field = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        leverLengthField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        observedVoltageField = new javax.swing.JTextField();
        calcButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        torqueField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        percentTorqueField = new javax.swing.JTextField();

        //   org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, leverLengthField, org.jdesktop.beansbinding.ObjectProperty.create(), this, org.jdesktop.beansbinding.BeanProperty.create("alignmentX"));
        //bindingGroup.addBinding(binding);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel1.text")); // NOI18N

        hpField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.hpField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel2.text")); // NOI18N

        ratedVoltageField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.ratedVoltageField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel3.text")); // NOI18N

        ratedSpeedField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.ratedSpeedField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel4.text")); // NOI18N

        t1Field.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.t1Field.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel5.text")); // NOI18N

        t2Field.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.t2Field.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel6.text")); // NOI18N

        leverLengthField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.leverLengthField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel7.text")); // NOI18N

        observedVoltageField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.observedVoltageField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(calcButton, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.calcButton.text")); // NOI18N
        calcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonActionPerformed(evt);
            }
        });
        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel8.text")); // NOI18N

        torqueField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.torqueField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel9.text")); // NOI18N

        percentTorqueField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.percentTorqueField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel1))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(hpField, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                                .addComponent(t1Field)))
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(t2Field, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                                .addComponent(ratedVoltageField)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(torqueField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(calcButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(140, 140, 140)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(46, 46, 46)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ratedSpeedField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(observedVoltageField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(leverLengthField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(percentTorqueField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(hpField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(ratedVoltageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(ratedSpeedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(t1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(t2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(leverLengthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(observedVoltageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addComponent(calcButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(torqueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(percentTorqueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55))
        );

        // bindingGroup.bind();
    }// </editor-fold>                

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel1 = new javax.swing.JLabel();
        hpField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ratedVoltageField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ratedSpeedField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        t1Field = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        t2Field = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        leverLengthField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        observedVoltageField = new javax.swing.JTextField();
        calcButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        torqueField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        percentTorqueField = new javax.swing.JTextField();

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, leverLengthField, org.jdesktop.beansbinding.ObjectProperty.create(), this, org.jdesktop.beansbinding.BeanProperty.create("alignmentX"));
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel1.text")); // NOI18N

        hpField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.hpField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel2.text")); // NOI18N

        ratedVoltageField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.ratedVoltageField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel3.text")); // NOI18N

        ratedSpeedField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.ratedSpeedField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel4.text")); // NOI18N

        t1Field.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.t1Field.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel5.text")); // NOI18N

        t2Field.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.t2Field.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel6.text")); // NOI18N

        leverLengthField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.leverLengthField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel7.text")); // NOI18N

        observedVoltageField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.observedVoltageField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(calcButton, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.calcButton.text")); // NOI18N
        calcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel8.text")); // NOI18N

        torqueField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.torqueField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.jLabel9.text")); // NOI18N

        percentTorqueField.setText(org.openide.util.NbBundle.getMessage(TorqueCalculatorTopComponent.class, "TorqueCalculatorTopComponent.percentTorqueField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hpField, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                            .addComponent(t1Field)))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(t2Field, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(ratedVoltageField)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(torqueField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(calcButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ratedSpeedField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(observedVoltageField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(leverLengthField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(percentTorqueField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hpField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(ratedVoltageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(ratedSpeedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(t1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(t2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(leverLengthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(observedVoltageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(calcButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(torqueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(percentTorqueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void calcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonActionPerformed
        // TODO add your handling code here:
        try {
            double hp = Double.valueOf(hpField.getText());
            double ratedVoltage = Double.valueOf(ratedVoltageField.getText());
            double ratedSpeed = Double.valueOf(ratedSpeedField.getText());
            double t1 = Double.valueOf(t1Field.getText());
            double t2 = Double.valueOf(t2Field.getText());
            double leverLength = Double.valueOf(leverLengthField.getText());
            double observedTorque = (t2 - t1) * leverLength;
            double ratedTorque = hp * 4500.00 / (2.0 * Math.PI * ratedSpeed);
            double percentTorque = 100.0 * observedTorque / ratedTorque;
            torqueField.setText(String.format("%.2f", observedTorque));
            percentTorqueField.setText(String.format("%.2f", percentTorque));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_calcButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calcButton;
    private javax.swing.JTextField hpField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField leverLengthField;
    private javax.swing.JTextField observedVoltageField;
    private javax.swing.JTextField percentTorqueField;
    private javax.swing.JTextField ratedSpeedField;
    private javax.swing.JTextField ratedVoltageField;
    private javax.swing.JTextField t1Field;
    private javax.swing.JTextField t2Field;
    private javax.swing.JTextField torqueField;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
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

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
