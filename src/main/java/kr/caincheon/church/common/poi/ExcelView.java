package kr.caincheon.church.common.poi;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.caincheon.church.common.utils.UtilString;


public class ExcelView extends AbstractExcelPOIView
{

    public ExcelView()
    {
    }

    public void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        String target = UtilString.pnull(model.get("target"));
        System.out.println((new StringBuilder("[model]:")).append(model.toString()).toString());
        System.out.println((new StringBuilder("[workbook]:")).append(workbook.toString()).toString());
        System.out.println((new StringBuilder("[request]:")).append(request.toString()).toString());
        if(target.equals("doctrine"))
        {
            List list = (List)model.get("excelList");
            Sheet sheet = workbook.createSheet(target);
            Row row = null;
            int rowCount = 0;
            int cellCount = 0;
            row = sheet.createRow(rowCount++);
            row.createCell(cellCount++).setCellValue("\uBC88\uD638");
            row.createCell(cellCount++).setCellValue("ID");
            row.createCell(cellCount++).setCellValue("\uC2E0\uC790\uAD6C\uBD84");
            row.createCell(cellCount++).setCellValue("\uC774\uB984");
            row.createCell(cellCount++).setCellValue("\uC138\uB840\uBA85");
            row.createCell(cellCount++).setCellValue("\uC18C\uC18D\uBCF8\uB2F9");
            row.createCell(cellCount++).setCellValue("\uC2E0\uCCAD\uC77C");
            row.createCell(cellCount++).setCellValue("\uC2B9\uC778\uC5EC\uBD80");
            if(list != null)
            {
                Map map;
                for(Iterator iterator = list.iterator(); iterator.hasNext(); row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("APPROVE_YN_TEXT"))))
                {
                    map = (Map)iterator.next();
                    row = sheet.createRow(rowCount++);
                    cellCount = 0;
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("RNUM")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("ID")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("MEMBER_TYPE_TEXT")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("NAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("BAPTISMAL_NAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("CHURCH_NAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("APPLY_DAY")));
                }

            }
        }
        if(target.equals("marry"))
        {
            List list = (List)model.get("excelList");
            Sheet sheet = workbook.createSheet(target);
            Row row = null;
            int rowCount = 0;
            int cellCount = 0;
            row = sheet.createRow(rowCount++);
            row.createCell(cellCount++).setCellValue("번호");
            row.createCell(cellCount++).setCellValue("강좌신청날자");
            row.createCell(cellCount++).setCellValue("혼인예정일");
            row.createCell(cellCount++).setCellValue("성명(남)");
            row.createCell(cellCount++).setCellValue("세례명(남)");
            row.createCell(cellCount++).setCellValue("본당(남)");
            row.createCell(cellCount++).setCellValue("생년월일(남)");
            row.createCell(cellCount++).setCellValue("성명(여)");
            row.createCell(cellCount++).setCellValue("세례명(여)");
            row.createCell(cellCount++).setCellValue("본당(여)");
            row.createCell(cellCount++).setCellValue("생년월일(여)");
            row.createCell(cellCount++).setCellValue("신청일");
            row.createCell(cellCount++).setCellValue("승인여부");
            if(list != null)
            {
                Map map;
                for(Iterator iterator1 = list.iterator(); iterator1.hasNext(); row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("APPROVE_YN_TEXT"))))
                {
                    map = (Map)iterator1.next();
                    row = sheet.createRow(rowCount++);
                    cellCount = 0;
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("RNUM")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("LECTURE_APPLY_DAY")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("MARRY_DAY")));
                    
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("MAN_NAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("MAN_BAPTISMAL_NAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("MAN_CHURCH_NM")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("MAN_BIRTHDAY")));
                    
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("WOMAN_NAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("WOMAN_BAPTISMAL_NAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("WOMAN_CHURCH_NM")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("WOMAN_BIRTHDAY")));
                    
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("APPLY_DAY")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("PROCESS_STATUS_TEXT")));
                }

            }
        }
        if(target.equals("member"))
        {
            List list = (List)model.get("excelList");
            Sheet sheet = workbook.createSheet(target);
            Row row = null;
            int rowCount = 0;
            int cellCount = 0;
            row = sheet.createRow(rowCount++);
            row.createCell(cellCount++).setCellValue("\uBC88\uD638");
            row.createCell(cellCount++).setCellValue("ID");
            row.createCell(cellCount++).setCellValue("\uC774\uB984");
            row.createCell(cellCount++).setCellValue("\uC138\uB840\uBA85");
            row.createCell(cellCount++).setCellValue("\uD68C\uC6D0\uAD6C\uBD84");
            row.createCell(cellCount++).setCellValue("\uADF8\uB8F9");
            row.createCell(cellCount++).setCellValue("\uCD5C\uADF8\uC811\uC18D\uC77C");
            if(list != null)
            {
                Map map;
                for(Iterator iterator2 = list.iterator(); iterator2.hasNext(); row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("LASTLOGINDT"))))
                {
                    map = (Map)iterator2.next();
                    row = sheet.createRow(rowCount++);
                    cellCount = 0;
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("RNUM")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("ID")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("NAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("BAPTISMALNAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("MEMBERTYPENAME")));
                    row.createCell(cellCount++).setCellValue(UtilString.pnull(map.get("GROUPTYPENAME")));
                }

            }
        }
    }

    public Workbook createWorkbook()
    {
        return new XSSFWorkbook();
    }
}
