// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HolidayFileServiceImpl.java

package kr.caincheon.church.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.poi.ExcelRead;
import kr.caincheon.church.common.poi.ExcelReadOption;
import kr.caincheon.church.dao.HolidayFileDao;

// Referenced classes of package kr.caincheon.church.service:
//            HolidayFileService

@Service("holidayFileService")
public class HolidayFileServiceImpl extends CommonService
    implements HolidayFileService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="holidayFileDao")
    private HolidayFileDao holidayFileDao;

    @Override
    public CommonDaoDTO holidayList(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [params:"+_params+"]" );
    	CommonDaoDTO dto = new CommonDaoDTO();
    	holidayFileDao.holidayList(_params, dto);
    	dto.setPaging(ipnull(_params, "pageNo"), ipnull(_params, "pageSize"));
        return dto;
    }

    /*
     * 전례력 벌크로드
     * (non-Javadoc)
     * @see kr.caincheon.church.service.HolidayFileService#bulkloadPrecedentByXlsx(java.io.File)
     */
    @Override
    public boolean bulkloadPrecedentByXlsx(File destFile)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [params:"+destFile+"]" );
    	
    	// load a xlsx
        ExcelReadOption readOption = new ExcelReadOption();
        readOption.setFilePath(destFile.getAbsolutePath());
        readOption.setOutputColumns(new String[] {"A", "B", "C", "D", "E"});
        readOption.setStartRow(2);
        List excelContent = ExcelRead.read(readOption);
        
        // dao call
        return holidayFileDao.insertTempFile(excelContent);
    }

    @Override
    public Map holidayContents(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [params:"+_params+"]" );
    	
        return holidayFileDao.holidayContents(_params);
    }

    @Override
    public boolean holidayModify(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [params:"+_params+"]" );
    	
        return holidayFileDao.holidayModify(_params);
    }

    @Override
    public boolean holidayDelete(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [params:"+_params+"]" );
    	
        return holidayFileDao.holidayDelete(_params);
    }

}
