package kr.caincheon.church.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilString
{

    public UtilString()
    {
    }

    public static String pnull(Object key)
    {
        if(key == null || "".equals(key.toString().trim()))
            return "";
        else
            return key.toString();
    }

    public static String pnull(String key)
    {
        if(key == null || "".equals(key.toString().trim()))
            key = "";
        return key;
    }

    public static String pnull(Object key, String defaultVal)
    {
        if(key == null || "".equals(key.toString().trim()))
            key = defaultVal;
        return key.toString();
    }

    public static String pnull(String key, String defaultVal)
    {
        if(key == null || key.length() == 0)
            key = defaultVal;
        return key;
    }

    public static String pnull(Map params, String key)
    {
        return pnull(params, key, "");
    }

    public static String pnull(Map params, String key, String defaultVal)
    {
        if(params != null && key != null)
        {
            Object obj = params.get(key);
            return obj != null ? obj.toString() : defaultVal;
        } else
        {
            return defaultVal;
        }
    }

    public static String toString(String elements[], String delim)
    {
    	String rtStr = elements!=null && elements.length>0 ? elements[0] : "";
        for(int i=1, i2 = elements.length; i < i2; i++)
            rtStr += delim + elements[i] ;

        return rtStr;
    }

    public static List toList(String elements[])
    {
        List rtList = new ArrayList();
        int i = 0;
        for(int i2 = elements.length; i < i2; i++)
            rtList.add(elements[i]);

        return rtList;
    }

    public static ArrayList toArray(String ori, String delim, boolean doTrim)
    {
        String param[] = ori.split(delim);
        ArrayList rtVal = new ArrayList();
        int isTrim = doTrim ? 1 : 0;
        int i = 0;
        for(int i2 = param.length; i < i2; i++)
            switch(isTrim)
            {
            case 1: // '\001'
                rtVal.add(param[i].trim());
                break;

            case 0: // '\0'
                rtVal.add(param[i]);
                break;
            }

        return rtVal;
    }

    public static Map toMap(String ori, String delim1, String delim2, boolean doTrim)
    {
        String firstEle[] = ori.split(delim1);
        int isTrim = doTrim ? 1 : 0;
        Map rtMap = new HashMap();
        int i = 0;
        for(int i2 = firstEle.length; i < i2; i++)
        {
            String secondEle = firstEle[i];
            String elements[] = secondEle.split(delim2);
            switch(isTrim)
            {
            case 1: // '\001'
                elements[0] = elements[0].trim();
                break;
            }
            if(elements.length > 1)
                rtMap.put(elements[0], elements[1]);
            else
                rtMap.put(elements[0], "");
        }

        return rtMap;
    }

    public static String LPadding(int number, int length)
    {
        return LPadding(number, length, '0');
    }

    public static String LPadding(long number, int length)
    {
        return LPadding(number, length, '0');
    }

    public static String LPadding(int number, int length, char paddingChar)
    {
    	String t = ""+number;
		int tLen = t.length();
		if (tLen < length) 
			for (int i = 1; i <= (length - tLen); i++)
				t = paddingChar + t;
		return t;
    }

    public static String LPadding(long number, int length, char paddingChar)
    {
    	String t = ""+number;
		int tLen = t.length();
		if (tLen < length) 
			for (int i = 1; i <= (length - tLen); i++)
				t = paddingChar + t;
		return t;
    }

    public static String RPadding(int number, int length)
    {
        return RPadding(number, length, '0');
    }

    public static String RPadding(long number, int length)
    {
        return RPadding(number, length, '0');
    }

    public static String RPadding(int number, int length, char paddingChar)
    {
    	String t = ""+number;
		int tLen = t.length();
		if (tLen < length) 
			for (int i = 1; i <= (length - tLen); i++)
				t = t + paddingChar;
		return t;
    }

    public static String RPadding(long number, int length, char paddingChar)
    {
    	String t = ""+number;
		int tLen = t.length();
		if (tLen < length) 
			for (int i = 1; i <= (length - tLen); i++)
				t = t + paddingChar;
		return t;
    }

    /** 문자열에서 숫자만 추출하여 리턴 */
    public static String extractOnlyDigit(String str) {
    	Matcher m = Pattern.compile("[0-9]").matcher(str);
    	String x="";
    	while(m.find()) {
			x += m.group();
		}
    	return x;
    }
    
    /** 날자유형의 문자열(구분자로 포메팅)로 만들어 리턴한다. :: 예) '20171231' 는  '2017-12-31'로 변환해서 리턴 */
    public static String setDayString(String str, String delim) {
    	str = extractOnlyDigit(str);//문자열에서 숫자만 추출(나머지는 버린다.)
    	if(str.length() <= 4) {
    		return str;
    	} else if(str.length() <= 6) {
    		return str.substring(0,4) + delim + str.substring(4,6);
    	}
    	int len = str.length();
    	if(len > 8) len = 8;
    	return str.substring(0,4) + delim + str.substring(4,6) + delim + str.substring(6,len);
    }
    
    public static boolean verifyResidentRegistrationId(String socialId)
    {
        if(socialId == null || socialId.length() != 13)
            return false;
        char chRegs[] = socialId.toCharArray();
        if(chRegs[6] != '1' && chRegs[6] != '2' && chRegs[6] != '3' && chRegs[6] != '4' && chRegs[6] != '9' && chRegs[6] != '0')
            return false;
        int nTotInt = 0;
        int nMulti = 2;
        if(chRegs.length != 13)
            return false;
        for(int i = 0; i < chRegs.length - 1; i++)
        {
            if(chRegs[i] < '0' || chRegs[i] > '9')
                return false;
            if(i == 12)
                break;
            int curInt = chRegs[i] - 48;
            if(nMulti == 10)
                nMulti = 2;
            nTotInt += nMulti * curInt;
            nMulti++;
        }

        int nResult = (11 - nTotInt % 11) % 10;
        int nComp = chRegs[12] - 48;
        return nResult == nComp;
    }

    public static String subStringToByte(String str, int endIndex)
    {
		if (str == null) return "";
		String tmp = str;
		int slen = 0, blen = 0;
		char c;
		if (tmp.getBytes().length > endIndex - 1) {
			while (blen < endIndex) {
				c = tmp.charAt(slen);
				blen++;
				slen++;
				if (c > 127)
					blen = blen + 1;
			}
			tmp = tmp.substring(0, slen);
		}
        return tmp;
    }

    public static String convertByteToString(byte strByte[])
    {
        try
        {
            return new String(strByte, "UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] convertStringToByte(String str)
    {
        try
        {
            return str.getBytes("UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] convertStringToUtf8Byte(String str, int len)
    {
        byte rtByte[] = null;
        try {
            byte strByte[] = str.getBytes("UTF-8");
            rtByte = new byte[len];
            for(int i = 0; i < len; i++)
                if(strByte.length > i)
                    rtByte[i] = strByte[i];
                else
                    rtByte[i] = 0x20; //32;

        } catch(UnsupportedEncodingException e) {
            rtByte = null;
            e.printStackTrace();
        }
        return rtByte;
    }

    public static String[] mergeStringArray(String a[], String b[])
    {
        String result[] = new String[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static byte[] mergeByteArray(byte a[], byte b[])
    {
        byte result[] = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static String byteArrayToString(byte bytes[])
    {
        StringBuilder sb = new StringBuilder();
        try {
            for (byte b : bytes) {
                sb.append(String.format("%02X", b ));
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    public static byte[] hexToByteArray(String hex)
    {
        if(hex == null || hex.length() % 2 != 0)
            return new byte[0];
        byte bytes[] = new byte[hex.length() / 2];
        for(int i = 0; i < hex.length(); i += 2)
        {
            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int)Math.floor(i / 2)] = value;
        }

        return bytes;
    }

    public static Map jsonToMap(String jsonData)
    {
        jsonData = jsonData.substring(1, jsonData.length() - 1);
        String k = "";
        String v = "";
        Map map = new HashMap();
        String lines[] = jsonData.split(",");
        int i = 0;
        for(int i2 = lines.length; i < i2; i++)
        {
            String element[] = lines[i].split(":");
            k = element[0].replace("\"", "").trim();
            v = element[1].substring(1, element[1].trim().length() - 1).trim();
            map.put(k, v);
        }

        return map;
    }

    public static String getRandomString()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
