package com.instcar.android.service;


import com.instcar.android.config.Config;
import com.mycommonlib.android.common.util.HttpUtils;
import com.mycommonlib.common.entity.HttpRequest;
import com.mycommonlib.common.entity.HttpResponse;

public class GetAuthCodeService  extends BaseService{

	@Override
	public String request() {
		// TODO Auto-generated method stub
		String url = Config.apiserverusergetauthcode();
		HttpRequest request = new HttpRequest(url);
		this.postCookie(request);
		request.setParasMap(this.params);
		HttpResponse response  = HttpUtils.httpPost(request );
		
	    String s = response.getResponseBody();
	    super.SetCookie(response);
		return s;
	}



}
