// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsgDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

public interface MsgDao
{

    public List msgList(Map map);

    public int msgListCount(Map map);

    public Map msgContents(Map map);

    public List admMsgList(Map map);

    public int admMsgListCount(Map map);

    public Map admMsgContents(Map map);

    public boolean msgInsert(Map map);

    public boolean msgModify(Map map);

    public boolean msgDelete(Map map);
}
