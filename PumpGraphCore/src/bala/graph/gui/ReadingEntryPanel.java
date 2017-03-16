package bala.graph.gui;

import bala.graph.persistence.v1.EntryValues;
import bala.graph.graph.*;
import bala.graph.persistence.DirectoryPathPersistence;
import bala.graph.persistence.v1.TypeValues;
import bala.graph.persistence.TypeValuesInputStream;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.miginfocom.swing.MigLayout;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bala
 */
public final class ReadingEntryPanel extends JPanel implements Serializable {

    JPopupMenu popup = new JPopupMenu();
    JMenuItem openType = new JMenuItem("Open Pump Type");
    JMenuItem saveType = new JMenuItem("Save Pump Type");

    /**
     * @return the minBoreSizeField
     */
    public JTextField getMinBoreSizeField() {
        return minBoreSizeField;
    }

    class OpenTypeMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            {
                try {
                    JFileChooser chooser = null;// new JFileChooser();
                    if (ReadingEntryPanel.this.getIsRefField().getText().equalsIgnoreCase("IS 9079")) {
                        chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("MBTypeDir"));
                    } else {
                        chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("OWSTypeDir"));
                    }
                    chooser.showOpenDialog(null);
                    if (chooser.getSelectedFile() == null) {
                        return;
                    }

                    ObjectInputStream is = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
                    ReadingEntryPanel.this.useSavedTypeValues((bala.graph.persistence.v1.TypeValues) is.readObject());
                    if (ReadingEntryPanel.this.getIsRefField().getText().equalsIgnoreCase("IS 9079")) {
                        DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "MBTypeDir");
                    } else {
                        DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "OWSTypeDir");
                    }
                    //OpeningFrame.this.saveTypeDirectory(chooser.getCurrentDirectory());
                    // OpeningFrame.this.setTitle(appConstants.SOFTWARE_TITLE + "  :  " + chooser.getSelectedFile().getName());
                    // openFile = chooser.getSelectedFile();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    class SaveTypeMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser chooser = new JFileChooser();
                if (ReadingEntryPanel.this.getIsRefField().getText().equalsIgnoreCase("IS 9079")) {
                    chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("MBTypeDir"));
                } else {
                    chooser = new JFileChooser(DirectoryPathPersistence.getFileDirectory("OWSTypeDir"));
                }
                chooser.showSaveDialog(null);
                if (chooser.getSelectedFile() == null) {
                    return;
                }

                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));

                os.writeObject(new bala.graph.persistence.TypeValues(ReadingEntryPanel.this));

                if (ReadingEntryPanel.this.getIsRefField().getText().equalsIgnoreCase("IS 9079")) {
                    DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "MBTypeDir");
                } else {
                    DirectoryPathPersistence.saveFileDirectory(chooser.getCurrentDirectory(), "OWSTypeDir");
                }
                // OpeningFrame.this.saveTypeDirectory(chooser.getCurrentDirectory());
                //   openFile = chooser.getSelectedFile();
                // OpeningFrame.this.setTitle(appConstants.SOFTWARE_TITLE + "  :  " + openFile.getName());
            } catch (Exception ex) {
                ex.printStackTrace();

                //}
                JOptionPane.showMessageDialog(null, "Cannot save . Please check filename");
            }

        }
    }

    class PopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }

    private static class NumericTableModel extends DefaultTableModel {

        public NumericTableModel() {
        }

        @Override
        public Class getColumnClass(int col) {
            return Double.class;

        }
    }
    private GridLinesType gridLinesType;
    private double desiredSmoothnessPercentage;
    protected JTextField slNoField;
    private JTextField ipNoField;
    protected JTextField pumpTypeField;
    private JTextField ratingField;
    private JTextField headField;
    private JTextField dischField;
    private JTextField effField;
    private JTextField currField;
    private JTextField voltField;
    private JTextField phaseField;
    private JTextField freqField;
    private JTextField hRangeLwrField;
    private JTextField hRangeUprField;
    private JTextField suctionSizeField;
    private JTextField delSizeField;
    private JTextField stagesField;
    private JTextField minBoreSizeField;
    private JTextField gaugDistField;
    private JTextField isRefField;
    private JTextField remarksField;
    //  private JTextField dateStringField;
    JTable table;
    JPanel fieldsPanel;
    protected JDateChooser dateChooser;
    static final long serialVersionUID = -1881500357011727315L;
    private JTextField dischMaxForScaleField;
    private JTextField headMaxForScaleField;
    private JTextField effMaxForScaleField;
    private JTextField currMaxForScaleField;
    public AppConstants appConstants;

    public ReadingEntryPanel(AppConstants appConstants) {
        this.setLayout(new MigLayout(
                "",
                "[][grow]50[][grow]",
                ""));
        this.appConstants = appConstants;
        this.addFieldsPanel();
        setBackground(new Color(236, 233, 216));
        JLabel text = new JLabel("Maximum axis   values  to  use ;    Leave as  0 for  auto-scale  : ");
        this.add(text, "span");
        this.addScalePanel();
        this.addTable();
        popup.add(openType);
        popup.add(saveType);
        MouseListener popupListener = new PopupListener();
        pumpTypeField.addMouseListener(popupListener);
        openType.addActionListener(new OpenTypeMenuListener());
        saveType.addActionListener(new SaveTypeMenuListener());
    }

    public void addFieldsPanel() {
        InputVerifier v = new NumericVerifier();
        fieldsPanel = new JPanel();
        JLabel slNoLabel = new JLabel("Sl. No : ");
        this.add(slNoLabel);
        slNoField = new JTextField(" ");
        this.add(slNoField, "grow");
        JLabel dateLabel = new JLabel("Date : ");
        this.add(dateLabel);
        dateChooser = new JDateChooser(new Date(), "dd-MM-yyyy");

        this.add(dateChooser, "grow,wrap");
        JLabel ipNoLabel = new JLabel("Inpass No : ");
        this.add(ipNoLabel);
        ipNoField = new JTextField("");
        this.add(ipNoField, "grow");
        JLabel pumpTypeLabel = new JLabel("Pump Type : ");
        this.add(pumpTypeLabel);
        pumpTypeField = new JTextField("");
        this.add(pumpTypeField, "grow,wrap");
        JLabel ratingLabel = new JLabel("Motor Rating(kW/HP) :   ");
        this.add(ratingLabel);
        ratingField = new JTextField(" ");
        this.add(ratingField, "grow");
        JLabel headLabel = new JLabel("Head (m) : ");
        this.add(headLabel);
        headField = new JTextField("");
        headField.setInputVerifier(v);
        this.add(headField, "grow,wrap");
        JLabel dischLabel = new JLabel("Discharge (lps) : ");
        this.add(dischLabel);
        dischField = new JTextField("");
        dischField.setInputVerifier(v);
        this.add(dischField, "grow");
        JLabel effLabel = new JLabel("Overall Eff. (%) : ");
        this.add(effLabel);
        effField = new JTextField("");
        effField.setInputVerifier(v);
        this.add(effField, "grow,wrap");
        JLabel currLabel = new JLabel("Max.current(A) : ");
        this.add(currLabel);
        currField = new JTextField("");
        currField.setInputVerifier(v);
        this.add(currField, "grow");
        JLabel voltLabel = new JLabel("Voltage(V)  : ");
        this.add(voltLabel);
        voltField = new JTextField("");
        voltField.setInputVerifier(v);
        this.add(voltField, "grow,wrap");
        JLabel phaseLabel = new JLabel("Phase  : ");
        this.add(phaseLabel);
        phaseField = new JTextField(" 1 ");
        phaseField.setInputVerifier(v);
        this.add(phaseField, "grow");
        JLabel freqLabel = new JLabel("Frequency(Hz)  : ");
        this.add(freqLabel);
        freqField = new JTextField("50.0");
        freqField.setInputVerifier(v);
        this.add(freqField, "grow,wrap");
        JLabel hRangeLwrLabel = new JLabel("Head Range Lower(m) : ");
        this.add(hRangeLwrLabel);
        hRangeLwrField = new JTextField(" ");
        hRangeLwrField.setInputVerifier(v);
        this.add(hRangeLwrField, "grow");
        JLabel hRangeUprLabel = new JLabel("Head Range Upper(m)  : ");
        this.add(hRangeUprLabel);
        hRangeUprField = new JTextField(" ");
        hRangeUprField.setInputVerifier(v);
        this.add(hRangeUprField, "grow,wrap");
        if (appConstants.standard == IndianStandard.IS9079) {
            JLabel suctionSizeLabel = new JLabel("Suction size(mm) : ");
            this.add(suctionSizeLabel);
            suctionSizeField = new JTextField("");
            suctionSizeField.setInputVerifier(v);
            this.add(suctionSizeField, "grow");
        }
        JLabel delSizeLabel = new JLabel("Delivery size(mm) : ");
        this.add(delSizeLabel);
        delSizeField = new JTextField("25");
        delSizeField.setInputVerifier(v);
        if (appConstants.standard == IndianStandard.IS9079) {
            this.add(delSizeField, "grow,wrap");
        } else {
            this.add(delSizeField, "grow");
        }
        JLabel stagesLabel = new JLabel("No. of stages : ");
        this.add(stagesLabel, "grow");
        stagesField = new JTextField("1");
        stagesField.setInputVerifier(v);
        this.add(stagesField, "grow,wrap");
        if (appConstants.standard == IndianStandard.IS8034) {
            JLabel minBoreSizeLabel = new JLabel("Minimum Bore Size(mm) :  ");
            this.add(minBoreSizeLabel);
            minBoreSizeField = new JTextField(" ");
            minBoreSizeField.setInputVerifier(v);
            this.add(minBoreSizeField, "grow,wrap");
        }
         JLabel gaugDistLabel = new JLabel("Gauge Distance(m)  : ");
        this.add(gaugDistLabel);
        gaugDistField = new JTextField(" ");
        gaugDistField.setInputVerifier(v);
        this.add(gaugDistField, "grow");
       
        JLabel isRefLabel = new JLabel("IS Ref  : ");
        this.add(isRefLabel);
        isRefField = new JTextField("");
        if (appConstants.standard == IndianStandard.IS9079) {
            isRefField.setText("IS 9079");
        } else if (appConstants.standard == IndianStandard.IS14220) {
            isRefField.setText("IS 14220");
        } else {
            isRefField.setText("IS 8034");
        }
        isRefField.setEditable(false);
        this.add(isRefField, "grow,wrap");

        JLabel remarksLabel = new JLabel("Remarks  : ");
        this.add(remarksLabel);
        remarksField = new JTextField(" ");
        this.add(remarksField, "grow,wrap");
        this.setVisible(true);
        setVisible(true);

    }

    public void addScalePanel() {
        InputVerifier v = new NumericVerifier();
        JPanel scalePanel = new JPanel();
        scalePanel.setLayout(new MigLayout("", "[][grow][][grow]", ""));
        scalePanel.setBackground(new Color(236, 233, 216));
        JLabel dischMaxLabel = new JLabel("Max.Disch(lps) : ");
        scalePanel.add(dischMaxLabel);
        dischMaxForScaleField = new JTextField("0.0");
        dischMaxForScaleField.setInputVerifier(v);
        scalePanel.add(dischMaxForScaleField, "grow");

        JLabel headMaxLabel = new JLabel("Max.Head(m) : ");
        scalePanel.add(headMaxLabel);
        headMaxForScaleField = new JTextField("0.0");
        headMaxForScaleField.setInputVerifier(v);
        scalePanel.add(headMaxForScaleField, "grow,wrap");

        JLabel EffMaxLabel = new JLabel("Max.Eff(%) : ");
        scalePanel.add(EffMaxLabel);
        effMaxForScaleField = new JTextField("0.0");
        effMaxForScaleField.setInputVerifier(v);
        scalePanel.add(effMaxForScaleField, "grow");
        JLabel currMaxLabel = new JLabel("Max. Curr(A) : ");
        scalePanel.add(currMaxLabel);
        currMaxForScaleField = new JTextField("0.0");
        currMaxForScaleField.setInputVerifier(v);
        scalePanel.add(currMaxForScaleField, "grow,wrap");
        add(scalePanel, "span,grow,wrap");
        scalePanel.setVisible(true);
        setVisible(true);
    }

    public void addTable() {
        NumericTableModel model = new NumericTableModel();
        table = new JTable(model);
        table.setBackground(new Color(236, 233, 216));
        model.addColumn("No.");
        model.addColumn("Frequency in Hz");
        if (appConstants.standard == IndianStandard.IS9079) {
            model.addColumn("<HTML>Suction Gauge<BR> Reading in mmHg</HTML>");
        }
        model.addColumn("<HTML>Delivery Gauge<BR>Reading in m</HTML>");
        model.addColumn("Discharge in lps");
        model.addColumn("Voltage in V");
        model.addColumn("Current in A");
        model.addColumn("Power in kW");
        table.getTableHeader().setPreferredSize(new Dimension(table.getColumnModel().getTotalColumnWidth(), 40));
        table.setRowHeight(24);
        table.setRowMargin(3);
        for (int k = 0; k < appConstants.MAX_ROWS_COUNT; k++) {
            Object[] rowData = null;
            if (appConstants.standard == IndianStandard.IS9079) {
                rowData = new Object[]{k + 1, " ", " ", " ", " ", " ", " ", " ", " "};
            } else {
                rowData = new Object[]{k + 1, " ", " ", " ", " ", " ", " ", " "};
            }
            model.addRow(rowData);
        }
        Object[] rowData9;//= {"Multiplication Factor", "1", "1 ", "1 ", "1 ", "1 ", "1 ", "0.001 "};
        if (appConstants.standard == IndianStandard.IS9079) {
            rowData9 = new Object[]{"Multiplication Factor", "1", "1 ", "1 ", "1 ", "1 ", "1 ", "1 "};//{k," ", " ", " ", " ", " ", " ", " "};
        } else {
            rowData9 = new Object[]{"Multiplication Factor", "1 ", "1 ", "1 ", "1 ", "1 ", "1"};// {k, " ", " ", " ", " ", " ", " "};
        }
        model.addRow(rowData9);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new RPStatusColumnCellRenderer());
        }
        table.setPreferredScrollableViewportSize(new Dimension(getWidth(), table.getRowCount() * table.getRowHeight()));
        JScrollPane tScroller = new JScrollPane(table);
        add(tScroller, "span,grow,wrap");
        setVisible(true);
    }

    public void useSavedValues(EntryValues values) {
        this.slNoField.setText(values.getSlNoFieldString());
        ipNoField.setText(values.getIpNoFieldString());
        pumpTypeField.setText(values.getPumpTypeFieldString());
        ratingField.setText(values.getRatingFieldString());
        headField.setText(values.getHeadFieldString());
        dischField.setText(values.getDischFieldString());
        effField.setText(values.getEffFieldString());
        currField.setText(values.getCurrFieldString());
        voltField.setText(values.getVoltFieldString());
        phaseField.setText(values.getPhaseFieldString());
        freqField.setText(values.getFreqFieldString());
        hRangeLwrField.setText(values.gethRangeLwrFieldString());
        hRangeUprField.setText(values.gethRangeUprFieldString());
        delSizeField.setText(values.getDelSizeFieldString());
        if (appConstants.standard == IndianStandard.IS9079) {
            suctionSizeField.setText(values.getSuctionSizeFieldString());
        }
          stagesField.setText(values.getStagesString());
         if (appConstants.standard == IndianStandard.IS8034) {
         
           minBoreSizeField.setText(values.getMinBoreSizeString());
        }
        gaugDistField.setText(values.getGaugDistFieldString());
        isRefField.setText(values.getIsRefFieldString());
        remarksField.setText(values.getRemarksFieldString());
        dateChooser.setDate(values.getDateFieldDate());
        int vRows = values.getTableValueStrings().length;
        int vCols = 0;//default
        if (vRows != 0) {
            vCols = values.getTableValueStrings()[0].length;
        }
        String[][] tableValues = values.getTableValueStrings();
        for (int i = 0; i < vRows; i++) {
            if (i >= table.getRowCount()) {
                break;
            }
            for (int j = 0; j < vCols; j++) {

                table.setValueAt(tableValues[i][j], i, j);
            }
        }

    }

    public void useSavedTypeValues(TypeValues values) {

        pumpTypeField.setText(values.getPumpTypeFieldString());
        ratingField.setText(values.getRatingFieldString());
        headField.setText(values.getHeadFieldString());
        dischField.setText(values.getDischFieldString());
        effField.setText(values.getEffFieldString());
        currField.setText(values.getCurrFieldString());
        voltField.setText(values.getVoltFieldString());
        phaseField.setText(values.getPhaseFieldString());
        freqField.setText(values.getFreqFieldString());
        hRangeLwrField.setText(values.gethRangeLwrFieldString());
        hRangeUprField.setText(values.gethRangeUprFieldString());
        delSizeField.setText(values.getDelSizeFieldString());
        stagesField.setText(values.getStagesString());
        if (appConstants.standard == IndianStandard.IS9079) {
            suctionSizeField.setText(values.getSuctionSizeFieldString());
            
        }
         if (appConstants.standard == IndianStandard.IS8034) {
         
           minBoreSizeField.setText(values.getMinBoreSizeString());
        }
        isRefField.setText(values.getIsRefFieldString());

    }

    /**
     * @return the slNoField
     */
    public JTextField getSlNoField() {
        return slNoField;
    }

    /**
     * @return the ipNoField
     */
    public JTextField getIpNoField() {
        return ipNoField;
    }

    /**
     * @return the pumpTypeField
     */
    public JTextField getPumpTypeField() {
        String type = pumpTypeField.getText();
        pumpTypeField.setText(type.replace(" ", ""));
        return pumpTypeField;
    }

    /**
     * @return the ratingField
     */
    public JTextField getRatingField() {
        return ratingField;
    }

    /**
     * @return the headField
     */
    public JTextField getHeadField() {
        return headField;
    }

    /**
     * @return the dischField
     */
    public JTextField getDischField() {
        return dischField;
    }

    /**
     * @return the effField
     */
    public JTextField getEffField() {
        return effField;
    }

    /**
     * @return the currField
     */
    public JTextField getCurrField() {
        return currField;
    }

    /**
     * @return the voltField
     */
    public JTextField getVoltField() {
        return voltField;
    }

    /**
     * @return the phaseField
     */
    public JTextField getPhaseField() {
        return phaseField;
    }

    /**
     * @return the freqField
     */
    public JTextField getFreqField() {
        return freqField;
    }

    /**
     * @return the hRangeLwrField
     */
    public JTextField gethRangeLwrField() {
        return hRangeLwrField;
    }

    /**
     * @return the hRangeUprField
     */
    public JTextField gethRangeUprField() {
        return hRangeUprField;
    }

    /**
     * @return the delSizeField
     */
    public JTextField getDelSizeField() {
        return delSizeField;
    }

    public JTextField getSuctionSizeField() {
        return suctionSizeField;
    }

    public JTextField getStagesField() {
        return stagesField;
    }

    /**
     * @return the gaugDistField
     */
    public JTextField getGaugDistField() {
        return gaugDistField;
    }

    /**
     * @return the isRefField
     */
    public JTextField getIsRefField() {
        return isRefField;
    }

    /**
     * @return the remarksField
     */
    public JTextField getRemarksField() {
        return remarksField;
    }
    // public JTextField getDatestringField() {
    //     return dateStringField;
    // }

    /**
     * @return the table
     */
    public JTable getTable() {
        return table;
    }

    /**
     * @return the dateChooser
     */
    public JDateChooser getDateChooser() {
        return dateChooser;
    }

    /**
     * @return the dischMaxForScaleField
     */
    public JTextField getDischMaxForScaleField() {
        return dischMaxForScaleField;
    }

    /**
     * @return the headMaxForScaleField
     */
    public JTextField getHeadMaxForScaleField() {
        return headMaxForScaleField;
    }

    /**
     * @return the effMaxForScaleField
     */
    public JTextField getEffMaxForScaleField() {
        return effMaxForScaleField;
    }

    /**
     * @return the currMaxForScaleField
     */
    public JTextField getCurrMaxForScaleField() {
        return currMaxForScaleField;
    }

    /**
     * @return the gridLinesType
     */
    public GridLinesType getGridLinesType() {
        return gridLinesType;
    }

    /**
     * @param gridLinesType the gridLinesType to set
     */
    public void setGridLinesType(GridLinesType gridLinesType) {
        this.gridLinesType = gridLinesType;
    }

    /**
     * @return the desiredSmoothnessPercentage
     */
    public double getDesiredSmoothnessPercentage() {
        return desiredSmoothnessPercentage;
    }

    /**
     * @param desiredSmoothnessPercentage the desiredSmoothnessPercentage to set
     */
    public void setDesiredSmoothnessPercentage(double desiredSmoothnessPercentage) {
        this.desiredSmoothnessPercentage = desiredSmoothnessPercentage;
    }

    class NumericVerifier extends InputVerifier {

        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;
            try {
                double ok = Double.parseDouble(tf.getText());

                return true;
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(ReadingEntryPanel.this, "Here , you can enter numbers only!");
            return false;
        }
    }
}

class RPStatusColumnCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        //Cells are by default rendered as a JLabel.
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        //Get the status for the current row.
        //CustomTableModel tableModel = (CustomTableModel) table.getModel();
        // if (tableModel.getStatus(row) == CustomTableModel.APPROVED) {
        if (isSelected && hasFocus) {
            l.setBackground(Color.GREEN);
            l.setForeground(Color.black);
        } else {
            l.setBackground(Color.CYAN);
            l.setForeground(Color.black);
        }
        //} else {
        // l.setBackground(Color.RED);
        // }

        //Return the JLabel which renders the cell.
        return l;

    }
}
