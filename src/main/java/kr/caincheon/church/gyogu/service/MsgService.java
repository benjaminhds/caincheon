// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsgService.java

package kr.caincheon.church.gyogu.service;

import java.util.List;
import java.util.Map;

public interface MsgService
{

    public abstract List msgList(Map map);

    public abstract int msgListCount(Map map);

    public abstract Map msgContents(Map map);

    public abstract List admMsgList(Map map);

    public abstract int admMsgListCount(Map map);

    public abstract Map admMsgContents(Map map);

    public abstract boolean msgInsert(Map map);

    public abstract boolean msgModify(Map map);

    public abstract boolean msgDelete(Map map);
}
