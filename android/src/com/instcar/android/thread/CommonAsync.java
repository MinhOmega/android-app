package com.instcar.android.thread;

import java.util.Map;

import com.instcar.android.service.BaseService;
import com.instcar.android.service.LoginService;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * 
 * @author dj880618
 *map0 :参数
 *
 *map1：1
 */


public class CommonAsync extends AsyncTask<BaseService, Integer, String>{
	Handler h;
	

	
	@Override
	protected String doInBackground(BaseService... params) {
		// TODO Auto-generated method stub
		BaseService service = params[0];
		String s =service.request();
		return null;
	}
	 @Override  
    protected void onPostExecute(String result) {  
		 Message m = new Message();
		 m.what=1;
		 
		 
    }  
	 

	  
}
