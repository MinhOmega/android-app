package com.instcar.android.service;

import java.util.Iterator;
import java.util.Map;

import com.instcar.android.config.Config;
import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.HttpUtils;
import com.mycommonlib.common.entity.HttpRequest;
import com.mycommonlib.common.entity.HttpResponse;

public class AuthPhoneService  extends BaseService{

	@Override
	public String request() {
		// TODO Auto-generated method stub
		String url = Config.apiserverusercheckusername();
		HttpRequest request = new HttpRequest(url);
		MyLog.d("incar", url);
		this.postCookie(request);
		
		request.setParasMap(this.params);
		HttpResponse response  = HttpUtils.httpPost(request );
		
	    String s = response.getResponseBody();
	    Map<String, Object> map = response.getResponseHeaders();
     
        Iterator<Map.Entry<String, Object>> ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>)ite.next();
            MyLog.d(entry.getKey(), entry.getValue().toString());
        }
	    
	    super.SetCookie(response);
		return s;
	}



}
