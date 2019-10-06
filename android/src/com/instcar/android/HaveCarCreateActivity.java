package com.instcar.android;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.NetEntry;
import com.mycommonlib.android.common.util.StringUtils;
import com.mycommonlib.android.common.util.TimeUtils;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

/**
 * 演示MapView的基本用法
 */
public class HaveCarCreateActivity extends BaseActivity {

	final static String TAG = "MainActivity";
	/**
	 *  MapView 是地图主控件
	 */
	/**
	 *  用MapController完成地图控制 
	 */
	EditText edittextdesc;
	Button buttonok;
	WheelView hours;
	WheelView mins;
	WheelView shangwu;
	WheelView people;
	RatingBar peopleRate;
	 String[] hoursarr=new String[24];
	 String[] minarr = new String[60];
	 String[] shangwuarr = {"今天","明天","后天"};
	 
	 private boolean isfirststart=true;//如果是第一次,启用定位，更新起点信息，然后就置为FALSE、；不会再定位
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意：请在试用setContentView前初始化BMapManager对象，否则会报错  
        setContentView(R.layout.have_car_create);  
        edittextdesc = (EditText) findViewById(R.id.create_room_desc);
        buttonok =(Button) findViewById(R.id.create_room_ok);
        peopleRate = (RatingBar) findViewById(R.id.ratingbar1);
        peopleRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				if(rating==0){
					rating=1;
					peopleRate.setRating(rating);
				}
				people.setCurrentItem((int) rating-1,true);	
				
			}
		});
        initBaseNav();
        navbar.setText(av.getCurrentLine().name);
        btn_right.setVisibility(View.GONE);
        btn_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HaveCarCreateActivity.this.finish();
			}
		});
        
         hours = (WheelView) findViewById(R.id.hour);
		
	       for (int i = 0; i < 24; i++) {
	    	   hoursarr[i]=i+"";
	   		}
	        DayArrayAdapter adapter = new DayArrayAdapter(this, hoursarr,R.layout.havr_car_create_whell_item,R.id.title);
	        adapter.setTextColor(0xFFFFFFFF);
	        adapter.setTextSize(25);
	        hours.setViewAdapter(adapter); 
	        hours.setCyclic(true);
	         
	        
	        for (int i = 0; i < 60; i++) {
				minarr[i]=i+1+"";
			}
	        
	        mins = (WheelView) findViewById(R.id.min);
	        
	        DayArrayAdapter adapter2 = new DayArrayAdapter(this, minarr,R.layout.havr_car_create_whell_item,R.id.title);
	        adapter2.setTextColor(0xFFFFFFFF);
	        adapter2.setTextSize(25);
	        mins.setViewAdapter(adapter2); 
	        
		
		mins.setCyclic(true);
		
		
	
		
		shangwu = (WheelView) findViewById(R.id.shangwu);
		
		DayArrayAdapter adapter3 = new DayArrayAdapter(this, shangwuarr,R.layout.havr_car_create_whell_item,R.id.title);
		adapter3.setTextColor(0xFFFFFFFF);
		adapter3.setTextSize(23);
		shangwu.setViewAdapter(adapter3); 
		shangwu.setCyclic(true);
		
		
		String[] peoplearr = {"1","2","3","4"};
		
		people = (WheelView) findViewById(R.id.people);
		
		DayArrayAdapter adapter4 = new DayArrayAdapter(this, peoplearr,R.layout.havr_car_create_whell_item,R.id.title);
		adapter4.setTextColor(0xFFFFFFFF);
		adapter4.setTextSize(25);
		people.setViewAdapter(adapter4); 
		people.setCyclic(true);
		people.setCurrentItem(3);
		people.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				peopleRate.setRating(newValue+1);
			}
		});
		
		btn_right.setVisibility(View.VISIBLE);
		btn_right.setImageResource(R.drawable.abtn_confirm_g);
		
		OnClickListener c =  new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar c = Calendar.getInstance();
				String year = String.valueOf(c.get(Calendar.YEAR));
				String month = String.valueOf(c.get(Calendar.MONTH) + 1);
				String shour = hoursarr[hours.getCurrentItem()];
				String smin = minarr[mins.getCurrentItem()];
				float people = peopleRate.getRating();
				int day = shangwu.getCurrentItem();
				String strdar = String.valueOf(c.get(Calendar.DAY_OF_MONTH)+day-1);
				
				String desc  = edittextdesc.getText().toString();
				showProcessDialog("正在进入房间");
				addfav(av.getCurrentLine().id);
				createRoom(av.getCurrentLine(), desc, year+"-"+month+"-"+strdar+" "+shour+":"+smin, people+"");
			}
		};
		btn_right.setOnClickListener(c);
		buttonok.setOnClickListener(c);
		
		initHandle();
    }
    void initHandle(){
    	
    	mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case HandleConfig.CREATEROOM:
					dismissProcessDialog();
					NetEntry entry = decodePointList(msg.getData().getString(
							"data"));
					if (NetEntry.CODESUCESS.equals(entry.status)) {
						Intent i = new Intent(HaveCarCreateActivity.this, MessageActivity.class);
						String roomid=entry.netentry.id;
						String openfire = entry.netentry.openfire;
						if(StringUtils.isEmpty(roomid)||StringUtils.isEmpty(openfire)){
							
							showToastError("创建房间失败");
						}else{
							i.putExtra("roomid", roomid);
							i.putExtra("openfire", openfire);
							
							startActivity(i);
							HaveCarCreateActivity.this.finish();
						}
					} else {

						showToastError(entry.msg);
					}

				
					break;
				default:
					break;
				}

			}

		};
    	
    	
    }

    
    @Override
    protected void onPause() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
    	 */
     //   mMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
    	 */
      //  mMapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
    	 */
       // mMapView.destroy();
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    //	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    //	mMapView.onRestoreInstanceState(savedInstanceState);
    }


    private class DayArrayAdapter extends AbstractWheelTextAdapter {

    	String[] items ;
    	protected DayArrayAdapter(Context context,String[] arr,int itemResource, int itemTextResource) {
    		super(context, itemResource,  itemTextResource);
    		this.items = arr;
    		// TODO Auto-generated constructor stub
    	}
		protected DayArrayAdapter(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getItemsCount() {
			// TODO Auto-generated method stub
			return items.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			// TODO Auto-generated method stub
			return items[index];
		}
    }
    
    private String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);
		return sbBuffer.toString();
	}

        // Count of days to be shown
}

