// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UtilCharSet.java

package kr.caincheon.church.common.utils;

import java.util.StringTokenizer;

/*
 * 
 */
public class UtilCharSet
{

    private UtilCharSet()
    {
    }

    public static String LPadding(String str, String addChar, int len)
    {
        while(len-- > 0) 
            str = (new StringBuilder(String.valueOf(addChar))).append(str).toString();
        return str;
    }

    public static String RPadding(String str, String addChar, int len)
    {
        while(len-- > 0) 
            str = (new StringBuilder(String.valueOf(str))).append(addChar).toString();
        return str;
    }

    public static String NVL(String strIn)
    {
        return strIn != null ? strIn : "";
    }

    public static String NVL(String strIn, String strOut)
    {
        return strIn != null ? strIn : strOut;
    }

    public static String EVL(String strIn, String strOut)
    {
        return strIn != null && !strIn.equals("") ? strIn : strOut;
    }

    public static String REMOVE(String strIn, char delchar)
    {
        strIn = NVL(strIn);
        strIn = strIn.replace(delchar, '\0');
        return strIn;
    }

    public static String CHSTRING(String source, String delim, String chstr)
    {
        source = NVL(source);
        StringTokenizer strToken = new StringTokenizer(source, delim);
        String strRtn = "";
        int nCnt = 0;
        while(strToken.hasMoreElements()) 
        {
            if(nCnt > 0)
                strRtn = (new StringBuilder(String.valueOf(strRtn))).append(chstr).toString();
            nCnt++;
            strRtn = (new StringBuilder(String.valueOf(strRtn))).append(strToken.nextElement()).toString();
        }
        return strRtn;
    }

    public static String cStr(int val)
    {
        Integer VAL = new Integer(val);
        return VAL.toString();
    }

    public static int cInt(String val)
    {
        Integer VAL = new Integer(val);
        return VAL.intValue();
    }

    public String addZero(int chkNumber, int chkLen)
    {
        String temp = cStr(chkNumber);
        int len = temp.length();
        if(len < chkLen)
        {
            for(int i = 1; i <= chkLen - len; i++)
                temp = (new StringBuilder("0")).append(temp).toString();

        }
        return temp;
    }

    public static boolean isRegID(String val)
    {
        if(val == null || val.length() != 13)
            return false;
        char chRegs[] = val.toCharArray();
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

    public static String A2EB(String strInput)
    {
        byte bIn[] = strInput.getBytes();
        byte bOut[] = new byte[bIn.length];
        for(int i = 0; i < bIn.length; i++)
            bOut[i] = translateByte(bIn[i]);

        String strOut = new String(bOut);
        return strOut;
    }

    public static byte translateByte(byte i)
    {
        for(int j = 0; j < ASCII.length;)
        {
            if(i == (byte)ASCII[j])
                return (byte)EBCDIC[j];
            i++;
        }

        return i;
    }

    public static String lineSeparator = System.getProperty("line.separator");
    public static String fileSeparator = System.getProperty("file.separator");
    private static final int ASCII[] = {
        32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 
        42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 
        62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 
        72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 
        82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 
        92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 
        102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 
        112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 
        122, 123, 124, 125, 126
    };
    private static final int EBCDIC[] = {
        64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 
        92, 78, 107, 96, 75, 97, 240, 241, 242, 243, 
        244, 245, 246, 247, 248, 249, 122, 94, 76, 126, 
        110, 111, 124, 193, 194, 195, 196, 197, 198, 199, 
        200, 201, 209, 210, 211, 212, 213, 214, 215, 216, 
        217, 226, 227, 228, 229, 230, 231, 232, 233, 173, 
        224, 189, 95, 109, 121, 129, 130, 131, 132, 133, 
        134, 135, 136, 137, 145, 146, 147, 148, 149, 150, 
        151, 152, 153, 162, 163, 164, 165, 166, 167, 168, 
        169, 192, 106, 208, 161
    };

}
