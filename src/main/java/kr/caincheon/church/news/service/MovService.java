// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MovService.java

package kr.caincheon.church.news.service;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface MovService
{

    public abstract CommonDaoDTO movList(Map _params);

    //public abstract int movListCount(Map _params);

    public abstract Map movContents(Map _params);

    public abstract CommonDaoDTO movDownloads(Map _params);

    /** 교구영상 :: 교구영상은 youtube.com에 등록된 영상으로 그 영상의 썸네일을 이용하는 방법을 적용하여 동영상 목록을 조회한다. */
    public abstract CommonDaoDTO youtubeList(Map _params, String pageNo, String pageSize);
    
    /** 메인(/home.do)에서 교구영상 조회(5개만 조회) */
    public abstract CommonDaoDTO movList_Main(Map _params);
}
