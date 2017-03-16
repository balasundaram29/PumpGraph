package bala.table.tame;


import bala.graph.gui.ReportTableConstants;
import bala.table.tame.ColumnGroup;
import bala.table.tame.GroupableTableHeader;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/*
 * (swing1.1beta3)
 *
 */
class MinGridTableWithGroupableHeaderUI extends BasicTableHeaderUI {

    public Hashtable h;
    public JTableHeader hd = header;

    @Override
    public void paint(Graphics g, JComponent c) {
        g.setColor(Color.white);
        g.fillRect(0, 0, header.getSize().width, header.getSize().height);
        JTable jTable = header.getTable();
        Dimension size = new Dimension(header.getSize().width, header.getSize().height - jTable.getRowCount() * jTable.getRowHeight());
        g.setColor(Color.blue);
        Rectangle clipBounds = g.getClipBounds();
        if (header.getColumnModel() == null) {
            return;
        }
        ((GroupableTableHeader) header).setColumnMargin();
        int column = 0;

        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
        h = new Hashtable();
        int columnMargin = header.getColumnModel().getColumnMargin();

        Enumeration enumeration = header.getColumnModel().getColumns();
        ArrayList<ColumnGroup> painted = new ArrayList<ColumnGroup>();
        while (enumeration.hasMoreElements()) {
            cellRect.height = size.height;
            cellRect.y = 0;
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            Enumeration cGroups = null;
            if (header instanceof GroupableTableHeader) {
                cGroups = ((GroupableTableHeader) header).getColumnGroups(aColumn);
            }
            if (cGroups != null) {
                int groupHeight = 0;
                while (cGroups.hasMoreElements()) {
                    ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
                    Rectangle groupRect = (Rectangle) h.get(cGroup);
                    if (groupRect == null) {
                        groupRect = new Rectangle(cellRect);
                        Dimension d = cGroup.getSize(header.getTable());
                        groupRect.width = d.width;
                        groupRect.height = d.height;
                        h.put(cGroup, groupRect);
                    }
                    System.out.println("before paintCell" + cGroup.getHeaderValue());
                    if (!painted.contains(cGroup)) {
                        paintCell(g, groupRect, cGroup);
                        painted.add(cGroup);
                    }
                    groupHeight += groupRect.height;
                    cellRect.height = size.height - groupHeight;
                    cellRect.y = groupHeight;
                }
            }
            cellRect.width = aColumn.getWidth();//+ columnMargin;
            if (cellRect.intersects(clipBounds)) {
                paintCell(g, cellRect, column);
            }
            cellRect.x += cellRect.width;
            column++;
        }

        paintTable(g);

    }

    private void paintTable(Graphics g) {

        JTable jTable = header.getTable();
        TableColumnModel cm = jTable.getColumnModel();

        int s = 0;
        for (int column = 0; column < cm.getColumnCount(); column++) {
            System.out.println("Drawing rightside grid for column :" + column);
            int w = cm.getColumn(column).getWidth();
            s += w;

            // g.drawLine(x -1,jTableYOrigin, x -1,  jTableYOrigin + tableHeight - 1);
        }
        Dimension size = new Dimension(s, header.getSize().height - jTable.getRowCount() * jTable.getRowHeight());
        int tableWidth = size.width;//jTable.getWidth();
        int jTableYOrigin = size.height;
        int y = jTableYOrigin;
        int tableHeight = jTable.getRowHeight() * jTable.getRowCount();
        int x = 0;
        int leftMargin = 1;
        g.setColor(Color.blue);
        g.drawLine(leftMargin, y - 1, tableWidth - 1, y - 1);
        // for (int row = 0; row < jTable.getRowCount(); row++) {
        System.out.println("Drawing grid");
        y += jTable.getRowHeight() * jTable.getRowCount();
        g.drawLine(leftMargin, y - 1, tableWidth - 1, y - 1);
         // g.drawLine(leftMargin, y +tableHeight- 1, tableWidth - 1, y+tableHeight - 1);
        //}

        // int tableHeight = damagedArea.y + damagedArea.height;
        //int x;
        // if (table.getComponentOrientation().isLeftToRight()) {
        //x = damagedArea.x;
        g.drawLine(leftMargin, jTableYOrigin, leftMargin, jTableYOrigin + tableHeight - 1);
        for (int column = 0; column < cm.getColumnCount(); column++) {
            System.out.println("Drawing rightside grid for column :" + column);
            int w = cm.getColumn(column).getWidth();
            x += w;

            g.drawLine(x - 1, jTableYOrigin, x - 1, jTableYOrigin + tableHeight - 1);
        }
        Rectangle cellRect = new Rectangle();
        y = jTableYOrigin;

        for (int row = 0; row < jTable.getRowCount(); row++) {
            x = 0;
            for (int column = 0; column < cm.getColumnCount(); column++) {
                System.out.println("Drawing rightside grid for column :" + column);
                int w = cm.getColumn(column).getWidth();

                TableCellRenderer renderer = jTable.getCellRenderer(row, column);
                Component comp = jTable.prepareRenderer(renderer, row, column);
                cellRect.x = x;
                cellRect.width = w - 1;
                if (x == 0) {
                    cellRect.x = 2;
                    cellRect.width = w - 3;
                }
                cellRect.y = y + 1;

                cellRect.height = jTable.getRowHeight() - 2;

                // rendererPane.paintComponent(g, comp, header, cellRect.x+2, cellRect.y+2,
                //       cellRect.width-4, cellRect.height-4, true);
                comp.setForeground(Color.blue);
                rendererPane.paintComponent(g, comp, header, cellRect);
                //  g.drawLine(x - 1, jTableYOrigin, x - 1, jTableYOrigin + tableHeight - 1);
                x += w;

            }
            y += jTable.getRowHeight();
        }
    }
    /* private void  paintTable(Graphics g){
     JTable jTable=header.getTable();
     Dimension size = new Dimension(header.getSize().width,header.getSize().height-jTable.getRowCount()*jTable.getRowHeight());

     for(int i=0;i<10;i++){
     System.out.println("Drawing grid in table");
     g.setColor(Color.blue);
     g.drawLine(0, size.height+i*5,size.width,size.height+i*5);
     }
     };*/

    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer = aColumn.getHeaderRenderer();

        String[] colNames = new String[header.getColumnModel().getColumnCount()];
        for (int i = 0; i < colNames.length; i++) {
            colNames[i] = header.getTable().getColumnName(i);
        }
        //revised by Java2s.com
        renderer = //new MyHeaderRenderer1(Color.green, header);
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        JLabel headerLabel = new JLabel((String) value);
                        headerLabel.setForeground(table.getTableHeader().getForeground());
                        headerLabel.setBackground(table.getTableHeader().getBackground());
                        //  header.setFont(table.getTableHeader().getFont());
                        headerLabel.setForeground(Color.blue);
                        headerLabel.setHorizontalAlignment(JLabel.LEFT);
                        headerLabel.setVerticalAlignment(JLabel.TOP);
                        // header.setText(value.toString());
                        //headerLabel.setUI(new VerticalLabelUI(false));
                        //  header.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                        if (column == ReportTableConstants.SLNO_COL_INDEX) {
                           // headerLabel.setBorder(new CustomBorder(CustomBorder.ALL - CustomBorder.BOTTOM, 1.0f));
                        } else {
                            Enumeration cGroups = ((GroupableTableHeader) header).getColumnGroups(header.getColumnModel().getColumn(column));

                            if (cGroups == null) {
                               // headerLabel.setBorder(new CustomBorder(CustomBorder.ALL - CustomBorder.LEFT - CustomBorder.BOTTOM, 1.0f));
                            } else {
                                //headerLabel.setBorder(new CustomBorder(CustomBorder.RIGHT, 1.0f));
                            }
                        }
                        return headerLabel;
                    }

                };
        Component c = renderer.getTableCellRendererComponent(
                header.getTable(), aColumn.getHeaderValue(), false, false, -1, columnIndex);

        c.setBackground(UIManager.getColor("control"));

        rendererPane.add(c);
        rendererPane.paintComponent(g, c, header, cellRect.x, cellRect.y,
                cellRect.width, cellRect.height, true);
    }

    private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
        TableCellRenderer renderer = cGroup.getHeaderRenderer();
        //revised by Java2s.com
        // if(renderer == null){
//      return ;
        //    }

        Component component = renderer.getTableCellRendererComponent(
                header.getTable(), cGroup.getHeaderValue(), false, false, -1, -1);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
                cellRect.width, cellRect.height, true);
    }

    private int getHeaderHeight() {
        int cols = header.getColumnModel().getColumnCount();
        int h = 0;
        FontMetrics fm = header.getFontMetrics(header.getFont());
        for (int i = 0; i < cols; i++) {
            String[] names = header.getTable().getColumnName(i).split("<BR>");
            for (int j = 0; j < names.length; j++) {
                h = Math.max(h, 60);
            }
        }
        return h + 30;
        /* for (int column = 0; column < columnModel.getColumnCount(); column++) {
         TableColumn aColumn = columnModel.getColumn(column);
         TableCellRenderer renderer = aColumn.getHeaderRenderer();
         //revised by Java2s.com
         if (renderer == null) {
         return 60;
         }

         Component comp = renderer.getTableCellRendererComponent(
         header.getTable(), aColumn.getHeaderValue(), false, false, -1, column);
         int cHeight = comp.getPreferredSize().height;
         Enumeration e = ((GroupableTableHeader) header).getColumnGroups(aColumn);
         if (e != null) {
         while (e.hasMoreElements()) {
         ColumnGroup cGroup = (ColumnGroup) e.nextElement();
         cHeight += cGroup.getSize(header.getTable()).height;
         }
         }
         height = Math.max(height, cHeight);
         }
         return height;*/
    }

    private Dimension createHeaderSize(long width) {
        TableColumnModel columnModel = header.getColumnModel();
        width += columnModel.getColumnMargin() * columnModel.getColumnCount();
        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }
        return new Dimension((int) width, getHeaderHeight());
    }

    public Dimension getPreferredSize(JComponent c) {
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            width = width + aColumn.getPreferredWidth();
        }
        return createHeaderSize(width);
    }
}

