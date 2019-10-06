package com.instcar.android.service;

import java.util.Iterator;
import java.util.Map;

import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.HttpUtils;
import com.mycommonlib.common.entity.HttpRequest;
import com.mycommonlib.common.entity.HttpResponse;

public class CommonService  extends BaseService{

	public String requrl;
	public CommonService(String url) {
		this.requrl = url;
		
	}
	@Override
	public String request() {
		// TODO Auto-generated method stub
		String url = this.requrl;
		HttpRequest request = new HttpRequest(url);
		MyLog.d("incar", url);
		this.postCookie(request);
		MyLog.d("incar", "====");
		
		request.setParasMap(this.params);
		HttpResponse response  = HttpUtils.httpPost(request );
		String s="{\"status\":500,\"data\":[],\"msg\":\"网络错误\"}";
		if(response!=null&&response.getResponseCode()==200){
			
			s = response.getResponseBody();
			Map<String, Object> map = response.getResponseHeaders();
			
			Iterator<Map.Entry<String, Object>> ite = map.entrySet().iterator();
			while (ite.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>)ite.next();
				MyLog.d(entry.getKey(), entry.getValue().toString());
			}
			
			MyLog.d("incar", "header");
			super.SetCookie(response);
		}
		return s;
	}

	


}
