// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonStorage.java

package kr.caincheon.church.common;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class CommonStorage
{

    private CommonStorage()
    {
    }

    public static CommonStorage getInstance()
    {
        if(INSTANCE == null)
            INSTANCE = new CommonStorage();
        return INSTANCE;
    }

    public synchronized void save(String key, String data)
    {
        STORAGE.put(key, data);
    }

    public synchronized Object find(String key)
    {
        return STORAGE.get(key);
    }

    public synchronized Object pop(String key)
    {
        return STORAGE.remove(key);
    }

    public synchronized void remove(String key)
    {
        STORAGE.remove(key);
    }

    public synchronized boolean checkOAuthKey(String authtoken)
    {
        Object o = STORAGE.get("SNS_AUTHKEY");
        if(o == null)
            return false;
        Long t = (Long)((HashMap)o).remove(authtoken);
        if(t == null)
        {
            return false;
        } else
        {
            long n = t.longValue() + 60000L;
            System.out.println((new StringBuilder("[")).append(Thread.currentThread().getName()).append(":").append(Thread.currentThread().getStackTrace()[1].getLineNumber()).append("]").toString());
            return n - System.currentTimeMillis() > 0L;
        }
    }

    public synchronized String getOAuthKey()
    {
        String authtoken = (new BigInteger(130, new SecureRandom())).toString(32);
        ((HashMap)STORAGE.get("SNS_AUTHKEY")).put(authtoken, Long.valueOf(System.currentTimeMillis()));
        long currMillies = System.currentTimeMillis();
        if(currMillies - (0x927c0L + LAST_MEM_CLEAR_MILLIES) < 0L)
        {
            executeOAuthKeyRemover();
            LAST_MEM_CLEAR_MILLIES = currMillies;
        }
        System.out.println((new StringBuilder("[")).append(Thread.currentThread().getName()).append(":").append(Thread.currentThread().getStackTrace()[1].getLineNumber()).append("]").toString());
        return authtoken;
    }

    private static synchronized void executeOAuthKeyRemover()
    {
        Object o = STORAGE.get("SNS_AUTHKEY");
        if(o == null)
        {
            STORAGE.put("SNS_AUTHKEY", new HashMap());
            return;
        }
        HashMap M = (HashMap)o;
        Iterator itr = M.keySet().iterator();
        int c = 0;
        while(itr.hasNext()) 
        {
            String k = itr.next().toString();
            Long t = (Long)M.get(k);
            long n = System.currentTimeMillis() - t.longValue() - 60000L;
            if(n < 1L)
            {
                M.remove(k);
                c++;
            }
        }
        if(c > 0)
            System.out.println((new StringBuilder("[SNS OAuthkey] Removed the expred keys : ")).append(c).append(" EA.").toString());
    }

    private static final HashMap STORAGE;
    private static final HashMap<String, Long> CAPTCHA;
    private static CommonStorage INSTANCE = new CommonStorage();
    private static final String SNS_KEY = "SNS_AUTHKEY";
    private static final int OAUTHKEY_EXPIRE_MILLIES = 60 *1000;
    private static long LAST_MEM_CLEAR_MILLIES = 0x493e0L;

    static 
    {
        STORAGE = new HashMap();
        if(!STORAGE.containsKey("SNS_AUTHKEY"))
            STORAGE.put("SNS_AUTHKEY", new HashMap());
        
        CAPTCHA = new HashMap<String, Long>();
    }
    
    
    public synchronized void saveCaptcha(String key)
    {
    	CAPTCHA.put(key, System.currentTimeMillis());
    }
    public synchronized boolean isExpreCaptcha(String key)
    {
    	Long time = CAPTCHA.remove(key);
    	if(time != null) {
    		if ( System.currentTimeMillis() - time.longValue() - 5*60*100 > 0 ) // 5분이내면 유효, 그렇지 않으면 expired
    			return false;
    		else 
    			return true;
    	}
        return false;
    }
    
}
