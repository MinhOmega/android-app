package com.instcar.android;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.instcar.android.config.Config;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.service.CommonService;
import com.instcar.android.thread.AuthPhoneThread;
import com.instcar.android.thread.GetPhoneCodeThread;
import com.instcar.android.thread.UserRegisterThread;
import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.StringUtils;
/**
 * 
 * @dujie
 * 
 */
public class RegisterActivity extends BaseActivity {
	
	
    
    EditText phone ;
    EditText phonecode ;
    EditText password ;
    
    Button btnCheckPhoneCode ;
    Button btnSubmit ;
    
    String phonemun ;
	String authcode ;
	String smsid ;
	String passwordt ;
    private boolean isPhoneOk=false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_register);
		mHandler = new Handler(){ 
			public int count=60;
			
	        public void handleMessage(Message msg) { 
	            String  data;
	            NetEntry entry;
	           
	        	switch (msg.what) { 
	            
	            case HandleConfig.AUTHPHONE://验证手机号码有效性返回的数据
	            	 dismissProcessDialog();
	            	data = msg.getData().getString("data");
	            	 entry = new NetEntry(data);
	            	if(NetEntry.CODESUCESS.equals(entry.status)){
	            		isPhoneOk=true;
	            		setEditStatus(phone, 1);
	            	}else{
	            		showToastError(entry.msg);
	            		isPhoneOk=false;
	            		setEditStatus(phone, 0);
	            	}
	            	break;
	            case HandleConfig.GETAUTHCODE://获取短信验证码
	            	 dismissProcessDialog();
	            	 data = msg.getData().getString("data");
	            	 entry = new NetEntry(data);
	            	 System.out.println(entry.toString());
	             	if(NetEntry.CODESUCESS.equals(entry.status)){//已经发送成功了
	             		smsid = entry.netentry.smsid;
	             		this.sendEmptyMessageDelayed(HandleConfig.AUTHCODE_DELAY, 1000);
	            	}else{//发送不成功
	            		showToastError(entry.msg);
	            	}
	                break;
	                
	            case HandleConfig.USERREGISTER://用户注册
	            	 dismissProcessDialog();
	            	data = msg.getData().getString("data");
	            	entry = new NetEntry(data);
	            	if(NetEntry.CODESUCESS.equals(entry.status)){//注册成功了，
	            		
	            		showToastError(entry.msg);
	            	}else{//注册不成功
	            		showToastError(entry.msg);
	            	}
	            	break; 
	                
	            case HandleConfig.AUTHCODE_DELAY:
	            	count--;
	            	//if(count==60){
	            		btnCheckPhoneCode.setEnabled(false);
	            	//}
	            	btnCheckPhoneCode.setText("("+count+")秒之后重新发送");
	            	if(count>1){
	            		this.sendEmptyMessageDelayed(HandleConfig.AUTHCODE_DELAY, 1000);
	            	}
	            	if(count==1){
	            		btnCheckPhoneCode.setEnabled(true);
	            		count=60;
	            		btnCheckPhoneCode.setText(R.string.registergetauthcode);
	            		btnCheckPhoneCode.setEnabled(true);
	            	}
	            	break;
	              
	            } 
	        }; 
	    }; 
		
	    initBaseNav();
		phone = (EditText) findViewById(R.id.textuserName);
		phonecode = (EditText) findViewById(R.id.textphonecode);
		password = (EditText) findViewById(R.id.textpassword);
		btnCheckPhoneCode = (Button) findViewById(R.id.btn_getphonecode);
		btnSubmit = (Button) findViewById(R.id.btn_login);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				phonemun = phone.getText().toString();
				authcode = phonecode.getText().toString();
				passwordt = password.getText().toString();
				if((StringUtils.vilidateLength(phonemun)!=11)||!StringUtils.isPhoneNum(phonemun)){
					showToastError("手机号码不正确");
					return;
				}
				if((StringUtils.vilidateLength(authcode)!=6)){
					showToastError("请输入正确的手机验证码");
					return;
				}
				if(StringUtils.isEmpty(passwordt)||passwordt.length()<6){
					showToastError("密码太短了");
					return;
				}
				showProcessDialog("正在注册，请稍后");
				userRegister(phonemun, smsid, authcode, passwordt);
			}
		});
		
		btnCheckPhoneCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String str = phone.getText().toString();
				if((StringUtils.vilidateLength(str)==11)&&StringUtils.isPhoneNum(str)){
					showProcessDialog("正在获取验证码");
					getAuthCode(str);
				}else{
					showToastError("亲，输入正确的手机号码");
					
				}
				
			}
		});
		
		phone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String str = s.toString();
				 MyLog.d("incar", str);
				 isPhoneOk=false;
				 setEditStatus(phone, 0);
				if((StringUtils.vilidateLength(str)==11)&&StringUtils.isPhoneNum(str)){
					AuthPhone(str);
					
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
