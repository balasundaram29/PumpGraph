/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bala.table.tame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import bala.table.tame.GroupableTableHeader;
//import static bala.table.tame.InputMapLister.list;
import com.sun.org.apache.bcel.internal.generic.LADD;
import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIDefaults;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TableUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.JTableHeader;
import sun.swing.SwingUtilities2;
//this class includes Haeder and table inside  JTAble.
//Set the JTable's preferred height  to real Table header height plus jtable's rowheight * row count;

public class GroupableHeaderPlusTableUI extends BasicTableUI {

    public GroupableHeaderPlusTableUI(JTable table) {
        super();
        UIDefaults uidef = UIManager.getDefaults();
        Enumeration keys = uidef.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            //System.out.println("UI , Key : " + key + "   Element : " + uidef.get(key));
        }
        this.table = table;
        addListener();
        //table.doLayout();
    }

    public static void main(String[] args) {
        //System.out.println("ok");
    }
    int i = 0;
    // private Color selectBg=(Color)UIManager.getColor("Table.selectionBackground");
    private Cursor vResizeCursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
    private Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
    private Cursor otherCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private Cursor defaultCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private int HeaderAreaHeight;
    boolean cellSelected = false;
    int selectedRow = -101;
    int selectedCol = 0;
    TableColumn draggedColumn;
    TableColumn resizingColumn;
    ColumnGroup resizingGroup;
    int draggedDistance = 0;
    Rectangle selectedGroupRect = null;
    boolean groupSelected = false;
    Point selectedPoint = null;
    Enumeration cGroups;
    //group to rect map;
    Hashtable h;
    int k = 0;

    public ColumnGroup getColumnGroupAt(Point p) {
        k++;
        Enumeration groups = h.keys();
        while (groups.hasMoreElements()) {
            ColumnGroup group = (ColumnGroup) groups.nextElement();

            Rectangle rect = (Rectangle) h.get(group);
            //System.out.println("getColumnGroup group , k=" + k + group + " rectangle is" + rect);
            if (rect.contains(p)) {
                //System.out.println("From getColumnGroup returning group" + group + " rectangle is" + rect);
                return group;
            }
        }
        return null;
    }

    public Rectangle getGroupRectAt(Point p) {
        Enumeration groups = h.keys();
        while (groups.hasMoreElements()) {
            ColumnGroup group = (ColumnGroup) groups.nextElement();
            Rectangle groupRect = (Rectangle) h.get(group);
            if (groupRect.contains(p)) {
                return groupRect;
            }

        }
        return null;
    }
    //   BasicTableUI

    private void addListener() {
        // super.installListeners();
        super.uninstallListeners();
        super.uninstallDefaults();
        super.uninstallKeyboardActions();
        //super.installKeyboardActions();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.removeMouseListener(mouseInputListener);
        table.removeMouseMotionListener(mouseInputListener);
        table.removeKeyListener(keyListener);
        table.setEnabled(true);
        table.setCursor(resizeCursor);
        table.requestFocusInWindow();
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // table.setCellSelectionEnabled(cellSelected);
        Action doNothing = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
            }
        };
        //   selectBg=table.getSelectionBackground();

        table.getActionMap().put("doNothing",
                doNothing);
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "doNothing");
//table.setCellSelectionEnabled(true);
        //  InputMap map = table.getInputMap();
        // list(map, map.keys());
        //  list(map, map.allKeys());
        KeyAdapter kl = new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("Key Released");
//  super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //To change body of generated methods, choose Tools | Templates.
                //System.out.println("Key Pressed");
                //System.out.println("found " + selectedRow + "," + selectedCol);
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP | key == KeyEvent.VK_KP_UP) {
                    selectedRow = (selectedRow == -1 ? table.getRowCount() - 1 : --selectedRow);
                }
                if (key == KeyEvent.VK_DOWN | key == KeyEvent.VK_KP_DOWN) {
                    selectedRow = (selectedRow == table.getRowCount() - 1 ? -1 : ++selectedRow);
                }
                if (key == KeyEvent.VK_LEFT | key == KeyEvent.VK_KP_LEFT) {
                    selectedCol = (selectedCol == 0 ? table.getColumnCount() - 1 : --selectedCol);
                }
                if (key == KeyEvent.VK_RIGHT | key == KeyEvent.VK_KP_RIGHT) {
                    selectedCol = (selectedCol == table.getColumnCount() - 1 ? 0 : ++selectedCol);

                }
                //System.out.println("selected " + selectedRow + "," + selectedCol);
                // table.setRowSelectionInterval(selectedRow, selectedRow);
                //table.setColumnSelectionInterval(selectedCol, selectedCol);

                table.repaint();
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //To change body of generated methods, choose Tools | Templates.
                //System.out.println("Key typed");
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP | key == KeyEvent.VK_KP_UP) {
                    if (selectedRow == 0) {
                        selectedRow = table.getRowCount();
                    } else {
                        selectedRow--;
                    }
                    table.repaint();
                    //    selectedRow = (selectedRow == 0 ? table.getRowCount() - 1 : --selectedRow);
                    table.repaint();
                }
            }
        };

        MouseAdapter ml = new MouseAdapter() {

            private int mouseXRef, mouseYRef, widthRef, heightRef;

            ;

            private boolean insideHeaderArea(Point p) {
                return p.y < (table.getHeight() - table.getRowCount() * table.getRowHeight());
            }

            private boolean canResize(TableColumn column,
                    JTable table1) {
                return (column != null) && column.getResizable();
            }

            private boolean canResize(ColumnGroup group,
                    JTable table1) {
                return (group != null);// && column.getResizable();
            }

            private ColumnGroup getResizingColumnGroup(Point p) {
                ColumnGroup group = getColumnGroupAt(p);
                if (group == null) {
                    p.y = p.y - 3;
                    group = getColumnGroupAt(p);
                    return group;
                }
                Rectangle r = (Rectangle) h.get(group);
                r.grow(0, -3);
                if (r.contains(p)) {
                    return null;
                }
                r.grow(0, 3);
                int midPoint = r.y + r.height / 2;
                if (p.y < midPoint) {
                    return getColumnGroupAt(new Point(p.x, r.y - 1));
                } else {
                    return group;
                }
            }

            private TableColumn getResizingColumn(Point p) {
                // //System.out.println("inside getrezingcolumn");
                if (getGroupRectAt(p) != null) {
                    return null;
                }
                int column = table.columnAtPoint(p);
                if (column == -1) {
                    //    //System.out.println("return null beacuse  column==-1,inside getrezingcolumn");
                    return null;
                }
                Rectangle r = table.getCellRect(0, column, true);
                p.y = r.y + 5;

                r.grow(-3, 0);
                if (r.contains(p)) {
                    // //System.out.println("return null because r contains p,inside getrezingcolumn");
                    return null;
                }
                int midPoint = r.x + r.width / 2;
                int columnIndex;
                if (table.getComponentOrientation().isLeftToRight()) {
                    columnIndex = (p.x < midPoint) ? column - 1 : column;
                } else {
                    columnIndex = (p.x < midPoint) ? column : column - 1;
                }
                if (columnIndex == -1) {
                    // //System.out.println("return null beacuse  column==-1,may be firstcolumn,inside getrezingcolumn");
                    return null;
                }
                // //System.out.println("return  table.getColumnModel().getColumn(columnIndex) ="
                // + table.getColumnModel().getColumn(columnIndex));
                return table.getColumnModel().getColumn(columnIndex);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
               
                Point p = e.getPoint();
                selectedPoint = new Point(p.x, p.y);
                if (!insideHeaderArea(p)) {
                    p.y = p.y - HeaderAreaHeight;
                    selectedRow = table.rowAtPoint(p);
                    selectedCol = table.columnAtPoint(p);
                    //table.setCellSelectionEnabled(true);
                    //  table.setRowSelectionInterval(selectedRow, selectedRow);
                    //table.setColumnSelectionInterval(selectedCol, selectedCol);
                    //System.out.println("selected by mouse  " + selectedRow + "," + selectedCol);
                } else if (getGroupRectAt(p) != null) {
                    selectedGroupRect = getGroupRectAt(p);
                    //System.out.println("Group rect selected");
                } else {
                    selectedRow = -1;
                    p.y = 2;
                    selectedCol = table.columnAtPoint(p);
                }
                table.repaint();
// //System.out.println("inside click");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //  super.mouseReleased(e); //To change body of generated methods, choose Tools | Templates.
              
                setDraggedDistance(0, viewIndexForColumn(draggedColumn));

                resizingColumn = null;
                draggedColumn = null;
                resizingGroup = null;
            }

            private void swapCursor() {
                Cursor tmp = table.getCursor();
                table.setCursor(otherCursor);
                otherCursor = tmp;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (!table.isEnabled()) {
                    return;
                }
                table.setCursor(otherCursor);
                Point p = e.getPoint();
                if (canResize(getResizingColumnGroup(p), table) && insideHeaderArea(p)) {
                    table.setCursor(vResizeCursor);
                } else if (((getColumnGroupAt(p) == null) && canResize(getResizingColumn(e.getPoint()), table) && insideHeaderArea(p))) {
                    table.setCursor(resizeCursor);
                }
                //  if (!insideHeaderArea(p)) {
                //    table.setCursor(otherCursor);
                // }
                //  table.repaint();
            }

            public void mousePressed(MouseEvent e) {
               
                //System.out.println("inside press");
                if (!table.isEnabled()) {
                    return;
                }
                draggedColumn = null;
                resizingColumn = null;
                resizingGroup = null;
                Point p = e.getPoint();
                // First find which header cell was hit
                int index = table.columnAtPoint(p);
                ColumnGroup group = getColumnGroupAt(p);

                if (canResize(getResizingColumnGroup(p), table) && insideHeaderArea(p)) {
                    resizingGroup = getResizingColumnGroup(p);
                    mouseYRef = p.y;
                    heightRef = resizingGroup.getHeight();
                }
                if (index != -1) {
                    // The last 3 pixels + 3 pixels of next column are for resizing
                    resizingColumn = getResizingColumn(p);
                    if (resizingColumn != null) {
                        mouseXRef = p.x;
                        widthRef = resizingColumn.getWidth();
                    } else if (resizingGroup == null && group == null) {
                        draggedColumn = table.getColumnModel().getColumn(index);
                        mouseXRef = p.x;
                    }
                }


            }

            public void mouseDragged(MouseEvent e) {
                //System.out.println("inside drag");
                if (!table.isEnabled()) {
                    return;
                }
                int mouseX = e.getPoint().x;
                int mouseY = e.getPoint().y;
                boolean headerLeftToRight = table.getComponentOrientation().isLeftToRight();
                if (resizingGroup != null) {
                    // int oldHeight = resizingGroup.getHeight();
                    int newHeight = (mouseY - mouseYRef) + heightRef;
                    // resizingGroup.setPreferredHeight(resizingGroup.getHeight() + newHeight - oldHeight);
                    resizingGroup.setPreferredHeight(newHeight);
                    table.repaint();
                    return;
                }
                if (resizingGroup == null && resizingColumn != null) {
                    int oldWidth = resizingColumn.getWidth();
                    int newWidth = mouseX - mouseXRef + widthRef;

                    //System.out.println("Setting column width");
                    //resizingColumn.setPreferredWidth(resizingColumn.getPreferredWidth() + newWidth - oldWidth);
                    //  table.getTableHeader().setResizingColumn(resizingColumn);
                    resizingColumn.setPreferredWidth(newWidth);
                    //table.doLayout();

                } else if (resizingGroup == null && draggedColumn != null) {
                    TableColumnModel cm = table.getColumnModel();
                    draggedDistance = mouseX - mouseXRef;
                    int direction = (draggedDistance < 0) ? -1 : 1;
                    int columnIndex = viewIndexForColumn(draggedColumn);
                    int newColumnIndex = columnIndex + (headerLeftToRight ? direction : -direction);
                    if (0 <= newColumnIndex && newColumnIndex < cm.getColumnCount()) {
                        int width = cm.getColumn(newColumnIndex).getWidth();
                        if (Math.abs(draggedDistance) > (width / 2)) {
                            mouseXRef = mouseXRef + direction * width;
                            //Cache the selected column.
                            int selectedIndex = SwingUtilities2.convertColumnIndexToModel(
                                    table.getColumnModel(),
                                    columnIndex);

                            //Now do the move.
                            cm.moveColumn(columnIndex, columnIndex + direction);

                            //Update the selected index.
                            // selectColumn(
                            SwingUtilities2.convertColumnIndexToView(
                                    table.getColumnModel(), selectedIndex);//,
                            //   false);
                            //draggedColumn=null;
                            return;
                        }

                    }
                    setDraggedDistance(draggedDistance, columnIndex);
                    table.repaint();
                }

            }

            private void setDraggedDistance(int draggedDistance1, int column) {
                draggedDistance = draggedDistance1;
                if (column != -1) {
                    table.getColumnModel().moveColumn(column, column);
                }
            }
        };

        table.addMouseListener(ml);

        table.addMouseMotionListener(ml);
        table.addKeyListener(kl);
    }

    private int viewIndexForColumn(TableColumn aColumn) {
        TableColumnModel cm = table.getColumnModel();
        for (int column = 0; column < cm.getColumnCount(); column++) {
            if (cm.getColumn(column) == aColumn) {
                return column;
            }
        }
        return -1;
    }
//for header grid

    public void drawInvertedLShape(Graphics g, Rectangle rect) {

        g.setColor(table.getGridColor());
        if (rect.x == 1) {

            g.drawLine(rect.x, rect.y, rect.x + rect.width - 1, rect.y);
        }
        if (rect.x != 1) {
            g.drawLine(rect.x - 1, rect.y, rect.x + rect.width - 1, rect.y);
        }
        g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height);
        //g.drawLine(rect.x, rect.y, rect.x,rect.y+rect.height);
    }
//for haeder grid leftmost line

    public void drawLeftLine(Graphics g, Rectangle rect) {
        g.setColor(table.getGridColor());
        // g.drawLine(rect.x, rect.y, rect.x + rect.width - 1, rect.y);
        g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height);
        //g.drawLine(rect.x, rect.y, rect.x,rect.y+rect.height);
    }

    public void loadRectangles(Graphics g) {
        int column = 0;
        //  Dimension size = header.getSize();
        Dimension size = new Dimension(table.getSize().width, table.getSize().height - table.getRowCount() * table.getRowHeight());
        JTableHeader header = table.getTableHeader();
        Rectangle clipBounds = g.getClipBounds();
        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
        h = new Hashtable();
        //  int columnMargin = header.getColumnModel().getColumnMargin();

        Enumeration enumeration = table.getTableHeader().getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            cellRect.height = size.height;
            cellRect.y = 0;
            if (column == 0) {
                cellRect.x = 1;
            }
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            //cGroups = null;
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
                        if (column == 0) {
                            groupRect.width = d.width - 1;
                        }
                        groupRect.height = d.height;
                        h.put(cGroup, groupRect);
                    }
                    if (!cGroup.isPaintedAlready()) {
                        if (groupRect.y == 0) {
                            groupRect.y = 1;
                        }
                        // paintCell(g, groupRect, cGroup);
                        //drawInvertedLShape(g, groupRect);
                        if (column == 0) {
                            //  drawLeftLine(g, groupRect);
                        }
                        //cGroup.setPaintedAlready(true);
                    }
                    groupHeight += groupRect.height;
                    cellRect.height = size.height - groupHeight;
                    cellRect.y = groupHeight;
                }
            }

            cellRect.width = aColumn.getWidth();//+ columnMargin;
            if (column == 0) {
                cellRect.width = aColumn.getWidth() - 1;
                //   groupRect.width = d.width - 1;
            }
            if (cellRect.intersects(clipBounds)) {
                if (cellRect.y == 0) {
                    // cellRect.y = 1;
                }
                // paintCell(g, cellRect, column);
                //drawInvertedLShape(g, cellRect);
                if (column == 0) {
                    //  drawLeftLine(g, cellRect);
                }
            }

            cellRect.x += cellRect.width;
            column++;
        }
    }

    @Override
    public void paint(Graphics g, JComponent c) {

        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(0.80f));
       loadRectangles(g);
        //System.out.println("Repainting table ..." + (++i));
        JTable jTable = table;

        HeaderAreaHeight = table.getSize().height - jTable.getRowCount() * jTable.getRowHeight();
        JTableHeader header = table.getTableHeader();
        Dimension size = new Dimension(table.getSize().width, table.getSize().height - jTable.getRowCount() * jTable.getRowHeight());

        Rectangle clipBounds = g.getClipBounds();
        g.setColor(Color.white);
        g.fillRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
        g.setColor(table.getForeground());
        if (table.getTableHeader().getColumnModel() == null) {
            return;
        }
        Enumeration enumeration1 = table.getTableHeader().getColumnModel().getColumns();
        while (enumeration1.hasMoreElements()) {
            TableColumn aColumn1 = (TableColumn) enumeration1.nextElement();
            Enumeration cGroups1 = null;
            if (header instanceof GroupableTableHeader) {

                cGroups1 = ((GroupableTableHeader) table.getTableHeader()).getColumnGroups(aColumn1);
            }
            if (cGroups1 != null) {
                while (cGroups1.hasMoreElements()) {
                    ColumnGroup cGroup = (ColumnGroup) cGroups1.nextElement();
                    cGroup.setPaintedAlready(false);
                }
            }
        }
        //((GroupableTableHeader) header).setColumnMargin();
        int column = 0;
        //  Dimension size = header.getSize();
        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
        Hashtable h1 = new Hashtable();
        //  int columnMargin = header.getColumnModel().getColumnMargin();

        Enumeration enumeration = table.getTableHeader().getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            cellRect.height = size.height;
            cellRect.y = 0;
            if (column == 0) {
                cellRect.x = 1;
            }
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            //cGroups = null;
            if (header instanceof GroupableTableHeader) {
                cGroups = ((GroupableTableHeader) header).getColumnGroups(aColumn);
            }
            if (cGroups != null) {
                int groupHeight = 0;
                while (cGroups.hasMoreElements()) {
                    ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
                    Rectangle groupRect = (Rectangle) h1.get(cGroup);
                    if (groupRect == null) {
                        groupRect = new Rectangle(cellRect);
                        Dimension d = cGroup.getSize(header.getTable());

                        groupRect.width = d.width;
                        if (column == 0) {
                            groupRect.width = d.width - 1;
                        }
                        groupRect.height = d.height;
                        h1.put(cGroup, groupRect);
                    }
                    if (!cGroup.isPaintedAlready()) {
                        if (groupRect.y == 0) {
                            groupRect.y = 1;
                        }
                        paintCell(g, groupRect, cGroup);
                        drawInvertedLShape(g, groupRect);
                        if (column == 0) {
                            drawLeftLine(g, groupRect);
                        }
                        cGroup.setPaintedAlready(true);
                    }
                    groupHeight += groupRect.height;
                    cellRect.height = size.height - groupHeight;
                    cellRect.y = groupHeight;
                }
            }

            cellRect.width = aColumn.getWidth();//+ columnMargin;
            if (column == 0) {
                cellRect.width = aColumn.getWidth() - 1;
                //   groupRect.width = d.width - 1;
            }
            if (cellRect.intersects(clipBounds)) {
                if (cellRect.y == 0) {
                    cellRect.y = 1;
                }
                paintCell(g, cellRect, column);
                drawInvertedLShape(g, cellRect);
                if (column == 0) {
                    drawLeftLine(g, cellRect);
                }
            }

            cellRect.x += cellRect.width;
            column++;
        }
        if (draggedColumn != null) {
            if (draggedDistance == 0) {
                paintTable(g);
                return;
            }
            int draggedColumnIndex = viewIndexForColumn(draggedColumn);
            Rectangle draggedCellRect = table.getCellRect(0, draggedColumnIndex, true);
            draggedCellRect.y = 0;
            draggedCellRect.height = table.getSize().height - table.getRowCount() * table.getRowHeight();
            // Draw a gray well in place of the moving column.

            // g.fillRect(draggedCellRect.x, draggedCellRect.y,
            //       draggedCellRect.width, draggedCellRect.height);

            paintTable(g);
            g.setColor(table.getParent().getBackground());
            // g.setColor(Color.white);
            g.fillRect(draggedCellRect.x - 1, draggedCellRect.y,
                    draggedCellRect.width + 2, table.getHeight() - 1);
            g.setColor(table.getGridColor());
            g.drawLine(draggedCellRect.x, draggedCellRect.height, draggedCellRect.x + draggedCellRect.width,
                    draggedCellRect.height);
            g.drawRect(draggedCellRect.x, draggedCellRect.y,
                    draggedCellRect.width, table.getHeight() - 1);
            draggedCellRect.x += draggedDistance;

            // Fill the background.
            //    g.setColor(table.getBackground());
            paintCell(g, draggedCellRect, draggedColumnIndex);
            //drawInvertedLShape(g, draggedCellRect);
            //  g.setColor(new Color(100, 80, 80));


            paintSingleColumn(g, draggedColumnIndex);
            //paintCell(g, draggedCellRect, draggedColumnIndex);
            //  drawInvertedLShape(g, draggedCellRect);
            //table.getBackground());
            g.setColor(table.getGridColor());
            g.drawRect(draggedCellRect.x - draggedDistance, draggedCellRect.y,
                    draggedCellRect.width, draggedCellRect.height);
            g.drawRect(draggedCellRect.x, draggedCellRect.y,
                    draggedCellRect.width, table.getHeight() - 1);
        } else {
            paintTable(g);
        }

    }

    private void paintSingleColumn(Graphics g, int column) {
        Rectangle draggedCellRect = table.getCellRect(0, column, true);
        draggedCellRect.x += draggedDistance;
        int x = draggedCellRect.x;
        Dimension size = new Dimension(table.getSize().width, table.getSize().height - table.getRowCount() * table.getRowHeight());

        int tableWidth = size.width;//jTable.getWidth();
        int jTableYOrigin = size.height;
        int y = jTableYOrigin;
        for (int row = 0; row < table.getRowCount(); row++) {

            TableColumnModel cm = table.getColumnModel();
            g.setColor(table.getForeground());
            int w = cm.getColumn(column).getWidth();
            TableColumn tc = cm.getColumn(column);
            int index = viewIndexForColumn(tc);
            TableCellRenderer renderer = table.getCellRenderer(row, index);//etCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            Rectangle cellRect = new Rectangle();
            cellRect.x = x;
            cellRect.width = w - 1;
            if (x == 0) {
                cellRect.x = 2;
                cellRect.width = w - 3;
            }
            cellRect.y = y;

            cellRect.height = table.getRowHeight();// - 2;
            if (row + 1 == table.getRowCount()) {
                cellRect.height = cellRect.height - 1;
            }
            ((JLabel) comp).setBorder(BorderFactory.createEmptyBorder());
            rendererPane.add(comp);
            rendererPane.paintComponent(g, comp, table, cellRect.x, cellRect.y,
                    cellRect.width, cellRect.height, true);

            y += table.getRowHeight();
        }

    }

    private void paintTable(Graphics g) {
        JTableHeader header = table.getTableHeader();
        JTable jTable = header.getTable();
        TableColumnModel cm = jTable.getColumnModel();

        int s = 0;
        for (int column = 0; column < cm.getColumnCount(); column++) {
            ////System.out.println("Drawing rightside grid for column :" + column);
            int w = cm.getColumn(column).getWidth();
            s += w;

            // g.drawLine(x -1,jTableYOrigin, x -1,  jTableYOrigin + tableHeight - 1);
        }
        Dimension size = new Dimension(table.getSize().width, table.getSize().height - jTable.getRowCount() * jTable.getRowHeight());
        //    Dimension size = new Dimension(s, header.getSize().height - jTable.getRowCount() * jTable.getRowHeight());
        int tableWidth = size.width;//jTable.getWidth();
        int jTableYOrigin = size.height;
        int y = jTableYOrigin;
        int tableHeight = jTable.getRowHeight() * jTable.getRowCount();
        int x = 0;
        int leftMargin = 1;
        g.setColor(table.getGridColor());
        g.drawLine(leftMargin, y, tableWidth - 1, y);
        // for (int row = 0; row < jTable.getRowCount(); row++) {
        ////System.out.println("Drawing grid");
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
            ////System.out.println("Drawing rightside grid for column :" + column);
            int w = cm.getColumn(column).getWidth();
            x += w;
            g.setColor(table.getGridColor());
            g.drawLine(x - 1, jTableYOrigin, x - 1, jTableYOrigin + tableHeight - 1);
        }
        Rectangle cellRect = new Rectangle();
        y = jTableYOrigin;

        for (int row = 0; row < jTable.getRowCount(); row++) {
            x = 0;
            for (int column = 0; column < cm.getColumnCount(); column++) {
                g.setColor(table.getGridColor());
                ////System.out.println("Drawing rightside grid for column :" + column);
                int w = cm.getColumn(column).getWidth();
                TableColumn tc = cm.getColumn(column);
                int index = viewIndexForColumn(tc);
                TableCellRenderer renderer = jTable.getCellRenderer(row, index);//etCellRenderer(row, column);
                //   TableCellRenderer renderer = new SelectRenderer();// jTable.getCellRenderer(row, column);
//table.setSelectionModelSINGLE_SELECTION);
                //  Component comp = jTable.prepareRenderer(renderer, row, column);
                Component comp = renderer.getTableCellRendererComponent(table, table.getValueAt(row, index), (!table.isPaintingForPrint()
                        && selectedRow == row && selectedCol == column), false, row, column);
                //if (selectedRow == row && selectedCol == column){
                //  comp.setBackground(new Color(184, 207, 229));
                //}
                cellRect.x = x;
                cellRect.width = w - 1;
                if (x == 0) {
                    cellRect.x = 2;
                    cellRect.width = w - 3;
                }
                cellRect.y = y;

                cellRect.height = jTable.getRowHeight();// - 2;
                if (row + 1 == table.getRowCount()) {
                    cellRect.height = cellRect.height - 1;
                }
                if (selectedRow == row) {
                    ////System.out.println("true in caller");
                    //comp.setBackground(Color.green);
                }
                rendererPane.add(comp);
                rendererPane.paintComponent(g, comp, header, cellRect.x, cellRect.y,
                        cellRect.width, cellRect.height, true);
              
                x += w;

            }
            y += jTable.getRowHeight();
        }
        //      table.setRowSelectionAllowed(true);
        table.requestFocusInWindow();
    }

    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        JTableHeader header = table.getTableHeader();
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
                JTableHeader header = table.getTableHeader();
                JLabel headerLabel = new JLabel((String) value);
                headerLabel.setForeground(table.getTableHeader().getForeground());
                //   header.setBackground(table.getTableHeader().getBackground());
                headerLabel.setFont(table.getFont());
                headerLabel.setBackground(Color.white);
                headerLabel.setOpaque(true);
                if (!table.isPaintingForPrint() && isSelected) {
                   
                    headerLabel.setBackground(new Color(184, 207, 229));//184,g=207,b=229
                }

                headerLabel.setHorizontalAlignment(JLabel.LEFT);
                headerLabel.setVerticalAlignment(JLabel.CENTER);

                headerLabel.setText(value.toString());
                if (header instanceof GroupableTableHeader) {
                    headerLabel.setHorizontalAlignment(JLabel.LEFT);
                    headerLabel.setVerticalAlignment(JLabel.TOP);
                    headerLabel.setUI(new VerticalLabelUI(false));
                }
               
                return headerLabel;
            }
        };
        boolean selected = false;
        if (selectedPoint != null && cellRect.contains(selectedPoint)) {
            selected = true;
            groupSelected = false;//selectedRow == -1 && selectedCol == columnIndex
        }
        Component c = renderer.getTableCellRendererComponent(
                header.getTable(), aColumn.getHeaderValue(), selected, false, -1, columnIndex);

        // c.setBackground(UIManager.getColor("control"));

        rendererPane.add(c);
        //Make height 2 px less , to get lower margin
        rendererPane.paintComponent(g, c, table, cellRect.x, cellRect.y,
                cellRect.width, cellRect.height, true);
    }

    private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
        JTableHeader header = table.getTableHeader();
        TableCellRenderer renderer;//= cGroup.getHeaderRenderer();

        renderer = //new MyHeaderRenderer1(Color.green, header);
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JTableHeader header = table.getTableHeader();
                JLabel headerLabel = new JLabel((String) value);
                headerLabel.setForeground(table.getForeground());
                //   header.setBackground(table.getTableHeader().getBackground());
                headerLabel.setFont(table.getTableHeader().getFont());
                headerLabel.setBackground(Color.white);
                headerLabel.setOpaque(true);
                if (!table.isPaintingForPrint() && isSelected) {
                    //System.out.println("header label slected");
                    headerLabel.setBackground(new Color(184, 207, 229));
                }
                headerLabel.setHorizontalAlignment(JLabel.CENTER);
                headerLabel.setVerticalAlignment(JLabel.CENTER);
                headerLabel.setText(value.toString());
                if (header instanceof GroupableTableHeader) {
                    // headerLabel.setUI(new VerticalLabelUI(false));
                }
               
                return headerLabel;
            }
        };
     
        boolean selected = false;
        if (selectedPoint != null && cellRect.contains(selectedPoint)) {
            selected = true;
            groupSelected = true;
        }
        Component component = renderer.getTableCellRendererComponent(
                header.getTable(), cGroup.getHeaderValue(), selected, false, -1, -1);
        component.setFont(header.getFont());
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, table, cellRect.x, cellRect.y,
                cellRect.width - 1, cellRect.height, true);
    }

    private int getHeaderHeight() {
        JTableHeader header = table.getTableHeader();
        int cols = header.getColumnModel().getColumnCount();
        int h = 0;
        FontMetrics fm = header.getFontMetrics(header.getFont());
        for (int i = 0; i < cols; i++) {
            String[] names = header.getTable().getColumnName(i).split("<BR>");
            for (int j = 0; j < names.length; j++) {
                h = Math.max(h, fm.stringWidth(names[j]));
            }
        }
        return h + 30;
       
    }

    private Dimension createHeaderSize(long width) {
        JTableHeader header = table.getTableHeader();
        TableColumnModel columnModel = header.getColumnModel();
        width += columnModel.getColumnMargin() * columnModel.getColumnCount();
        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }
        return new Dimension((int) width, getHeaderHeight());
    }

    public Dimension getPreferredSize(JComponent c) {
        JTableHeader header = table.getTableHeader();
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            width = width + aColumn.getPreferredWidth();
        }
        return createHeaderSize(width);
    }
}

class SelectRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        JLabel lhis = new JLabel();
        lhis.setOpaque(true);
        lhis.setText(value.toString() + "x");
        if (isSelected) {
            //   //System.out.println("setting pink");

            lhis.setBackground(Color.green);
        } else {
            lhis.setBackground(Color.white);
        }

        return lhis;
    }
}
