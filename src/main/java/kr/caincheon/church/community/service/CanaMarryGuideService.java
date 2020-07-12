// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CanaService.java

package kr.caincheon.church.community.service;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface CanaMarryGuideService
{
	/**
	 * 목록조회 :: 카나혼(type=1)과 주말혼인 강좌(type=2)
	 */
	CommonDaoDTO listMarryGuide(Map<String,Object> _params) throws Exception ;
	
	/**
	 * 등록 :: 카나혼과 주말혼인 강좌
	 */
	int insertMarryGuide(Map<String,Object> _params) throws Exception ;
	
	/**
	 * 수정 :: 카나혼과 주말혼인 강좌
	 */
	int updateMarryGuide(Map<String,Object> _params) throws Exception ;
	
	/**
	 * 삭제 :: 카나혼과 주말혼인 강좌 - 삭제 flag on
	 */
	int deleteMarryGuide(Map<String,Object> _params) throws Exception ;
}
