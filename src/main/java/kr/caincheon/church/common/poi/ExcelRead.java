// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExcelRead.java

package kr.caincheon.church.common.poi;

import java.io.PrintStream;
import java.util.*;
import org.apache.poi.ss.usermodel.*;

// Referenced classes of package kr.caincheon.church.common:
//            ExcelReadOption, ExcelFileType, ExcelCellRef

public class ExcelRead
{

    public ExcelRead()
    {
    }

    public static List read(ExcelReadOption excelReadOption)
    {
        Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
        Sheet sheet = wb.getSheetAt(0);
        System.out.println((new StringBuilder("Sheet \uC774\uB984: ")).append(wb.getSheetName(0)).toString());
        System.out.println((new StringBuilder("\uB370\uC774\uD130\uAC00 \uC788\uB294 Sheet\uC758 \uC218 :")).append(wb.getNumberOfSheets()).toString());
        int numOfRows = sheet.getPhysicalNumberOfRows();
        int numOfCells = 0;
        Row row = null;
        Cell cell = null;
        String cellName = "";
        Map map = null;
        List result = new ArrayList();
        for(int rowIndex = excelReadOption.getStartRow() - 1; rowIndex < numOfRows; rowIndex++)
        {
            row = sheet.getRow(rowIndex);
            if(row != null)
            {
                numOfCells = row.getLastCellNum();
                map = new HashMap();
                for(int cellIndex = 0; cellIndex < numOfCells; cellIndex++)
                {
                    cell = row.getCell(cellIndex);
                    cellName = ExcelCellRef.getName(cell, cellIndex);
                    if(excelReadOption.getOutputColumns().contains(cellName))
                        map.put(cellName, ExcelCellRef.getValue(cell));
                }

                result.add(map);
            }
        }

        return result;
    }
}
