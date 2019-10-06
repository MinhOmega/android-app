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
import com.instcar.android.thread.GetUserInfoThread;
import com.instcar.android.thread.LoginThread;
import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.StringUtils;
/**
 * 
 * @dujie
 * 选择出行路线的activity
 * 
 */
public class SelectWayActivity extends BaseActivity {

	private Handler mHandler = new Handler(){ 
        
        public void handleMessage(Message msg) { 
        	String data ;
        	NetEntry entry;
            switch (msg.what) { 
            case HandleConfig.AUTHLOGIN: 
              	data = msg.getData().getString("data");
            	entry = new NetEntry(data);
            	if(NetEntry.CODESUCESS.equals(entry.status)){//
            		showToastError("登陆成功了");
            		userdetail(entry.netentry.uid);
            	}else{//
            		showToastError(entry.msg);
            	}
            	
            	
            	
                break; 
             case HandleConfig.GETUSERINFO://获取用户信息成功
            	 data = msg.getData().getString("data");
             	entry = new NetEntry(data);
             	if(NetEntry.CODESUCESS.equals(entry.status)){//，
             		Intent i = new Intent(SelectWayActivity.this, MainActivity.class);
            		startActivity(i);
             	}else{
             		showToastError(entry.msg);
             	}
             	
            	 break; 
            } 
        }; 
    }; 
    
    EditText userName ;
    EditText password ;
    Button btnLogin ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_login);
		userName = (EditText) findViewById(R.id.textuserName);
		password = (EditText) findViewById(R.id.textpassword);
		password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String str = s.toString();
				if((StringUtils.vilidateLength(str)>=6)){
					setEditStatus(password, 1);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		userName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String str = s.toString();
				if((StringUtils.vilidateLength(str)==11)&&StringUtils.isPhoneNum(str)){
					setEditStatus(userName, 1);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});;
		
		btnLogin =(Button) findViewById(R.id.btn_login);
		
		btnLogin . setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String  suserName = userName.getText().toString();
				String  spassword = password.getText().toString();
				MyLog.d(suserName+spassword);
				if(!StringUtils.isEmpty(suserName)&&!StringUtils.isEmpty(spassword)&&StringUtils.isPhoneNum(suserName)){
					login(suserName, spassword);
					
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void login(String phone,String password){
		Map<String , String> param= new  HashMap<String, String>();
		param.put("phone",phone);
		param.put("password",password);
		
		CommonService service = new CommonService(Config.apiserveruserlogin());
		service.setParam(param);
		service.setAv(av);
		LoginThread thread = new LoginThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start(); 
		
	}
	public void userdetail(String uid){
		Map<String , String> param= new  HashMap<String, String>();
		param.put("uid",uid);
		
		CommonService service = new CommonService(Config.apiserveruserdetail());
		service.setParam(param);
		service.setAv(av);
		GetUserInfoThread thread = new GetUserInfoThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start(); 
		
	}
	
	

}
