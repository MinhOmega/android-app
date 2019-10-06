package com.instcar.android.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.instcar.android.util.ApplicationVar;
import com.instcar.android.util.MyLog;
import com.mycommonlib.common.entity.HttpRequest;
import com.mycommonlib.common.entity.HttpResponse;

public abstract class BaseService {

	public ApplicationVar av;
	public Context context;
	
	
	public Map<String, String> params;
	
	public void setContext(Context c){
		this.context = c;
	}
	public void setAv(ApplicationVar av){
		this.av = av;
	}
	public void setParam(Map<String, String> params){
		
	     
	        Iterator<Map.Entry<String, String>> ite = params.entrySet().iterator();
	        while (ite.hasNext()) {
	            Map.Entry<String, String> entry = (Map.Entry<String, String>)ite.next();
	            
	            MyLog.d("param key:"+entry.getKey()+"==value :"+entry.getValue());
	            
	      
	            //MyLog.d(entry.getKey(), entry.getValue().toString());
	        }
		this.params = params;
	}
	
	public abstract String request();
	/**
	 * 
	 * 存储cookie
	 * @param response
	 */
	public void SetCookie(HttpResponse response){
		if(response!=null){
			List <String> cookie  = response.getCookie();
			StringBuffer s = new StringBuffer();
			if(cookie!=null){
				for (int i = 0; i < cookie.size(); i++) {
					if(!cookie.get(i).startsWith("path")){
						s.append(cookie.get(i));
						if(i<cookie.size()-1){
							s.append(";");
						}
					}
				}
			}
			av.setStringCookie(s.toString());
		}
	}
	/**
	 * 发送cookie
	 * 
	 * @param response
	 */
	public void postCookie(HttpRequest request){
		String cookie = av.getStringCookie();
		
		//MyLog.d("发送的cookie",cookie+"");
		request.setRequestProperty("Cookie", cookie);
		
	}
}
