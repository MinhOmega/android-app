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

public class LineListAdapter extends BaseAdapter {
	private List<Line> list = null;
	private Context mContext;
	
	public LineListAdapter(Context mContext, List<Line> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<Line> list){
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
		Line entry = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.map_point_list_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.point_list_item_title);
			viewHolder.tvNumber = (TextView) view.findViewById(R.id.point_list_item_number);
			viewHolder.entry = entry;
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tvTitle .setText(entry.name);
		viewHolder.tvNumber.setText( position+1+"");
		
		//根据position获取分类的首字母的Char ascii值
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvNumber;
		TextView tvTitle;
		Line entry;
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