package bala.graph.settings.current;

import bala.graph.gui.EntryTableConstants;
import bala.graph.gui.ReportTableConstants;
import bala.graph.settings.all.IndianStandard;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ANNAIENG
 */

/*public class AppConstants {
 public  IndianStandard standard=IndianStandard.IS9079;
 public  String SOFTWARE_TITLE=" Annai : Graph Software - Centrifugal Monoblock Pumpsets";
 public  String COMPANY_NAME="Annai  Engineering  Company";
 public  String SHORT_COMPANY_ADDRESS="Coimbatore";
 public  String IS_REF="9079";
 public  String IS_NAME="Centrifugal Monoblock Pumpset";
 public  int ENTRY_TABLE_COL_COUNT=8;
 public  int MAX_ROWS_COUNT=8;
 public  int REPORT_TABLE_COL_COUNT = 15;
 }*/
public class AppConstants {

    public static IndianStandard standard = IndianStandard.IS9079;
    public IndianStandard currentStandard;
    public static String SOFTWARE_TITLE;
    public static String COMPANY_NAME;
    public static String SHORT_COMPANY_ADDRESS;
    public String IS_REF;
    public String IS_NAME;
    public int ENTRY_TABLE_COL_COUNT;
    public int MAX_ROWS_COUNT;
    public int REPORT_TABLE_COL_COUNT;
    public static IndianStandard defaultStandard = IndianStandard.IS9079;
    public EntryTableConstants entryTableConstants;
    public ReportTableConstants reportTableConstants;    /*   {
     if (standard == IndianStandard.IS9079) {
     SOFTWARE_TITLE = " Annai : Graph Software - Centrifugal Monoblock Pumpsets";
     COMPANY_NAME = "Annai  Engineering  Company";
     SHORT_COMPANY_ADDRESS = "Coimbatore";
     String IS_REF = "9079";
     String IS_NAME = "Centrifugal Monoblock Pumpset";
     ENTRY_TABLE_COL_COUNT = 8;
     MAX_ROWS_COUNT = 8;
     REPORT_TABLE_COL_COUNT = 15;
     }else if (standard == IndianStandard.IS14220) {
     SOFTWARE_TITLE = " Annai : Graph Software - Openwell Submersible Pumpsets";
     COMPANY_NAME = "Annai  Engineering  Company";
     SHORT_COMPANY_ADDRESS = "Coimbatore";
     String IS_REF = "14220";
     String IS_NAME = "Openwell Submersible Pumpset";
     ENTRY_TABLE_COL_COUNT = 7;
     MAX_ROWS_COUNT = 8;
     REPORT_TABLE_COL_COUNT = 14;
     }
     }*/


    public void setStandard(IndianStandard std) {
        if (std == IndianStandard.IS9079) {
            standard = std;
            currentStandard = std;
            SOFTWARE_TITLE = " Annai : Graph Software - Centrifugal Monoblock Pumpsets";
            COMPANY_NAME = "Annai  Engineering  Company";
            SHORT_COMPANY_ADDRESS = "Coimbatore";
            IS_REF = "9079";
            IS_NAME = "Centrifugal Monoblock Pumpset";
            ENTRY_TABLE_COL_COUNT = 8;
            MAX_ROWS_COUNT = 8;
            REPORT_TABLE_COL_COUNT = 15;
            entryTableConstants = new EntryTableConstants(std);
            reportTableConstants = new ReportTableConstants(std);
            System.out.println("App,entry tabl,rt,set to IS9079");

        } else if (std == IndianStandard.IS14220) {
            standard = std;
            currentStandard = std;
            SOFTWARE_TITLE = " Annai : Graph Software - Openwell Submersible Pumpsets";
            COMPANY_NAME = "Annai  Engineering  Company";
            SHORT_COMPANY_ADDRESS = "Coimbatore";
            IS_REF = "14220";
            IS_NAME = "Openwell Submersible Pumpset";
            ENTRY_TABLE_COL_COUNT = 7;
            MAX_ROWS_COUNT = 8;
            REPORT_TABLE_COL_COUNT = 14;
            entryTableConstants = new EntryTableConstants(std);
            reportTableConstants = new ReportTableConstants(std);
            System.out.println("App,entry tabl,rt,set to IS14220");
        } else if (std == IndianStandard.IS8034) {
            standard = std;
            currentStandard = std;
            SOFTWARE_TITLE = " Annai : Graph Software - Borewell Submersible Pumpsets";
            COMPANY_NAME = "Annai  Engineering  Company";
            SHORT_COMPANY_ADDRESS = "Coimbatore";
            IS_REF = "8034";
            IS_NAME = "Borewell Submersible Pumpset";
            ENTRY_TABLE_COL_COUNT = 7;
            MAX_ROWS_COUNT = 8;
            REPORT_TABLE_COL_COUNT = 14;
            entryTableConstants = new EntryTableConstants(std);
            reportTableConstants = new ReportTableConstants(std);
            System.out.println("App,entry tabl,rt,set to IS8034");
        }
    }

    public AppConstants(IndianStandard std) {
        this.setStandard(std);
    }
}
