package com.instcar.android;

import com.instcar.android.config.HandleConfig;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * 
 * 当前出行计划
 * 
 * @author dj880618
 * 
 */
@SuppressLint("NewApi")
public class TodayWayFragment extends BaseFrangment {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.main_framgment_2, container, false);
		
		mHandler = new Handler(){
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case HandleConfig.GETUSERROOMS:
					
					
					break;

				default:
					break;
				}
			}
			
		};
		getuserrooms(av.getUserData().id);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
}
