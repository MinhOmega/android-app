package com.instcar.android.floatwindow;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.instcar.android.R;
import com.instcar.android.entry.MessagePerson;
import com.instcar.android.entry.Room;
import com.instcar.android.util.MyLog;

public class FloatWindowBigViewDriver extends FloatWindowBigView  implements OnClickListener{

	
	
	/**
	 * 记录大悬浮窗的宽度
	 */

	/**
	 * 记录大悬浮窗的高度
	 */

	public boolean isBianji=false;
	public FloatWindowBigViewDriver(final Context context, MessagePerson owner,MessagePerson currentPerson,Room room) {
		super(context, owner, currentPerson, room);

		opt.setOnClickListener(this);

	}


	void initViewStatus(){
		opt.setOnClickListener(this);
		
		
	}
	
	void rechangeOptBUtton(int status){
		if(status==1){
			
			
		}
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.room_button_opt:
			if(isBianji==true){
				isBianji=false;
				opt.setText("编辑座位");
				opt.setBackgroundResource(R.drawable.btn_green_v2);
				if(user1.status==SeatView.STATUS_KONGZUO){
					
					user1.setCloseStatus();
				}
				if(user2.status==SeatView.STATUS_KONGZUO){
					
					user2.setCloseStatus();
				}
				if(user3.status==SeatView.STATUS_KONGZUO){
					
					user3.setCloseStatus();
				}
				if(user4.status==SeatView.STATUS_KONGZUO){
					
					user4.setCloseStatus();
				}
			}else{
				isBianji=true;
				opt.setText("确定");
				opt.setBackgroundResource(R.drawable.btn_green_v2);
				if(user1.status==SeatView.STATUS_CLOSE){
					user1.setKongweiStatus();
				}
				if(user2.status==SeatView.STATUS_CLOSE){
					user2.setKongweiStatus();
				}
				if(user3.status==SeatView.STATUS_CLOSE){
					user3.setKongweiStatus();
				}
				if(user4.status==SeatView.STATUS_CLOSE){
					user4.setKongweiStatus();
				}
			}
			
			break;

		default:
			break;
		}
		
	}
}
