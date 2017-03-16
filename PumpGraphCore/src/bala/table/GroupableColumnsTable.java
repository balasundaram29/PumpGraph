package bala.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ANNAIENG
 */
public class GroupableColumnsTable extends JComponent {

    private Alignment alignment;
    private ArrayList<ColumnGroup> groupList = new ArrayList<ColumnGroup>();
    private JTable jTable;
    private JTableHeader header;
    //  private final Object[] columnNames;

    public void addColumnGroup(ColumnGroup group) throws Exception {
        int x = 0;

        ArrayList<Integer> colList = group.getColList();
        int totalCols = colList.size();
        if (totalCols <= 1) {
            if (totalCols == 0) {
                return;//return 0;
            }
            groupList.add(group);
        }
        int cols[] = new int[colList.size()];
        for (int j = 0; j < totalCols; j++) {
            cols[j] = colList.get(j);
        }
        for (int col = 0; col < totalCols - 1; col++) {
            if (cols[col] != cols[col + 1] - 1) {
                throw new Exception("Merged Columns Should Contain Only Adjacent Columns");
            }
        }
        groupList.add(group);
    }

    public GroupableColumnsTable(final AbstractTableModel model) {
        this.alignment = Alignment.Vertical;
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        jTable = new JTable(model) {

            @Override
            public void columnMarginChanged(ChangeEvent e) {
                //     super.columnMarginChanged(e); //To change body of generated methods, choose Tools | Templates.

                repaint();
                getTableHeader().repaint();
            }

            protected JTableHeader createDefaultTableHeader() {
                return new GroupableColumnHeader(columnModel, groupList);
            }
        };

        jTable.setPreferredSize(new Dimension(this.getWidth(),
                jTable.getRowCount() * (jTable.getRowHeight())));
       // jTable.setMinimumSize(new Dimension(1100, jTable.getRowCount() * jTable.getRowHeight()));
        // jTable.setMaximumSize(new Dimension(1100, jTable.getRowCount() * jTable.getRowHeight()));

        // jTable.setShowGrid(true);
        jTable.setGridColor(Color.black);
        go();
    }

    public GroupableColumnsTable(final Object[][] rowData, final Object[] columnNames) {
        this(new AbstractTableModel() {
            public String getColumnName(int column) {
                return columnNames[column].toString();
            }

            public int getRowCount() {
                return rowData.length;
            }

            public int getColumnCount() {
                return columnNames.length;
            }

            public Object getValueAt(int row, int col) {
                return rowData[row][col];
            }

            public boolean isCellEditable(int row, int column) {
                return true;
            }

            public void setValueAt(Object value, int row, int col) {
                rowData[row][col] = value;
                fireTableCellUpdated(row, col);
            }
        });
    }

    public void go() {

        header = jTable.getTableHeader();
        //header.setMinimumSize(new Dimension(1000, 380));
        header.setPreferredSize(new Dimension(this.getWidth(), 390));
        // header.setMaximumSize(new Dimension(1100, 400));//this.getWidth()
        jTable.setFont(new Font("Monospaced 12", Font.PLAIN, 12));
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(jTable, BorderLayout.SOUTH);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int) header.getPreferredSize().getWidth(),
                (int) header.getPreferredSize().getHeight() + (int) jTable.getPreferredSize().getHeight());
    }

    // @Override
    public void setFont(Font f) {
        if (jTable != null) {
            jTable.setFont(f);
            // updateRowHeights();

        }
        if (header != null) {
            header.setFont(f);
        }
    }

    public void setMergedAreaHeight(int mergedAreaHeight) {
        if (groupList != null && groupList.size() != 0) {
            for (ColumnGroup group : groupList) {
                group.setMergedAreaHeight(mergedAreaHeight);
            }
        }
    }

    public static void main(String[] args) {
        try {
            int cols = 8;
            int rows = 6;
            Object[] colNames = new String[cols];
            Object[][] dataObject = new String[rows][cols];
            for (int col = 0; col < cols; col++) {
                colNames[col] = "Column " + (col);
                for (int row = 0; row < rows; row++) {
                    dataObject[row][col] = "row" + row + "," + "col" + col;
                }
            }
            GroupableColumnsTable t = new GroupableColumnsTable(dataObject, colNames);
            ColumnGroup group = new ColumnGroup();
            group.addColumn(1);
            group.addColumn(2);
            group.setText("HEAD, mWC");
            group.addColumn(3);
            t.addColumnGroup(group);
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            MinGridTableUI ui1 = new MinGridTableUI();
            ui1.setMinGrid(true);

            t.jTable.setUI(ui1);
            //   jTable.updateUI();
            t.jTable.setShowGrid(false);
            t.jTable.setShowVerticalLines(true);

            //t.setFont(new Font("Monospaced 12", Font.PLAIN, 14));
            //    panel.add(t.header);
            panel.add(t);//.jTable);
            //panel.add(t.header,BorderLayout.NORTH);
            //panel.add(t.jTable,BorderLayout.SOUTH);
            JFrame f = new JFrame("Table - Groupable Columns");
            f.setContentPane(panel);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(t.getPreferredSize());
            //  f.pack();
            f.setVisible(true);
            f.setAlwaysOnTop(true);
        } catch (Exception ex) {
            Logger.getLogger(GroupableColumnsTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the jTable
     */
    public JTable getjTable() {
        return jTable;
    }

    public int getXLeft(TableColumnModel model, int column) {
        int x = 0;
        for (int col = 0; col < column; col++) {
            x = x + model.getColumn(col).getWidth();
        }
        return x;
    }

    public int getMergedWidth(TableColumnModel model, ColumnGroup group) {
        int x = 0;

        ArrayList<Integer> colList = group.getColList();
        int totalCols = colList.size();
        if (totalCols == 0) {
            return 0;
        }
        int cols[] = new int[colList.size()];
        for (int j = 0; j < totalCols; j++) {
            cols[j] = colList.get(j);
        }

        int w = 0;
        for (int col = 0; col < totalCols; col++) {
            w = w + model.getColumn(cols[col]).getWidth();
        }
        return w;
    }

    /**
     * @return the header
     */
    public JTableHeader getHeader() {
        return header;
    }

    /**
     * @param alignment the alignment to set
     */
    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
        ((GroupableColumnHeader) this.jTable.getTableHeader()).setAligment(alignment);
    }
}

class GroupableColumnHeader extends JTableHeader {

    private ArrayList<ColumnGroup> groupList = new ArrayList<ColumnGroup>();
    private Alignment alignment;

    public GroupableColumnHeader(TableColumnModel model, ArrayList<ColumnGroup> groupList) {
        this.groupList = groupList;
        this.columnModel = model;
    }

    public int getXLeft(TableColumnModel model, int column) {
        int x = 0;
        for (int col = 0; col < column; col++) {
            x = x + model.getColumn(col).getWidth();
        }
        return x;
    }

    public int getMergedWidth(TableColumnModel model, ColumnGroup group) {
        int x = 0;

        ArrayList<Integer> colList = group.getColList();
        int totalCols = colList.size();
        if (totalCols == 0) {
            return 0;
        }
        int cols[] = new int[colList.size()];
        for (int j = 0; j < totalCols; j++) {
            cols[j] = colList.get(j);
        }

        int w = 0;
        for (int col = 0; col < totalCols; col++) {
            w = w + model.getColumn(cols[col]).getWidth();
        }
        return w;
    }

    @Override
    public void paintComponent(Graphics g) {
        int topMargin = 2;
        int leftMargin = 1;
        //   setColumnModel(jTable.getColumnModel());
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.white);
        g2D.fillRect(0, 0, getWidth(), getHeight());
        g2D.setColor(Color.black);
        int x = 0;
        Font horFont = g2D.getFont();
        g2D.drawLine(leftMargin, topMargin, leftMargin, getHeight());

        for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
            System.out.println("Colum No is " + i);
            if (i == 0) {
                g2D.drawLine(x + leftMargin, topMargin, x + getColumnModel().getColumn(i).getWidth() - 1, topMargin);
            } else {
                g2D.drawLine(x, topMargin, x + getColumnModel().getColumn(i).getWidth() - 1, topMargin);
            }
            //don't draw last column;drawn in enclosing rectangle
            //if (i != getColumnModel().getColumnCount() - 1) {
            g2D.drawLine(x + getColumnModel().getColumn(i).getWidth() - 1, topMargin, x + getColumnModel().getColumn(i).getWidth() - 1, getHeight());
            //}
            AffineTransform transform = new AffineTransform();
            transform.setToRotation(-Math.PI / 2.0);
            Font derivedFont = g2D.getFont().deriveFont(transform);
            float offsetInc = getHeight() / 4;
            if (alignment == Alignment.Vertical) {
                g2D.setFont(derivedFont);
                offsetInc = getColumnModel().getColumn(i).getWidth() / 4;
            }
            FontMetrics fm = g2D.getFontMetrics();
            String[] sarray = columnModel.getColumn(i).getHeaderValue().toString().split("<BR>");
            float offset = 13;//fm.stringWidth("hsfjkfh");//getHeight();//float)(this.getWidth()/3.0);
            if (alignment == Alignment.Horizontal) {
                offset = getHeight() / 2 + fm.getHeight() / 2;
            }
            for (int j = 0; j < sarray.length; j++) {
                String s = sarray[j].replace("<HTML>", "");
                s = s.replace("</HTML>", "");
                if (alignment == Alignment.Vertical) {
                    g2D.drawString(s, x + offset, this.getHeight() - 2);
                } else {
                    g2D.drawString(s, x, offset);
                }
                offset = offset + offsetInc;
            }
            x = x + getColumnModel().getColumn(i).getWidth();
        }
        g2D.setFont(horFont);
        FontMetrics fontMetrics = g2D.getFontMetrics();
        int len = 0;
        int w = 0;
        for (int gr = 0; gr < groupList.size(); gr++) {
            ColumnGroup group = groupList.get(gr);
            int leftCol = group.getLeftMostColumnIndex();
            int rightCol = group.getRightMostColumnIndex();
            x = getXLeft(columnModel, leftCol);
            w = getMergedWidth(columnModel, group);
            g2D.setColor(Color.white);
            int h = group.getMergedAreaHeight();
            g2D.clipRect(x - 1, topMargin, w, h + 1);
            g2D.fillRect(x - 1, topMargin, w, h);
            g2D.setColor(Color.black);
            g2D.drawRect(x - 1, topMargin, w, h);
            String text = group.getText();
            len = fontMetrics.stringWidth(text);
            int fontHeight = fontMetrics.getHeight();
            g2D.drawString(text, x + w / 2 - len / 2, h / 2 + fontHeight / 2);
            g2D.setClip(null);
        }
    }

    void setAligment(Alignment alignment) {
        this.alignment = alignment;
    }
}
