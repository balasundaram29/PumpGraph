/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bala.table.tame;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


/*
 * (swing1.1beta3)
 *
 */
/**
 * ColumnGroup
 *
 * @version 1.0 10/20/98
 * @author Nobuo Tamemasa
 */
public class ColumnGroup {

    protected TableCellRenderer renderer;
    protected Vector v;
    protected String text;
    protected int margin = 0;
    private boolean paintedAlready = false;
    private int prefHeight = 22;
    private int minHeight = 15;
    private int maxHeight = 70;

    public int getHeight() {
        return prefHeight;
    }

    public void setPreferredHeight(int height) {

        prefHeight = height;
        if (height < minHeight) {
            prefHeight = minHeight;
        }
         if (height > maxHeight) {
            prefHeight = maxHeight;
        }
    }

    @Override
    public String toString() {
        return "Column Group" + this.text;
    }

    public ColumnGroup(String text) {
        this(null, text);
    }

    public ColumnGroup(TableCellRenderer renderer, String text) {
        if (renderer == null) {
            this.renderer = new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null) {
                        setForeground(Color.black);//header.getForeground());
                        setBackground(Color.white);//header.getBackground()
                        Font f = new Font("Monospaced 12", Font.PLAIN, 8);

                        setFont(new Font("Monospaced 12", Font.PLAIN, 8));//header.getFont());
                        setForeground(Color.black);
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    setText((value == null) ? "" : value.toString());
                    //  setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    // setBorder(BorderFactory.createLineBorder(Color.black));
                    //   setBorder(new CustomBorder(CustomBorder.TOP+CustomBorder.RIGHT,1.0f));
                    return this;
                }
            };
        } else {
            this.renderer = renderer;
        }
        this.text = text;
        v = new Vector();
    }

    /**
     * @param obj TableColumn or ColumnGroup
     */
    public void add(Object obj) {
        if (obj == null) {
            return;
        }
        v.addElement(obj);
    }

    /**
     * @param c TableColumn
     * @param v ColumnGroups
     */
    public Vector getColumnGroups(TableColumn c, Vector g) {
        g.addElement(this);
        if (v.contains(c)) {
            return g;
        }
        Enumeration e = v.elements();
        while (e.hasMoreElements()) {
            Object obj = e.nextElement();
            if (obj instanceof ColumnGroup) {
                Vector groups = (Vector) ((ColumnGroup) obj).getColumnGroups(c, (Vector) g.clone());
                if (groups != null) {
                    return groups;
                }
            }
        }
        return null;
    }

    public TableCellRenderer getHeaderRenderer() {
        return renderer;
    }

    public void setHeaderRenderer(TableCellRenderer renderer) {
        if (renderer != null) {
            this.renderer = renderer;
        }
    }

    public Object getHeaderValue() {
        return text;
    }

    public Dimension getSize(JTable table) {
        Component comp = renderer.getTableCellRendererComponent(
                table, getHeaderValue(), false, false, -1, -1);
        int height = prefHeight;//20;//comp.getPreferredSize().height;
        int width = 0;
        Enumeration e = v.elements();
        while (e.hasMoreElements()) {
            Object obj = e.nextElement();
            if (obj instanceof TableColumn) {
                TableColumn aColumn = (TableColumn) obj;
                width += aColumn.getWidth();
                // width += margin;
            } else {
                width += ((ColumnGroup) obj).getSize(table).width;
            }
        }
        return new Dimension(width, height);
    }

    public void setColumnMargin(int margin) {
        this.margin = margin;
        Enumeration e = v.elements();
        while (e.hasMoreElements()) {
            Object obj = e.nextElement();
            if (obj instanceof ColumnGroup) {
                ((ColumnGroup) obj).setColumnMargin(margin);
            }
        }
    }

    /**
     * @return the paintedAlready
     */
    public boolean isPaintedAlready() {
        return paintedAlready;
    }

    /**
     * @param paintedAlready the paintedAlready to set
     */
    public void setPaintedAlready(boolean paintedAlready) {
        this.paintedAlready = paintedAlready;
    }
}

class MyHeaderRenderer1 extends JLabel implements
        TableCellRenderer, Serializable {

    String text = null;
    int row = 0;
    int column = 0;
    int h = 0;

    public MyHeaderRenderer1(Color gridColorIn, JTableHeader header) {
        this.setFont(header.getFont());
    }

    public MyHeaderRenderer1(Color gridColorIn, int row, int column, String text) {
        //  this.setBorder(BorderFactory.createLineBorder(gridColorIn, 1));
        this.row = row;
        this.column = column;
        this.setFont(new Font("Monospaced 12", Font.PLAIN, 8));
        this.text = text;
        //this.setAlignmentY(70);
    }

    public void paintComponent(Graphics g) {
        // super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.red);
        //   g2D.drawString("ok"+text,10,10);
        AffineTransform transform = new AffineTransform();
        transform.setToRotation(-Math.PI / 2.0);
        Font derivedFont = g2D.getFont().deriveFont(transform);
        g2D.setFont(derivedFont);
        FontMetrics fm = g2D.getFontMetrics();
        // g2D.rotate(-Math.PI/2.0);
        //g2D.drawOval(25, 25, 10, 10);
        String[] sarray = this.text.split("<BR>");
        float offset = this.getWidth() / 4;//fm.getHeight();//float)(this.getWidth()/3.0);
        for (int i = 0; i < sarray.length; i++) {
            String s = sarray[i].replace("<HTML>", "");
            s = s.replace("</HTML>", "");
            g2D.drawString(s, offset * (i + 1), this.getHeight() - 2);

        }
        //g2D.drawRect(0, 0, getWidth() , getHeight() );

    }

    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {
        this.text = value.toString();
        this.row = row;
        this.column = column;
        this.setText(value.toString());
        this.setPreferredSize(new Dimension(10, h));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        //  this.setBorder(new CustomBorder(CustomBorder.RIGHT, 1.0f));
        return this;
    }
}
