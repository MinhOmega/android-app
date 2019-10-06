package com.instcar.android.thread;

import android.os.Bundle;
import android.os.Message;

import com.instcar.android.config.HandleConfig;



public class EditUserhread  extends BaseThread implements Runnable{
	
	
	@Override
	public void run() {
		String data = this.service.request();
		Message message = Message.obtain();
		message.what = HandleConfig.EDITUSERINFO;
		Bundle bundle = new Bundle();
		bundle.putString("data", data);
		message.setData(bundle);
		handle.sendMessage(message);
		
	}
	

}
