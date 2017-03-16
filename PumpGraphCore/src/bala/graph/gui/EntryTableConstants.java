package bala.graph.gui;

import bala.graph.settings.all.IndianStandard;
import bala.graph.settings.current.AppConstants;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bala
 */
public class EntryTableConstants {

    public  int NO_COL_INDEX ;
    public  int FREQ_COL_INDEX ;
    public  int SGR_COL_INDEX;// = 2;
    public  int DGR_COL_INDEX;
    public  int DISCH_COL_INDEX;
    public  int VOL_COL_INDEX;
    public  int CURR_COL_INDEX;
    public  int POWER_COL_INDEX;



    public  void setStandard(IndianStandard std) {
        if (std == IndianStandard.IS9079) {

            NO_COL_INDEX = 0;
            FREQ_COL_INDEX = 1;
            SGR_COL_INDEX = 2;
            DGR_COL_INDEX = 3;
            DISCH_COL_INDEX = 4;
            VOL_COL_INDEX = 5;
            CURR_COL_INDEX = 6;
            POWER_COL_INDEX = 7;

        } else if (std == IndianStandard.IS14220||std==IndianStandard.IS8034) {
            NO_COL_INDEX = 0;
            FREQ_COL_INDEX = 1;
            DGR_COL_INDEX = 2;
            DISCH_COL_INDEX = 3;
            VOL_COL_INDEX = 4;
            CURR_COL_INDEX = 5;
            POWER_COL_INDEX = 6;
        }
    }

    public EntryTableConstants(IndianStandard std) {
        this.setStandard(std);

    }

}
