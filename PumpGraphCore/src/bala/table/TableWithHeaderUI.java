/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bala.table;

import java.awt.Graphics;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author ANNAIENG
 */
public class TableWithHeaderUI extends ComponentUI  {
 protected CellRendererPane rendererPane=new CellRendererPane();
TableWithHeader tablePlusHeader;
    @Override
   public void installUI(JComponent c) {
        //table = (JTable)c;
       tablePlusHeader=(TableWithHeader)c;
        rendererPane = new CellRendererPane();
        c.add(rendererPane);
      //  installDefaults();
        //installDefaults2();
        //installListeners();
        //installKeyboardActions();
    }
 public void uninstallUI(JComponent c) {
        

        c.remove(rendererPane);
        rendererPane = null;
     //   c = null;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
       // super.paint(g, c);
       
        rendererPane.paintComponent(g, tablePlusHeader.jTable, tablePlusHeader, 0,this.tablePlusHeader.jTable.getTableHeader().getPreferredSize().height ,
                this.tablePlusHeader.getWidth(),this.tablePlusHeader.jTable.getPreferredSize().height, true);
        
      rendererPane.paintComponent(g, tablePlusHeader.jTable.getTableHeader(), tablePlusHeader, 0,0 ,
                this.tablePlusHeader.getWidth(),this.tablePlusHeader.jTable.getTableHeader().getPreferredSize().height, true);
    }

}
