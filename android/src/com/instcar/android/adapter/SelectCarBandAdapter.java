package com.instcar.android.adapter;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.instcar.android.R;
import com.instcar.android.entry.CarBand;
import com.mycommonlib.android.common.util.StringUtils;

public class SelectCarBandAdapter extends BaseAdapter {
	private List<CarBand> list = null;
	private Context mContext;
	private String preXilie="";
	
	public SelectCarBandAdapter(Context mContext, List<CarBand> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<CarBand> list){
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
		final CarBand mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.user_info_selectbandxilie_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		if(!this.preXilie.equals(this.list.get(position).xilie)&&this.list.get(position).xilie!=null){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(this.list.get(position).xilie);
			
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		this.preXilie = this.list.get(position).xilie;
		
		//根据position获取分类的首字母的Char ascii值
		
		viewHolder.tvTitle.setText(this.list.get(position).name);
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
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