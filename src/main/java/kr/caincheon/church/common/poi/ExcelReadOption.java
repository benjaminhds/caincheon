// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExcelReadOption.java

package kr.caincheon.church.common.poi;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 */
public class ExcelReadOption
{

    public ExcelReadOption()
    {
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public List getOutputColumns()
    {
        List temp = new ArrayList();
        temp.addAll(outputColumns);
        return temp;
    }

    public void setOutputColumns(List outputColumns)
    {
        List temp = new ArrayList();
        temp.addAll(outputColumns);
        this.outputColumns = temp;
    }

    public void setOutputColumns(String outputColumns[])
    {
        if(this.outputColumns == null)
            this.outputColumns = new ArrayList();
        String as[];
        int j = (as = outputColumns).length;
        for(int i = 0; i < j; i++) {
            String ouputColumn = as[i];
            this.outputColumns.add(ouputColumn);
        }

    }

    public int getStartRow()
    {
        return startRow;
    }

    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }

    private String filePath;
    private List outputColumns;
    private int startRow;
}
