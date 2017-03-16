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
public class ReportTableConstants {

    public static  int SLNO_COL_INDEX;
    public   static int FREQ_COL_INDEX;
    public  int SGR_COL_INDEX;
    public  int DGR_COL_INDEX;
    public  int VHC_COL_INDEX;
    public  int TH_COL_INDEX;
    public  int DISCH_COL_INDEX;
    public  int VOL_COL_INDEX;
    public  int CURR_COL_INDEX;
    public  int MINPUT_COL_INDEX;
    public  int RDISCH_COL_INDEX;
    public  int RHEAD_COL_INDEX;
    public  int RINPUT_COL_INDEX;
    public  int POP_COL_INDEX;
    public  int EFF_COL_INDEX;

    public ReportTableConstants(IndianStandard std) {
   this.setStandard(std);
    }

    // {

    public  void setStandard(IndianStandard std) {
        if (std == IndianStandard.IS9079) {
           

                SLNO_COL_INDEX = 0;
                FREQ_COL_INDEX = 1;
                SGR_COL_INDEX = 2;
                DGR_COL_INDEX = 3;
                VHC_COL_INDEX = 4;
                TH_COL_INDEX = 5;
                DISCH_COL_INDEX = 6;
                VOL_COL_INDEX = 7;
                CURR_COL_INDEX = 8;
                MINPUT_COL_INDEX = 9;
                RDISCH_COL_INDEX = 10;
                RHEAD_COL_INDEX = 11;
                RINPUT_COL_INDEX = 12;
                POP_COL_INDEX = 13;
                EFF_COL_INDEX = 14;
         
        }
        if (std == IndianStandard.IS14220||std==IndianStandard.IS8034) {

            SLNO_COL_INDEX = 0;
            FREQ_COL_INDEX = 1;
           // SGR_COL_INDEX = 2;
            DGR_COL_INDEX = 2;
            VHC_COL_INDEX = 3;
            TH_COL_INDEX = 4;
            DISCH_COL_INDEX = 5;
            VOL_COL_INDEX = 6;
            CURR_COL_INDEX = 7;
            MINPUT_COL_INDEX = 8;
            RDISCH_COL_INDEX = 9;
            RHEAD_COL_INDEX = 10;
            RINPUT_COL_INDEX = 11;
            POP_COL_INDEX = 12;
            EFF_COL_INDEX = 13;
        }
    }

}
