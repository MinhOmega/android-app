package com.instcar.android.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

import com.instcar.android.util.SdCard;
import com.mycommonlib.android.common.util.UploadUtil;

public class UploadImageService  extends BaseService{

	public String requrl;
	public Map<String, File> mapfile;
	public Map<String, String> params;
	public Bitmap photo;
	
	
	public UploadImageService(String url) {
		this.requrl = url;
		
	}
	@Override
	public String request() {
		// TODO Auto-generated method stub
		String url = this.requrl;
		String path = SdCard.savePictureBitmap(SdCard.getUserIconPath(), photo, "myphoto");
		mapfile = new HashMap<String, File>();
		mapfile.put("file_1", new File(path));
		String s="{\"status\":500,\"data\":[],\"msg\":\"网络错误\"}";
		 try {
			s= UploadUtil.post(url, params, mapfile);
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			s="{\"status\":500,\"data\":[],\"msg\":\"网络错误\"}";
		}
		return s;
	}

	


}
