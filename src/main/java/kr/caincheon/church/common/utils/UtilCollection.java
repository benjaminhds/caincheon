// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UtilCollection.java

package kr.caincheon.church.common.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * 
 */
public class UtilCollection
{

	public static String[] toString(List list) {
		String[] rtList = new String[list.size()];
		for(int i=0, i2=rtList.length; i<i2; i++) {
			rtList[i] = list.get(i).toString();
		}
		return rtList;
	}
	
	public static String[] toString(Map map, boolean isValue) {
		String[] rtList = new String[map.size()];
		
		Iterator it = map.entrySet().iterator();
		int i = 0;
		while(it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			rtList[i++] = isValue ? e.getValue().toString() : e.getKey().toString();
		}
		
		return rtList;
	}
	

    public static String haveNecessariesParameters(Map bodyMap, String fields[], boolean isLengthCheck)
    {
        if(fields == null || fields.length < 1)
            return "";
        String rtVal = null;
        for(int i = 0; i < fields.length; i++)
        {
            Object obj = bodyMap.get(fields[i]);
            switch(i)
            {
            default:
                if(rtVal != null)
                    rtVal = (new StringBuilder(String.valueOf(rtVal))).append(",").toString();
                break;

            case 0: // '\0'
                break;
            }
            if(isLengthCheck)
            {
                if(obj == null || ((String)obj).length() < 1)
                    rtVal = (new StringBuilder(String.valueOf(rtVal))).append(fields[i]).toString();
            } else
            if(obj == null)
                rtVal = (new StringBuilder(String.valueOf(rtVal))).append(fields[i]).toString();
        }

        return rtVal;
    }
}
