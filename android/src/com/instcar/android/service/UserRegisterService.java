package com.instcar.android.service;

import java.util.Iterator;
import java.util.Map;

import com.instcar.android.config.Config;
import com.mycommonlib.android.common.util.HttpUtils;
import com.mycommonlib.common.entity.HttpRequest;
import com.mycommonlib.common.entity.HttpResponse;

public class UserRegisterService  extends BaseService{

	@Override
	public String request() {
		// TODO Auto-generated method stub
		String url = Config.apiserveruserregister();
		HttpRequest request = new HttpRequest(url);
		this.postCookie(request);
		
		request.setParasMap(this.params);
		HttpResponse response  = HttpUtils.httpPost(request );
		
	    String s = response.getResponseBody();
	    Map<String, Object> map = response.getResponseHeaders();
     
        Iterator<Map.Entry<String, Object>> ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>)ite.next();
        }
	    
	    super.SetCookie(response);
		return s;
	}



}
