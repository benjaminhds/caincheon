// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractExcelPOIView.java

package kr.caincheon.church.common.poi;

import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

public abstract class AbstractExcelPOIView extends AbstractView
{

    public AbstractExcelPOIView()
    {
    }

    protected boolean generatesDownloadContent()
    {
        return true;
    }

    protected final void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        Workbook workbook = createWorkbook();
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        buildExcelDocument(model, workbook, request, response);
        response.setContentType(getContentType());
        ServletOutputStream out = response.getOutputStream();
        out.flush();
        workbook.write(out);
        out.flush();
        if(workbook instanceof SXSSFWorkbook)
            ((SXSSFWorkbook)workbook).dispose();
    }

    protected abstract Workbook createWorkbook();

    protected abstract void buildExcelDocument(Map map, Workbook workbook, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception;

    private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
}
