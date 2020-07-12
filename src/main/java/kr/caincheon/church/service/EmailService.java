// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailService.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

public interface EmailService
{

    public abstract List emailList(Map map);

    public abstract int emailListCount(Map map);

    public abstract Map emailContents(Map map);

    public abstract boolean emailInsert(Map map);

    public abstract boolean emailModify(Map map);

    public abstract boolean emailDelete(Map map);
}
