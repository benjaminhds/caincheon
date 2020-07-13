// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UtilInt.java

package kr.caincheon.church.common.utils;

/*
 * 
 */
public class UtilInt
{

    public UtilInt()
    {
    }

    public static int pint(Object val)
    {
        if(val == null || "".equals(val.toString().trim()))
            return -1;
        else
            return Integer.parseInt(val.toString());
    }

    public static int pint(Object val, int defaultInt)
    {
        if(val == null || "".equals(val.toString().trim()))
            return defaultInt;
        else
            return Integer.parseInt(val.toString());
    }

    public static int char2Int(char cVal)
    {
        return cVal - 48;
    }

    public static int[] mergeIntArray(int a[], int b[])
    {
        int result[] = new int[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static int[] sumIntArray(int a[], int b[])
    {
        int len = a.length <= b.length ? a.length : b.length;
        int result[] = new int[len];
        for(int i = 0; i < result.length; i++)
            result[i] = a[i] + b[i];

        return result;
    }

    public static long builRandomDigit(int length)
    {
        if(length > 16)
            length = 16;
        double l = Math.pow(10D, length);
        double r = Math.random();
        long v = 0L;
        int loopLimit = 5;
        while((double)(v = (long)(r * l)) < l / 10D) 
        {
            r = Math.random();
            if(--loopLimit > 0)
                break;
        }
        return v;
    }

    public static long builRandomDigit(int length, int maxVal)
    {
        long k = 0L;
        long loopLimit = 5L;
        do
            k = builRandomDigit(length);
        while(k > (long)maxVal && --loopLimit > 0L);
        return k;
    }
}
