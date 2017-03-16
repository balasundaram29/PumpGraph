/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bala.table.tame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.text.View;
import sun.swing.SwingUtilities2;

/*
 * The contents of this file are subject to the Sapient Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://carbon.sf.net/License.html.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * The Original Code is The Carbon Component Framework.
 *
 * The Initial Developer of the Original Code is Sapient Corporation
 *
 * Copyright (C) 2003 Sapient Corporation. All Rights Reserved.
 */
/**
 * This is the template for Classes.
 *
 *
 * @since carbon 1.0
 * @author Greg Hinkle, January 2002
 * @version $Revision: 1.4 $($Author: dvoet $ / $Date: 2003/05/05 21:21:27 $)
 * @copyright 2002 Sapient
 */
class VerticalLabelUI extends BasicLabelUI {

    static {
        labelUI = new VerticalLabelUI(false);
    }
    protected boolean clockwise;

    public static BasicLabelUI getLabelUI() {
        return labelUI;
    }

    public VerticalLabelUI(boolean clockwise) {
        super();
        this.clockwise = clockwise;
    }

    public Dimension getPreferredSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(dim.height, dim.width);
    }
    private static Rectangle paintIconR = new Rectangle();
    private static Rectangle paintTextR = new Rectangle();
    private static Rectangle paintViewR = new Rectangle();
    private static Insets paintViewInsets = new Insets(0, 0, 0, 0);

    public void paint(Graphics g, JComponent c) {

        JLabel label = (JLabel) c;

        String text = label.getText();
        Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

        if ((icon == null) && (text == null)) {
            return;
        }
        FontMetrics fm = SwingUtilities2.getFontMetrics(label, g);
        paintViewInsets = c.getInsets(paintViewInsets);
        g.setColor(c.getBackground());//Color.white);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
        g.setColor(Color.black);
        paintViewR.x = paintViewInsets.left;
        paintViewR.y = paintViewInsets.top;

        // Use inverted height & width
        paintViewR.height = c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
        paintViewR.width = c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
        paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

        String clippedText = text;
        layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform tr = g2.getTransform();
        if (clockwise) {
            g2.rotate(Math.PI / 2);
            g2.translate(0, -c.getWidth());
        } else {
            g2.rotate(-Math.PI / 2);
            g2.translate(-c.getHeight(), 0);
        }

        if (icon != null) {
            icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
        }


        if (text != null) {
            int bottomMargin = 2;
            int textX = paintTextR.x + bottomMargin;
            int textY = paintTextR.y + fm.getAscent();
            View v = (View) c.getClientProperty("html");
            if (v != null) {
                g.translate(bottomMargin, 0);
                v.paint(g, paintTextR);
            } else {
                if (label.isEnabled()) {
                    paintEnabledText(label, g, clippedText, textX, textY);
                } else {
                    paintDisabledText(label, g, clippedText, textX, textY);
                }
                textY = textY + fm.getHeight();
            }
        }
        g2.setTransform(tr);
    }
}
