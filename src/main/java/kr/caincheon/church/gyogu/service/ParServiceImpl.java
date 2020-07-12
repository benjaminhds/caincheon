package kr.caincheon.church.gyogu.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.dao.ParDao;


@Service("parService")
public class ParServiceImpl
    implements ParService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="parDao")
    private ParDao parDao;
    
    
    public List parList(Map _params) {
        return parDao.parList(_params);
    }

    public int parListCount(Map _params) {
        return parDao.parListCount(_params);
    }

    public Map parContents(Map _params) {
        return parDao.parContents(_params);
    }

}
