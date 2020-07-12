package kr.caincheon.church.common.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonParameter extends CommonLogging {
	
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
	/*  */
	protected final String ERROR_MSG = "ERROR_MSG";
	protected final String ERROR_CD  = "ERROR_CD";
	
	/* CommonController.build()에서 request parameters(_params) 에 세션 MAP을 저장하는데, 사용하는 키 변수임. */
	public final String SESSION_MAP = "__SESSION_MAP__";
	
	/* request header */
	public final String HEADER_MAP = "__HEADER_MAP__";
	
	/* 세션에서 해당 키의 값을 리턴, 없으면 공백을 리턴 */
	protected String getSession(Map _params, String sessionKey) {
		Object o = _params.get(SESSION_MAP);
		if(o!=null) {
			Map s = (Map)o;
			Object v = s.get(sessionKey);
			return v == null ? "":v.toString();
		}
		return "";
	}

	/* 세션에서 해당 키의 값을 리턴, 없으면 공백을 리턴 */
	protected String getHeader(Map _params, String sessionKey) {
		Object o = _params.get(HEADER_MAP);
		if(o!=null) {
			Map s = (Map)o;
			Object v = s.get(sessionKey);
			return v == null ? "":v.toString();
		}
		return "";
	}

	/* map을 복사하여 새로운 map을 리턴 */
	public Map cloneParams(Map params) {
		Map<String, Object> newP = new HashMap<String, Object>();
		newP.putAll(params);
		return newP;
	}
	
	public String getParameter(Map params, String key) {
		if(params.containsKey(key)) {
			return params.get(key).toString();
		}
		return "";
	}

	public Map getParameterMap(Map params, String key) {
		if(params.containsKey(key)) {
			return (Map)params.get(key);
		}
		return new HashMap();
	}
	

	public List getParameterList(Map params, String key) {
		if(params.containsKey(key)) {
			return (List)params.get(key);
		}
		return new ArrayList();
	}
	

	/* 세션에서 해당 키의 값을 리턴, 없으면 공백을 리턴 */
	protected void setSession(Map _params, String key, String val) {
		Map<String,String> s = null;
		Object o = _params.get(SESSION_MAP);
		if(o==null) {
			s = new HashMap<String,String>();
		} else {
			s =(Map)o;
		}
		s.put(key, val);
	}
	
	/* 세션에서 해당 키의 값을 리턴, 없으면 공백을 리턴 */
	protected void setHeader(Map _params, String key, String val) {
		Map<String,String> h = null;
		Object o = _params.get(HEADER_MAP);
		if(o==null) {
			h = new HashMap<String,String>();
		} else {
			h =(Map)o;
		}
		h.put(key, val);
	}
	
	public String pnull(Object v) {
        return v != null ? v.toString() : "";
    }

	public String pnull(Object v, String defaulVal) {
        return v != null ? v.toString() : defaulVal;
    }

	public String pnull(String v) {
        return v != null ? v : "";
    }

	public String pnull(String v, String defaulVal) {
        return v != null ? v : defaulVal;
    }

	public String pnull(Map params, String k) {
		Object v = params.get(k);
        return v != null ? v.toString() : "";
    }

	public String pnull(Map params, String k, String defaulVal) {
		Object v = params.get(k);
        return v != null ? v.toString() : defaulVal;
    }
	
	// ipnum 
	public int ipnull(String p) {
        return p != null ? Integer.parseInt(p.toString()) : -1;
    }

	public int ipnull(String p, int defaulVal) {
        return p != null ? Integer.parseInt(p.toString()) : defaulVal;
    }

	public int ipnull(Map params, String key) {
		Object v = params.get(key);
        return v != null ? Integer.parseInt(v.toString()) : -1;
    }

	public int ipnull(Map params, String key, int defaulVal) {
		Object v = params.get(key);
        return v != null ? Integer.parseInt(v.toString()) : defaulVal;
    }

//	// pint
//	public int pint(String p) {
//        return p != null ? Integer.parseInt(p.toString()) : -1;
//    }
//
//	public int pint(String p, int defaulVal) {
//        return p != null ? Integer.parseInt(p.toString()) : defaulVal;
//    }
//
//	public int pint(Map params, String key) {
//		Object v = params.get(key);
//        return v != null ? Integer.parseInt(v.toString()) : -1;
//    }
//	
//	public int pint(Map params, String key, int defaulVal) {
//		Object v = params.get(key);
//        return v != null ? Integer.parseInt(v.toString()) : defaulVal;
//    }
//	
//	// plong
//	public long plong(String p) {
//        return p != null ? Long.parseLong(p.toString()) : -1;
//    }
//
//	public long plong(String p, long defaulVal) {
//        return p != null ? Long.parseLong(p.toString()) : defaulVal;
//    }
//
//	public long plong(Map params, String key) {
//		Object v = params.get(key);
//        return v != null ? Long.parseLong(v.toString()) : -1;
//    }
//	
//	public long plong(Map params, String key, long defaulVal) {
//		Object v = params.get(key);
//        return v != null ? Long.parseLong(v.toString()) : defaulVal;
//    }
	
	
	// pnull
	public void pnullPut(Map params, String k, String defaulVal) {
		Object k1 = params.get(k);
        if(k1==null || k1.toString().trim().length()==0) {
        	params.put(k, defaulVal);
        }
    }
	
	// pnull
	public void pnullUpdate(Map params, String k, String updateVal) {
        params.put(k, updateVal);
    }

	/** k의 값이  compareVal와 일치하면 k의 값을 리턴, 다르면 wrongVal을 리턴 */
	public String pcompare(Map params, String k, String compareVal, String wrongVal) {
		Object v = params.get(k);
        return v != null ? v.toString().equalsIgnoreCase(compareVal) ? v.toString():wrongVal : wrongVal;
    }

	/** k의 값이  compareVal와 일치하면 rightVal(null 이면 k의 값)을 리턴, 다르면 wrongVal(null 이면 k의 값)을 리턴 */
	public String pcompare(Map params, String k, String compareVal, String rightVal, String wrongVal) {
		// String isManChurch  = pcompare(_params, "man_member_type",   "3", "-", null);
		String v = pnull(params, k);
        return v.equalsIgnoreCase(compareVal) ? rightVal:wrongVal;
    }

	/** 특정키를 다른키로 변경한다. */
	public int pchange(Map params, String oldKeyName, String newKeyName) {
		if(params.containsKey(newKeyName)) {
			return MAP_KEY_CHANGE_DUPLICATION;
		}
		Object v = params.remove(oldKeyName);
		if(v!=null) {
			params.put(newKeyName, v);
			return MAP_KEY_CHANGE_SUCESS;
		} else {
			return MAP_KEY_CHANGE_NOTEXISTS;
		}
    }
	
	//
	public static final int MAP_KEY_CHANGE_SUCESS  = 1;
	public static final int MAP_KEY_CHANGE_FAILURE = 0;
	public static final int MAP_KEY_CHANGE_NOTEXISTS = -1;
	public static final int MAP_KEY_CHANGE_DUPLICATION = -2;
	
	
}
