package com.instcar.android;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.instcar.android.adapter.RoomListAdapter;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.Line;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.entry.Room;
import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.JSONUtils;
import com.mycommonlib.android.common.util.StringUtils;
/**
 * 
 * @dujie
 传入lineid 得到房间列表
 * 
 */
public class RoomListActivity extends BaseActivity {

    
	
	public Line line;
	public String lineId;
	public RoomListAdapter roomListAdapter;
	public List<Room> roomlist = new ArrayList<Room>();
	public ListView listview;
	public RoomListAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room_list);
		initBaseNav();
		btn_right.setVisibility(View.GONE);
		navbar.setText("");
		lineId = getIntent().getStringExtra("lineId");
		adapter = new RoomListAdapter(this, roomlist);
		listview = (ListView) findViewById(R.id.room_list);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//此时就需要加入聊天喽；
				Intent i = new Intent(RoomListActivity.this, MessageActivity.class);
				i.putExtra("roomid", roomlist.get(position).roomid);
				i.putExtra("openfire", roomlist.get(position).openfireid);
				startActivity(i);
			}
		});
		
		 mHandler = new Handler(){ 
		        
		        public void handleMessage(Message msg) { 
		        	String data ;
		        	NetEntry entry;
		            switch (msg.what) { 
		            case HandleConfig.QUERTLINEROOMS: 
		            	data = msg.getData().getString("data");
		            	entry = new NetEntry(data);
		            	if(NetEntry.CODESUCESS.equals(entry.status)){//
		            		//解析数据
		            		JSONObject json;
							try {
								json = new JSONObject(data);
								JSONArray dataarr = json.getJSONObject("data").getJSONArray("list");
								if(dataarr!=null&&dataarr.length()>0){
									//解析roomdata
									for (int i = 0; i < dataarr.length(); i++) {
										Room r = new Room();
										JSONObject tmp = dataarr.getJSONObject(i);
										//解析owner；
										JSONObject tmpowner = tmp.getJSONObject("user");
										JSONObject tmproom = tmp.getJSONObject("room");
										
										//解析room
										
										r.roomid = tmproom.getString("id");
										r.roomuserId = tmproom.getString("user_id");
										r.roomdesc = tmproom.getString("description");
										r.roomkongwei = tmproom.getString("max_seat_num");
										r.createTime = tmproom.getString("addtime");
										r.openfireid =tmproom.getString("openfire");
										r.roomuserId = tmpowner.getString("id");
										r.roomUsername = tmpowner.getString("name");
										r.roomUserpic= tmpowner.getString("headpic")+"_a.jpg";
										
										roomlist.add(r);
									}
									
								}
								adapter.updateListView(roomlist);
							} catch (JSONException e) {
								e.printStackTrace();
			            		showToastError("解析room列表出错");
							}
		            		
		            	}else{
		            		showToastError(entry.msg);
		            	}
		                break; 
		                case HandleConfig.QUERYLINEDETAIL:
		                	data = msg.getData().getString("data");
			            	entry = new NetEntry(data);
			            	if(NetEntry.CODESUCESS.equals(entry.status)){//
			            		JSONObject json;
								try {
									json = new JSONObject(data);
									JSONObject dataarr = json.getJSONObject("data");
									Line l = new Line();
									l.id = "";
								}catch (Exception e) {
									// TODO: handle exception
								}
			            		
			            	}else{
			            		RoomListActivity.this.finish();
			            		showToastError(entry.msg);
			            	}
		                	break;
		            } 
		        }; 
		    }; 
		    if(StringUtils.isEmpty(lineId)){
		    	showToastError("线路信息不存在");
		    }else{
		    	queryLineDetail(lineId);
		    	
		    	queryRoomListByLineid(lineId);
		    	
		    	
		    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	

}
