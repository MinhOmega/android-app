package com.instcar.android.floatwindow;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.instcar.android.R;
import com.instcar.android.entry.MessagePerson;
import com.instcar.android.entry.Room;

public class FloatWindowBigView extends LinearLayout {

	/**
	 * 记录大悬浮窗的宽度
	 */
	public static int viewWidth;

	public ImageView touxiang_owner;

	public TextView name_owner;
	public SeatView user1;
	public SeatView user2;
	public SeatView user3;
	public SeatView user4;
	
	

	
	public Button opt;
	public static int USER_ROLE_SIJI=1;
	public static int USER_ROLE_CHENGKE=2;
	public int currentUserRole=2;
	public boolean isJiaru=false;

	public MessagePerson currentPerson;
	public Map<String, MessagePerson> personlist = new HashMap<String, MessagePerson>();;
	public List<MessagePerson> person;
	public MessagePerson owner;
	public Room room;

	/**
	 * 记录大悬浮窗的高度
	 */
	public static int viewHeight;

	public FloatWindowBigView(final Context context, MessagePerson owner,MessagePerson currentPerson,Room room) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		View view = findViewById(R.id.big_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		touxiang_owner = (ImageView) view.findViewById(R.id.room_owner_photo);
		user1 = (SeatView) view.findViewById(R.id.room_user1);
		user2 = (SeatView) view.findViewById(R.id.room_user2);
		user3 = (SeatView) view.findViewById(R.id.room_user3);
		user4 = (SeatView) view.findViewById(R.id.room_user4);

		name_owner = (TextView) findViewById(R.id.room_owner_name);

		opt = (Button) findViewById(R.id.room_button_opt);
		

	}

	
	
	public void setUserRole(int role){
		this.currentUserRole=role;
	}


	
	public void UpdateRoomPerson(List<MessagePerson> person) {
		this.person =person;
		personlist.clear();
		if(person!=null&&person.size()>0){
			for (int i = 0; i < person.size(); i++) {
				MessagePerson p = person.get(i);
				personlist.put(p.phone, p);
				switch (i) {
				case 0:
					user1.setPerson(p);
					user1.status=SeatView.STATUS_HAVE;
					break;
				case 1:
					user2.setPerson(p);
					user2.status=SeatView.STATUS_HAVE;
					break;
				case 2:
					user3.setPerson(p);
					user3.status=SeatView.STATUS_HAVE;
					break;
				case 3:
					user4.setPerson(p);
					user4.status=SeatView.STATUS_HAVE;
					break;
				}
				
			}
			
		}
		
		
	}

	/*
	 * 设置房主信息
	 * 头像先忽略
	 */
	public void setOwnerInfo(){
		name_owner.setText(owner.name);
	}



}
