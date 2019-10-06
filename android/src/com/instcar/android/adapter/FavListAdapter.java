package com.instcar.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.instcar.android.R;
import com.instcar.android.entry.Line;
import com.instcar.android.entry.NetDataEntry;
import com.instcar.android.entry.Room;
import com.instcar.android.view.CarTouXiangImg;
import com.mycommonlib.android.common.util.ToastUtils;

public class FavListAdapter extends BaseAdapter {
	private List<Line> list = null;
	private Context mContext;
	
	public FavListAdapter(Context mContext, List<Line> lineList) {
		this.mContext = mContext;
		this.list = lineList;
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
			view = LayoutInflater.from(mContext).inflate(R.layout.fav_way_list_item, null);
			viewHolder.qidian = (TextView) view.findViewById(R.id.fav_textview_qidian);
			viewHolder.qidiantip = (TextView) view.findViewById(R.id.fav_textview_qidian_tip);
			viewHolder.zhongdian = (TextView) view.findViewById(R.id.fav_textview_zhongdian);
			viewHolder.zhongdiantip = (TextView) view.findViewById(R.id.fav_textview_zhongdian_tip);
			viewHolder.btnhaveCar= (ImageView) view.findViewById(R.id.fav_btn_have_car);
			viewHolder.btnUserCar= (ImageView) view.findViewById(R.id.fav_btn_use_car);
			viewHolder.entry = entry;
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		String[] s =entry.name.split("-");
		if(s.length==1){
			s[1] =entry.name;
		}
		viewHolder.qidian.setText(s[0]);
		viewHolder.qidiantip.setText(entry.name);
		viewHolder.zhongdian.setText(s[1]);
		viewHolder.zhongdiantip.setText(entry.name);
		
		//根据position获取分类的首字母的Char ascii值
		
		viewHolder.btnhaveCar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtils.show(mContext, "有车");
			}
		});
		viewHolder.btnUserCar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtils.show(mContext, "用车");
			}
		});
		
		return view;

	}
	


	final static class ViewHolder {
		
		
		
		TextView qidian;
		TextView qidiantip;
		TextView zhongdian;
		TextView zhongdiantip;
		ImageView btnhaveCar;
		ImageView btnUserCar;
		
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