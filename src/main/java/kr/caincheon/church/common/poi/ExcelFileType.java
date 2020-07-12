// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExcelFileType.java

package kr.caincheon.church.common.poi;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileType
{

    public ExcelFileType()
    {
    }

    public static Workbook getWorkbook(String filePath)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(filePath);
        }
        catch(FileNotFoundException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
        Workbook wb = null;
        if(filePath.toUpperCase().endsWith(".XLS"))
            try
            {
                wb = new HSSFWorkbook(fis);
            }
            catch(IOException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
        else
        if(filePath.toUpperCase().endsWith(".XLSX"))
            try
            {
                wb = new XSSFWorkbook(fis);
            }
            catch(IOException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
        return wb;
    }
}
