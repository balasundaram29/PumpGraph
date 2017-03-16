/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bala.table;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ANNAIENG
 */
public class ColumnGroup {

  private ArrayList<Integer> colList = new ArrayList<Integer>();
    private String text = "";
    private static int DEFAULT_MERGED_AREA_HEIGHT = 25;
    private int mergedAreaHeight;

    public void addColumn(int i) {
        if (!colList.contains(i)) {
            colList.add(i);
        }
        Collections.sort(colList);
    }

    public ColumnGroup() {
        this.mergedAreaHeight = DEFAULT_MERGED_AREA_HEIGHT;
    }

    /**
     * @return the group
     */
    public ArrayList<Integer> getColList() {
        return colList;
    }

    public int getLeftMostColumnIndex() {
        return colList.get(0);
    }

    public int getRightMostColumnIndex() {
        return colList.get(colList.size() - 1);
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the mergedAreaHeight
     */
    public int getMergedAreaHeight() {
        return mergedAreaHeight;
    }

    /**
     * @param mergedAreaHeight the mergedAreaHeight to set
     */
    public void setMergedAreaHeight(int mergedAreaHeight) {
        this.mergedAreaHeight = mergedAreaHeight;
    }





}
