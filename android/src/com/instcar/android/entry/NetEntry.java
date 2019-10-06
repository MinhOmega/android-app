package com.instcar.android.entry;

import java.util.ArrayList;
import java.util.List;

import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.JSONUtils;
import com.mycommonlib.android.common.util.StringUtils;

public class NetEntry {
	
	public static final String CODESUCESS="200";
	public static final String CODEUNAUTHORIZED="401";//未授权登陆
	
	public String status;
	public String msg;
	public String data;
	public NetDataEntry netentry;
	public NetEntry() {
		
	}
	public NetEntry(String jsonString) {
		if(!StringUtils.isEmpty(jsonString)){
			this.status = JSONUtils.getString(jsonString, "status", "500");
			this.msg = JSONUtils.getString(jsonString, "msg", "请求失败");
			this.netentry = new NetDataEntry(JSONUtils.getString(jsonString, "data", ""));
		}else{
			this.status = "500";
			this.msg = "请求失败";
		}
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		MyLog.d(status+"===="+msg);
		return super.toString();
	}
	
	
}
