package com.instcar.android;


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

import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.StringUtils;
/**
 * 
 * @dujie
 * 
 */
public class LoginActivity extends BaseActivity {

    
    EditText userName ;
    EditText password ;
    Button btnLogin ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 mHandler = new Handler(){ 
		        
		        public void handleMessage(Message msg) { 
		        	String data ;
		        	NetEntry entry;
		        	
		            switch (msg.what) { 
		            case HandleConfig.AUTHLOGIN: 
		            	dismissProcessDialog();
		              	data = msg.getData().getString("data");
		            	entry = new NetEntry(data);
		            	if(NetEntry.CODESUCESS.equals(entry.status)){//
		            		//showToastError("登陆成功了");
		            		av.setUid(entry.netentry.id);
		            		userdetail(entry.netentry.id);
		            	}else{//
		            		showToastError(entry.msg);
		            	}
		            	
		            	
		            	
		                break; 
		             case HandleConfig.GETUSERINFO://获取用户信息成功
		            	 data = msg.getData().getString("data");
		             	entry = new NetEntry(data);
		             	dismissProcessDialog();
		             	if(NetEntry.CODESUCESS.equals(entry.status)){//，
		             		dismissProcessDialog();
		             		Intent i = new Intent(LoginActivity.this, MainActivity.class);
		            		startActivity(i);
		            		LoginActivity.this.finish();
		            	
		            		av.setUserData(entry.netentry);
		             	}else{
		             		showToastError(entry.msg);
		             	}
		             	
		            	 break; 
		            } 
		        }; 
		    }; 
		setContentView(R.layout.layout_activity_login);
		initBaseNav();
		navbar.setText("登陆");
		
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
					showProcessDialog("正在登陆中..");
					
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

	
	

}
