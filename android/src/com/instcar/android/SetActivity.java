package com.instcar.android;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.NetDataEntry;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.util.MyLog;

/**
 * 
 * @dujie
 * 
 */
@SuppressLint("ResourceAsColor")
public class SetActivity extends BaseActivity implements OnClickListener {

	public static final int SET1 =1;
	public static final int SET2 =2;
	public static final int SET3 =3;
	public static final int SET4 =4;

    Button addcar;
    View viewlayout;
    View editlayout;
    int status;
    
    TextView tedit1;
    TextView tedit2;
    TextView tedit3;
    TextView tedit4;
    TextView tedit5;
    
    TextView tview1;
    TextView tview2;
    TextView tview3;
    TextView tview4;
    TextView tview5;
    TextView textview_yanzheng2;
    
    LinearLayout layout_renzheng;
    
    LinearLayout ledit1;
    LinearLayout ledit2;
    LinearLayout ledit3;
    LinearLayout ledit4;
    LinearLayout ledit5;
    ImageView imageview_touxiang;
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_detail);
		btn_right = (ImageButton) findViewById(R.id.btn_right);
		btn_left = (ImageButton) findViewById(R.id.btn_left);
		navbar  =(TextView) findViewById(R.id.text_top_bar);
		viewlayout = findViewById(R.id.viewlayout);
		editlayout = findViewById(R.id.editlayout);
		
		navbar.setText(av.getUserData().name);
		initview();
		
		addcar  = (Button) findViewById(R.id.add_verfy_car);
		addcar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SetActivity.this, VerfyCarActivity.class);
				startActivity(i);
				
			}
		});
	//btn_right.setVisibility(View.GONE);
		btn_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rightBtnStatus(status);
				
				if(status==0){//保存用户数据
					
					
				}
				
			}
		});
		btn_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SetActivity.this.finish();
			}
		});
	
		initHandle();
		
getUrlImage(av.getUserData().headpic+"_a.jpg");
	}
	
	void initHandle(){
		 mHandler = new Handler(){ 
		        
		        public void handleMessage(Message msg) { 
		        	String data ;
		        	NetEntry entry;
		        	
		            switch (msg.what) { 
		           
		            case HandleConfig.SETHEADIMG: 
		            	imageview_touxiang .setImageDrawable(image);
		            		
		            	
		            	
		            	
		            	break; 
		          
	
		            } 
		        }; 
		    }; 
		
	}
	private void initview(){
		tedit1  =(TextView) findViewById(R.id.tedit1);
		tedit1.setText(av.getUserData().signature);
		tedit2  =(TextView) findViewById(R.id.tedit2);
		tedit1.setText(av.getUserData().comp_addr);
		tedit3  =(TextView) findViewById(R.id.tedit3);
		tedit3.setText(av.getUserData().home_addr);
		tedit4  =(TextView) findViewById(R.id.tedit4);
		tedit4.setText(av.getUserData().phone);
		tedit5  =(TextView) findViewById(R.id.tedit5);
		tedit5.setText("33条");
		
		tview1 = (TextView) findViewById(R.id.tview1);
		tview1.setText(av.getUserData().phone);
		
		textview_yanzheng2 = (TextView) findViewById(R.id.textview_yanzheng2);
		tview2 = (TextView) findViewById(R.id.tview2);
		if(av.getUserData().ischeck.equals(NetDataEntry.USER_STATE_RENZHENG)){
			tview2.setText("已认证");
			Resources res = getResources();
			Drawable myImage = res.getDrawable(R.drawable.ic_agree_ok); 
			textview_yanzheng2.setCompoundDrawablesWithIntrinsicBounds(myImage, null, null, null);
			
		}else if(av.getUserData().ischeck.equals(NetDataEntry.USER_STATE_RENZHENGING)){
			tview2.setText("正在认证中");
			tview2.setTextColor(R.color.red);
			Resources res = getResources();
			Drawable myImage = res.getDrawable(R.drawable.ic_next); 
			textview_yanzheng2.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
		}else{
			tview2.setText("未认证");
			Resources res = getResources();
			Drawable myImage = res.getDrawable(R.drawable.ic_next); 
			textview_yanzheng2.setText("");
			textview_yanzheng2.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
		}
		
		
		
		
		tview3 = (TextView) findViewById(R.id.tview3);
		tview3.setText("42条");
		tview4 = (TextView) findViewById(R.id.tview4);
		if("1".equals(av.getUserData().show_comp_addr)){
			tview4.setText("保密");
		}else{
			tview4.setText(av.getUserData().comp_addr);
			
		}
		tview5 = (TextView) findViewById(R.id.tview5);
		if("1".equals(av.getUserData().show_home_addr)){
			tview4.setText("保密");
		}else{
			tview4.setText(av.getUserData().home_addr);
			
		}
		
		
		ledit1 = (LinearLayout) findViewById(R.id.ledit1);
		ledit1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SetActivity.this, SetEdit1Activity.class);
				startActivityForResult(i, SetActivity.SET1);
			}
		});
		ledit2 = (LinearLayout) findViewById(R.id.ledit2);
	
		ledit3 = (LinearLayout) findViewById(R.id.ledit3);
		ledit4 = (LinearLayout) findViewById(R.id.ledit4);
		ledit5 = (LinearLayout) findViewById(R.id.ledit5);
		layout_renzheng = (LinearLayout) findViewById(R.id.layout_renzheng);
		layout_renzheng.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(av.getUserData().ischeck.equals(NetDataEntry.USER_STATE_RENZHENG)){
					showToastError("已经认证成功");
				}else if(av.getUserData().ischeck.equals(NetDataEntry.USER_STATE_RENZHENGING)){
					showToastError("正在认证中，请等待我们工作人员认证完成");
					
				}else{
					Intent i = new Intent(SetActivity.this, UploadIdCardImgActivity.class);
					startActivity(i);
				}
			}
		});
		
		imageview_touxiang = (ImageView) findViewById(R.id.imageview_touxiang);
		
		
	}
	private void rightBtnStatus(int status){
		MyLog.d(status+"'");
		switch (status) {
		case 0:

			editlayout.setVisibility(View.VISIBLE);
			viewlayout.setVisibility(View.GONE);
			btn_right.setImageResource(R.drawable.btn_save);
			SetActivity.this.status=1;
			break;
		case 1:
			editlayout.setVisibility(View.GONE);
			viewlayout.setVisibility(View.VISIBLE);
			btn_right.setImageResource(R.drawable.btn_edit);
			SetActivity.this.status=0;
			break;

		
		}

		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
	}

	
	

}
