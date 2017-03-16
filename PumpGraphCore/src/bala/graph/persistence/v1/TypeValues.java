//mb
package bala.graph.persistence.v1;

import bala.graph.gui.ReadingEntryPanel;
import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;
import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class TypeValues implements Serializable {

    private int stdNo;
    private String pumpTypeFieldString;
    private String ratingFieldString;
    private String headFieldString;
    private String dischFieldString;
    private String effFieldString;
    private String currFieldString;
    private String voltFieldString;
    private String phaseFieldString;
    private String freqFieldString;
    private String hRangeLwrFieldString;
    private String hRangeUprFieldString;
    private String delSizeFieldString;
    private String suctionSizeFieldString = "";
    private String isRefFieldString;
    private String stagesString;
    private String minBoreSizeString;

    public TypeValues(ReadingEntryPanel entryPanel) {
        this.pumpTypeFieldString = entryPanel.getPumpTypeField().getText();
        this.ratingFieldString = entryPanel.getRatingField().getText();
        this.headFieldString = entryPanel.getHeadField().getText();
        this.dischFieldString = entryPanel.getDischField().getText();
        this.effFieldString = entryPanel.getEffField().getText();
        this.currFieldString = entryPanel.getCurrField().getText();
        this.voltFieldString = entryPanel.getVoltField().getText();
        this.phaseFieldString = entryPanel.getPhaseField().getText();
        this.freqFieldString = entryPanel.getFreqField().getText();
        this.hRangeLwrFieldString = entryPanel.gethRangeLwrField().getText();
        this.hRangeUprFieldString = entryPanel.gethRangeUprField().getText();
        this.delSizeFieldString = entryPanel.getDelSizeField().getText();
        this.stagesString = entryPanel.getStagesField().getText();
        if (AppConstants.standard == IndianStandard.IS8034) {
            this.minBoreSizeString = entryPanel.getMinBoreSizeField().getText();
        }
        if (AppConstants.standard == IndianStandard.IS9079) {
            this.suctionSizeFieldString = entryPanel.getSuctionSizeField().getText();
        }
        this.isRefFieldString = entryPanel.getIsRefField().getText();
        if (AppConstants.standard == IndianStandard.IS9079) {
            this.stdNo = 9079;
        } else if (AppConstants.standard == IndianStandard.IS14220) {
            this.stdNo = 14220;
        } else if (AppConstants.standard == IndianStandard.IS8034) {
            this.stdNo = 8034;
        }
    }

    /**
     * @return the pumpTypeFieldString
     */
    public String getPumpTypeFieldString() {
        return pumpTypeFieldString;
    }

    /**
     * @return the ratingFieldString
     */
    public String getRatingFieldString() {
        return ratingFieldString;
    }

    /**
     * @return the headFieldString
     */
    public String getHeadFieldString() {
        return headFieldString;
    }

    /**
     * @return the dischFieldString
     */
    public String getDischFieldString() {
        return dischFieldString;
    }

    /**
     * @return the effFieldString
     */
    public String getEffFieldString() {
        return effFieldString;
    }

    /**
     * @return the currFieldString
     */
    public String getCurrFieldString() {
        return currFieldString;
    }

    /**
     * @return the voltFieldString
     */
    public String getVoltFieldString() {
        return voltFieldString;
    }

    /**
     * @return the phaseFieldString
     */
    public String getPhaseFieldString() {
        return phaseFieldString;
    }

    /**
     * @return the freqFieldString
     */
    public String getFreqFieldString() {
        return freqFieldString;
    }

    /**
     * @return the hRangeLwrFieldString
     */
    public String gethRangeLwrFieldString() {
        return hRangeLwrFieldString;
    }

    /**
     * @return the hRangeUprFieldString
     */
    public String gethRangeUprFieldString() {
        return hRangeUprFieldString;
    }

    /**
     * @return the delSizeFieldString
     */
    public String getDelSizeFieldString() {
        return delSizeFieldString;
    }

    /**
     * @return the isRefFieldString
     */
    public String getIsRefFieldString() {
        return isRefFieldString;
    }

    /**
     * @return the suctionSizeFieldString
     */
    public String getSuctionSizeFieldString() {
        return suctionSizeFieldString;
    }

    /**
     * @return the stagesString
     */
    public String getStagesString() {
        return stagesString;
    }

    /**
     * @return the minBoreSizeString
     */
    public String getMinBoreSizeString() {
        return minBoreSizeString;
    }

    /**
     * @return the stdNo
     */
    public int getStdNo() {
        return stdNo;
    }
}
