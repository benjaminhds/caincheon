package kr.caincheon.church.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.dao.HomeDao;

/**
 * 
 * @author benjamin
 */
@Service("homeService")
public class HomeServiceImpl
    implements HomeService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="homeDao")
    private HomeDao homeDao;
    
    @Override
    public List noticeList()
    {
        _logger.fatal((new StringBuilder(" .... fatal::This is a Service .... [homeDao=")).append(homeDao).append("]").toString());
        _logger.error((new StringBuilder(" .... error::This is a Service .... [homeDao=")).append(homeDao).append("]").toString());
        _logger.warn((new StringBuilder(" .... warn ::This is a Service .... [homeDao=")).append(homeDao).append("]").toString());
        _logger.info((new StringBuilder(" .... info ::This is a Service .... [homeDao=")).append(homeDao).append("]").toString());
        _logger.debug((new StringBuilder(" .... debug::This is a Service .... [homeDao=")).append(homeDao).append("]").toString());
        _logger.trace((new StringBuilder(" .... trace::This is a Service .... [homeDao=")).append(homeDao).append("]").toString());
        System.out.println((new StringBuilder(" .... This is a Service .... [homeDao=")).append(homeDao).append("] _logger=").append(_logger.getName()).toString());
        return homeDao.noticeList();
    }

    @Override
    public List parishList()
    {
        return homeDao.parishList();
    }

}
