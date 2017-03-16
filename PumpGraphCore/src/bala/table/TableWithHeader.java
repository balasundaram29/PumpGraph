/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bala.table;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author ANNAIENG
 */
public class TableWithHeader extends JComponent {

    protected JTable jTable;

    public TableWithHeader(JTable jTable) {
        this.jTable = jTable;
        setUI(new TableWithHeaderUI());
      //  setPreferredSize(new Dimension(600, 300));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(jTable.getPreferredSize().width,jTable.getPreferredSize().height
                +jTable.getTableHeader().getPreferredSize().height+2);
    }

    public static void main(String[] args) {
    
    }
}
