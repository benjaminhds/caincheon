// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsgServiceImpl.java

package kr.caincheon.church.gyogu.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.dao.MsgDao;

// Referenced classes of package kr.caincheon.church.service:
//            MsgService

@Service("msgService")
public class MsgServiceImpl extends CommonService
    implements MsgService
{

    public MsgServiceImpl()
    {
    }

    public List msgList(Map _params)
    {
    	List<Map> list = msgDao.msgList(_params);
    	
    	if(list!=null) {
	    	for(int i=0, i2=list.size() ; i<i2 ; i++) {
	    		Map row = list.get(i);
	    		String CONTENT = pnull(row, "CONTENT");
	    		if(CONTENT.length() > 250) {
	    			row.put("CONTENT_S", CONTENT.substring(0, 250) );
	    		}
	    	}
    	}
    	
        return list;
    }

    public int msgListCount(Map _params)
    {
        return msgDao.msgListCount(_params);
    }

    /*
	 * front에서 메시지 보기
	 */
    public Map msgContents(Map _params)
    {
        return msgDao.msgContents(_params);
    }

    public List admMsgList(Map _params)
    {
        return msgDao.admMsgList(_params);
    }

    public int admMsgListCount(Map _params)
    {
        return msgDao.admMsgListCount(_params);
    }

    public Map admMsgContents(Map _params)
    {
        return msgDao.admMsgContents(_params);
    }

    public boolean msgInsert(Map _params)
    {
        return msgDao.msgInsert(_params);
    }

    public boolean msgModify(Map _params)
    {
        return msgDao.msgModify(_params);
    }

    public boolean msgDelete(Map _params)
    {
        return msgDao.msgDelete(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="msgDao")
    private MsgDao msgDao;
}
