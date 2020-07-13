// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExcelCellRef.java

package kr.caincheon.church.common.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;

/*
 * Neter touch this source 
 */
public class ExcelCellRef
{

    public ExcelCellRef()
    {
    }

    public static String getName(Cell cell, int cellIndex)
    {
        int cellNum = 0;
        if(cell != null)
            cellNum = cell.getColumnIndex();
        else
            cellNum = cellIndex;
        return CellReference.convertNumToColString(cellNum);
    }

    public static String getValue(Cell cell)
    {
        String value = "";
        if(cell == null)
            value = "";
        else
        if(cell.getCellType() == 2)
            value = cell.getCellFormula();
        else
        if(cell.getCellType() == 0)
            value = (new StringBuilder(String.valueOf(cell.getNumericCellValue()))).toString();
        else
        if(cell.getCellType() == 1)
            value = cell.getStringCellValue();
        else
        if(cell.getCellType() == 4)
            value = (new StringBuilder(String.valueOf(cell.getBooleanCellValue()))).toString();
        else
        if(cell.getCellType() == 5)
            value = (new StringBuilder(String.valueOf(cell.getErrorCellValue()))).toString();
        else
        if(cell.getCellType() == 3)
            value = "";
        else
            value = cell.getStringCellValue();
        return value;
    }
}
