package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.caincheon.church.dao.AdmMemberDao;
import kr.caincheon.church.dao.DocDao;
import kr.caincheon.church.dao.ExcelDao;
import kr.caincheon.church.dao.MarryDao;


@Service("excelService")
public class ExcelServiceImpl implements ExcelService
{

    @Resource(name="excelDao")
    private ExcelDao excelDao;
    
    @Resource(name="docDao")
    private DocDao docDao;
    
    @Resource(name="marryDao")
    private MarryDao marryDao;
    
    @Resource(name="admMemberDao")
    private AdmMemberDao admMemberDao;

    public List getAllObjects(String target, Map searchMap)
    {
        if(target.equals("doctrine"))
            return docDao.docExcelList(searchMap);
        
        if(target.equals("marry"))
            return marryDao.marryExcelList(searchMap);
        
        if(target.equals("member"))
            return admMemberDao.selectMemberListForExceldownload(searchMap);
        else
            return null;
    }

}
