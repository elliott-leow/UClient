package me.elliottleow.kabbalah.api.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cache {
	public static final int CLEAN_UP = 300*1000; // 5 mins
	@SuppressWarnings("serial")
	public static Map<String, String[]> playerCache = new LinkedHashMap<String, String[]> () {
	        @Override
	        protected boolean removeEldestEntry(@SuppressWarnings("rawtypes") final Map.Entry eldest) {
	        	//List<String> keys = new ArrayList<String>(this.keySet());
	        	for (Map.Entry<String,String[]> entry : Cache.playerCache.entrySet()) {
	        	if (Long.parseLong(entry.getValue()[1]) + CLEAN_UP < System.currentTimeMillis()) return true;
	        	}
	        	return false;
	        	
	            
	        }
	    };
	
	//player name, [json, expiry time]
	
	
 
    
	
}
