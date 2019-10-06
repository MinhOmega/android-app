package com.instcar.android.floatwindow;


import java.util.IdentityHashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.instcar.android.R;
import com.instcar.android.config.Config;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.MessagePerson;
import com.instcar.android.entry.Room;
import com.instcar.android.service.CommonService;
import com.instcar.android.thread.CommonThread;

public class FloatWindowBigViewPassager extends FloatWindowBigView  implements OnClickListener{

	
	
	/**
	 * 记录大悬浮窗的宽度
	 */
	/**
	 * 记录大悬浮窗的高度
	 */
	
	public boolean isBianji=false;

	
	
	
	public FloatWindowBigViewPassager(final Context context, MessagePerson owner,MessagePerson currentPerson,Room room) {
		super(context, owner, currentPerson, room);
		opt.setOnClickListener(this);
		opt.setText("加入房间");
	}

	

	void initViewStatus(){
		opt.setOnClickListener(this);
		
		
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.room_button_opt:
			if(personlist.get(currentPerson.phone)!=null){
				
				
				
			}
			
			
			break;

		default:
			break;
		}
		
	}

	
}
