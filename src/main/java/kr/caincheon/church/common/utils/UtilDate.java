// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UtilDate.java

package kr.caincheon.church.common.utils;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 */
public class UtilDate
{


    public static String getDateFormat(String formatStr)
    {
        if(formatStr == null || formatStr.length() < 4)
            formatStr = "yyyyMMddHHmmss";
        SimpleDateFormat sdf2 = new SimpleDateFormat(formatStr);
        return sdf2.format(new Date());
    }

    // 날자를 요일로 리턴
    public static String printYoilOfDay(String yyyymmdd) {
    	String[] week = {"","주일", "월", "화", "수", "목", "금", "토"};
    	String ymd = yyyymmdd.replaceAll("-","").replaceAll("/","");
    	int y = Integer.parseInt(ymd.substring(0,4));
    	int m = Integer.parseInt(ymd.substring(4,6));
    	int d = Integer.parseInt(ymd.substring(6));
    	
    	java.util.Calendar cld = java.util.Calendar.getInstance();
    	cld.set(y, m-1, d);//1월(0), 2월(1), ..., 12월(11)
    	
    	return week[ cld.get(java.util.Calendar.DAY_OF_WEEK) ];
    }
    
    
    // 날자를 요일로 바꿔 리턴
    public static String getDayOfWeekKR(int nicknameOfDay)
    {
    	String[] days = {"일","월","화","수","목","금","토"};
        Calendar calendar = Calendar.getInstance();
        if(nicknameOfDay < 0 || nicknameOfDay > 7)
            nicknameOfDay = calendar.get(7);
        if(nicknameOfDay == 0)
            nicknameOfDay = 1;
        return days[calendar.get(7) - 1];
    }

    public static int getDayOfWeek()
    {
        return Calendar.getInstance().get(7);
    }

    public static int getWeekOfYear()
    {
        return Calendar.getInstance().get(3);
    }

    public static int getWeekOfYear(String yyyyMMdd)
    {
        if(yyyyMMdd == null || yyyyMMdd.length() < 8)
            yyyyMMdd = getYMD();
        int year = Integer.parseInt(yyyyMMdd.substring(0, 4));
        int month = Integer.parseInt(yyyyMMdd.substring(4, 6)) - 1;
        int day = Integer.parseInt(yyyyMMdd.substring(6, 8));
        Calendar destDate = Calendar.getInstance();
        destDate.set(year, month, day);
        return destDate.get(3);
    }

    public static int getWeekOfMonth()
    {
        return Calendar.getInstance().get(4);
    }

    public static int getYear()
    {
        return Calendar.getInstance().get(1);
    }

    public static int getMonth()
    {
        return Calendar.getInstance().get(2) + 1;
    }

    public static String getMonthS()
    {
        int t = Calendar.getInstance().get(2) + 1;
        return t >= 10 ? (new StringBuilder()).append(t).toString() : (new StringBuilder("0")).append(t).toString();
    }

    public static int getToday()
    {
        return Calendar.getInstance().get(5);
    }

    public static String getTodayS()
    {
        int t = Calendar.getInstance().get(5);
        return t >= 10 ? (new StringBuilder()).append(t).toString() : (new StringBuilder("0")).append(t).toString();
    }

    public static int getHour()
    {
        return Calendar.getInstance().get(11);
    }

    public static String getHourS()
    {
        int t = Calendar.getInstance().get(11);
        return t >= 10 ? (new StringBuilder()).append(t).toString() : (new StringBuilder("0")).append(t).toString();
    }

    public static int getMinutes()
    {
        return Calendar.getInstance().get(12);
    }

    public static String getMinutesS()
    {
        int t = Calendar.getInstance().get(12);
        return t >= 10 ? (new StringBuilder()).append(t).toString() : (new StringBuilder("0")).append(t).toString();
    }

    public static int getSeconds()
    {
        return Calendar.getInstance().get(13);
    }

    public static String getSecondsS()
    {
        int t = Calendar.getInstance().get(13);
        return t >= 10 ? (new StringBuilder()).append(t).toString() : (new StringBuilder("0")).append(t).toString();
    }

    public static String getYMD()
    {
        Calendar cld = Calendar.getInstance();
        String rtVal = (new StringBuilder()).append(cld.get(1)).toString();
        int m = cld.get(2) + 1;
        rtVal = (new StringBuilder(String.valueOf(rtVal))).append(m >= 10 ? "" : "0").append(m).toString();
        int d = cld.get(5);
        rtVal = (new StringBuilder(String.valueOf(rtVal))).append(d >= 10 ? "" : "0").append(d).toString();
        return rtVal;
    }

    public static String getYMD(int preDays)
    {
        Calendar cld = Calendar.getInstance();
        cld.add(5, preDays);
        String rtVal = (new StringBuilder()).append(cld.get(1)).toString();
        int m = cld.get(2) + 1;
        rtVal = (new StringBuilder(String.valueOf(rtVal))).append(m >= 10 ? "" : "0").append(m).toString();
        int d = cld.get(5);
        rtVal = (new StringBuilder(String.valueOf(rtVal))).append(d >= 10 ? "" : "0").append(d).toString();
        return rtVal;
    }

    public static String getPreDays(int preDays)
    {
        String today = null;
        String preday = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar currentDate = Calendar.getInstance();
        today = dateFormat.format(currentDate.getTime());
        currentDate.add(5, -preDays);
        return dateFormat.format(currentDate.getTime());
    }

    public static String getYMDHMS()
    {
        return getDateFormat(null);
    }

    public static String getYM()
    {
        Calendar cld = Calendar.getInstance();
        String rtVal = (new StringBuilder()).append(cld.get(1)).toString();
        int m = cld.get(2) + 1;
        rtVal = (new StringBuilder(String.valueOf(rtVal))).append(m >= 10 ? "" : "0").append(m).toString();
        return rtVal;
    }

    public static String getYM(String basicDate, int addCount)
    {
    	Calendar cld = Calendar.getInstance();
    	
    	int Y = cld.get(Calendar.YEAR), M = cld.get(Calendar.MONTH);
    	if(basicDate.length()>4) {
    		Y = Integer.parseInt(basicDate.substring(0, 4));
    		M = Integer.parseInt(basicDate.substring(4, 6));
    	}
    	cld.set(Calendar.YEAR, Y);
        cld.set(Calendar.MONTH, M - 1 + addCount);
        int yyyy = cld.get(1);
        int mm = cld.get(2) + 1;
        return (new StringBuilder(String.valueOf(yyyy))).append(mm >= 10 ? (new StringBuilder()).append(mm).toString() : (new StringBuilder("0")).append(mm).toString()).toString();
    }
    
    public static void main(String[] args) {
    	//System.out.println(printYoilOfDay("2018-04-05"));
    	System.out.println(getYM("201803", -1));
		System.out.println(getYM("201803", 1));
	}
    
    public static String getYM(int addCount)
    {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.MONTH, addCount);
        int yyyy = cld.get(1);
        int mm = cld.get(2) + 1;
        return (new StringBuilder(String.valueOf(yyyy))).append(mm >= 10 ? (new StringBuilder()).append(mm).toString() : (new StringBuilder("0")).append(mm).toString()).toString();
    }

    public static String getMondayOfWeek()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.set(7, 2);
        return df.format(cal.getTime());
    }

    public static String getMondayOfWeek(String dateStr)
    {
        if(dateStr == null || dateStr.trim().length() != 8)
        {
            return "";
        } else
        {
            int year = Integer.parseInt(dateStr.substring(0, 4));
            int month = Integer.parseInt(dateStr.substring(4, 6)) - 1;
            int date = Integer.parseInt(dateStr.substring(6, 8));
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, date);
            df.format(cal.getTime());
            cal.set(7, 2);
            return df.format(cal.getTime());
        }
    }

    public static String getMondayOfpassedWeek(int weekCount)
    {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.set(7, 2);
        cal.add(5, -7 * weekCount);
        return df.format(cal.getTime());
    }

    public static String getMonthOfPrev(String format)
    {
        return getMonthOfAddition(format, -1);
    }

    public static String getMonthOfPost(String format)
    {
        return getMonthOfAddition(format, 1);
    }
    
    /**
     * 현재일자를 기준으로 월을 +,- 하여 포메팅하여 날자를 리턴
     * - 예) getMonthOfAddition("yyyyMMdd", -3) 은 현재년월일 기준으로 3개월 전의 년월일을 리턴
     */
    public static String getMonthOfAddition(String format, int addMinusMonth)
    {
        DateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + addMinusMonth);//cal.set(2, cal.get(2) + addMinusMonth);
        return df.format(cal.getTime());
    }
    
    /**
     * 지정한 년도의 1월부터 현재월(당해년도 이전년도의 경우라면 12월까지)를 포메팅하여 목록을 리턴, year가 1보다 작다면, 당해년도에서 연산한 년도로 설정.
     * -예) getMonthsInYear("yyyy-MM",2017) => [2017-04, 2017-05, 2017-06, 2017-07, 2017-08, 2017-09, 2017-10, 2017-11, 2017-12]
     * -예) getMonthsInYear("yyyy-MM", -1) => 현재년도가 2018년이라면,,,2017년 기준으로 월을 구한다. [2017-04, 2017-05, 2017-06, 2017-07, 2017-08, 2017-09, 2017-10, 2017-11, 2017-12]
     */
    public static java.util.List<String> getMonthsInYear(String format, int year)
    {
    	java.util.List<String> years = new java.util.ArrayList<String>();
        DateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        
        int sm = 1, em = 12;
        if (year < 1) year = cal.get(Calendar.YEAR)+year;
        if (year > cal.get(Calendar.YEAR) || year < 2017) return years; // 2017년 이전과 미래는 리턴 안함
        if (year == cal.get(Calendar.YEAR) ) em = cal.get(Calendar.MONTH)+1; // 당해년도는 당월까지만
        if (year == 2017) sm = 4; // 2017년 4월부터 서비스 시작이므로..
        
        cal.set(Calendar.YEAR, year);
        for(int i=sm-1; i < em ; i++) {
        	cal.set(Calendar.MONTH, i );
        	years.add(df.format(cal.getTime()));
        }
        
        return years;
    }
    
    /**
     * 지정한 일자(baseDate, '2017-12')을 기준으로 월(addMinusMonth, +3/-3)을 연산(+ or -) 후 날자를 리턴
     * - 주의) 날자 포멧(format & baseDate) 과 을 맞춰야 함. 만약 포멧이 다르면, 이상한 결과가 리턴됨.
     * 예) moveMonthOfAddition("yyyyMM", "201707", 2) => 201709
     * 예) moveMonthOfAddition("yyyyMM", "201707", -2) => 201705
     * 예) moveMonthOfAddition("yyyy-MM", "2017-07", -2) => 201603
     */
    public static String moveMonthOfAddition(String format, String baseDate, int addMinusMonth)
        throws ParseException
    {
        SimpleDateFormat oldSdf = new SimpleDateFormat(format);
        Date oldDate = oldSdf.parse(baseDate);
        
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(oldDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + addMinusMonth);
        String dateString = df.format(cal.getTime());
        return dateString;
    }
}
