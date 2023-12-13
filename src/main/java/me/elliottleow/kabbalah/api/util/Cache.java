package me.elliottleow.kabbalah.api.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cache {
	public static final int CLEAN_UP = 300*1000; // 5 mins
	@SuppressWarnings("serial")
	public Map<String, String[]> playerCache = new LinkedHashMap<String, String[]> () {
	        @Override
	        protected boolean removeEldestEntry(@SuppressWarnings("rawtypes") final Map.Entry eldest) {
	        	List<String> keys = new ArrayList<String>(this.keySet());
	        	if (Integer.parseInt(this.get(keys.get(0))[1]) < System.currentTimeMillis()) return true;
	        	return false;
	            
	        }
	    };
	
	//player name, [json, expiry time]
	
	
 
    
	
}
