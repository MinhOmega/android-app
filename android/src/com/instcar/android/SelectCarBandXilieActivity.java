package com.instcar.android;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.instcar.android.adapter.SelectCarBandAdapter;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.CarBand;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.sortlistview.CharacterParser;
import com.instcar.android.sortlistview.ClearEditText;
import com.instcar.android.sortlistview.PinyinComparator;
import com.instcar.android.sortlistview.SideBar;
import com.instcar.android.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.instcar.android.sortlistview.SortAdapter;
import com.instcar.android.sortlistview.SortModel;
import com.mycommonlib.android.common.util.JSONUtils;


public class SelectCarBandXilieActivity extends BaseActivity {
	private ListView sortListView;
	private SelectCarBandAdapter adapter;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	
	private String band;
	private String pic;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent mintent = getIntent();
		Bundle b =mintent .getExtras();
		band = b.getString("band");
		pic = b.getString("pic");
		
		setContentView(R.layout.user_info_selectcarbandxilie);
		initBaseNav();
		navbar.setText("编辑车辆信息");
		navbar2.setText("请选择您的车型");
		btn_right.setVisibility(View.GONE);
		
		initViews();
		initHandle();
		showProcessDialog("获取汽车类型中。。");
		requestcarxilie(pic);
		
	}

	private void initViews() {
	
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				//Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				//获取子分类
				Intent i = new Intent(SelectCarBandXilieActivity.this, UploadCarImgActivity.class);
				i.putExtra("name", ((CarBand)adapter.getItem(position)).name);
				i.putExtra("band", band);
				i.putExtra("xilie", ((CarBand)adapter.getItem(position)).xilie);
				i.putExtra("pic", pic);
				i.putExtra("carid", ((CarBand)adapter.getItem(position)).id);
				
				startActivity(i);
			}
		});
		List<CarBand> l = new ArrayList<CarBand>();
		adapter = new SelectCarBandAdapter(SelectCarBandXilieActivity.this, l);
		sortListView.setAdapter(adapter);
		
	}


	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		String[] a = getResources().getStringArray(R.array.car_picture);
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			sortModel.setPic(a[i]);
			
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}

	public void initHandle() {
		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				String data;
				NetEntry entry;
				List<CarBand> list= new ArrayList<CarBand>();
				switch (msg.what) {

				case HandleConfig.CARLIST:// 验证手机号码有效性返回的数据
					dismissProcessDialog();
					data = msg.getData().getString("data");
					String status = JSONUtils.getString(data, "status", "500");
					String message = JSONUtils.getString(data, "msg", "");
					if (NetEntry.CODESUCESS.equals(status)) {
					try {
						JSONObject object = new JSONObject(data);
						JSONArray arr = object.getJSONArray("data");
						if(arr!=null){
							  for(int i=0;i<arr.length();i++){ 
								    JSONObject jsonObject2 = (JSONObject)arr.opt(i); 
								  String xilie =   jsonObject2.getString("name");
								  JSONArray jsonarr = jsonObject2.getJSONArray("list");
								  for(int m =0;m<jsonarr.length();m++){
									  JSONObject jsonObject3 = (JSONObject)jsonarr.opt(m); 
									  String id= JSONUtils.getString(jsonObject3, "id", "0");
									  String name= JSONUtils.getString(jsonObject3, "name", "");
									  String picture= JSONUtils.getString(jsonObject3, "picture", "");
									  String series= JSONUtils.getString(jsonObject3, "series", "");
									  
									  CarBand c = new CarBand();
									  c.id = id;
									  c.name= name;
									  c.xilie = xilie;
									  c.picture = picture;
									  c.series = series;
									  list.add(c);
								  }
								  
							  }
							  adapter.updateListView(list);
							  
							
						}else{
							showToastError(message);
						} 
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
						
					} else {
					}
					break;

				}
			};
		};

	}

}
