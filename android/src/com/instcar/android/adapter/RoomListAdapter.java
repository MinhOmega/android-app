package com.instcar.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.instcar.android.R;
import com.instcar.android.entry.Line;
import com.instcar.android.entry.NetDataEntry;
import com.instcar.android.entry.Room;
import com.instcar.android.util.CommonUtil;
import com.instcar.android.view.CarTouXiangImg;

public class RoomListAdapter extends BaseAdapter {
	private List<Room> list = null;
	private Context mContext;
	
	public RoomListAdapter(Context mContext, List<Room> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<Room> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		Room entry = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.room_list_item, null);
			
			viewHolder.touxiang = (CarTouXiangImg) view.findViewById(R.id.room_list_user_touxiang);
			
			viewHolder.roomUser = (TextView) view.findViewById(R.id.room_list_roomuser);
			viewHolder.roomdesc = (TextView) view.findViewById(R.id.room_list_desc);
			viewHolder.roomkongwei = (TextView) view.findViewById(R.id.room_list_kongwei);
			viewHolder.StartTime = (TextView) view.findViewById(R.id.room_list_tip_time);
			viewHolder.createTime = (TextView) view.findViewById(R.id.room_list_fabushijian);
			viewHolder.cretateDay = (TextView) view.findViewById(R.id.room_list_tip_day);
			viewHolder.entry = entry;
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.roomUser.setText(entry.roomUsername);
		viewHolder.roomdesc.setText(entry.roomdesc);
		viewHolder.roomkongwei.setText(entry.roomkongwei);
		viewHolder.StartTime.setText(entry.StartTime);
		viewHolder.createTime.setText(CommonUtil.getTimedescByTime(entry.createTime));
		viewHolder.touxiang.setNetImageSource(entry.roomUserpic);
		
		//根据position获取分类的首字母的Char ascii值
		
		return view;

	}
	


	final static class ViewHolder {
		CarTouXiangImg touxiang;
		
		TextView cretateDay;
		TextView roomUser;
		TextView roomdesc;
		TextView  roomkongwei;
		TextView StartTime;
		TextView createTime;
		
		Room entry;
	}



	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}


}