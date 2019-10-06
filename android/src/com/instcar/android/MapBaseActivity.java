package com.instcar.android;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.util.MyLog;

public class MapBaseActivity extends BaseActivity {

	// 定位图层
	locationOverlay myLocationOverlay = null;
	MapController mMapController = null;
	/**
	 * MKMapViewListener 用于处理地图事件回调
	 */
	MKMapViewListener mMapListener = null;
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = null;
	BDLocation currentLocation = null;
	LocationData locdata = null;
	MKSearch mMKSearch = null;// 搜索的变量
	 ArrayList<OverlayItem>  mItems = new ArrayList<OverlayItem>(); // 地图的搜索据点信息
	 
	
	 OverlayItem mCurItem = null;//当前据点信息
	
	 OverlayItem mStartItem=null;
	
	 OverlayItem mEndItem=null;
	

	
	public View popview;
	public PopupOverlay   pop  = null;
	
	TextView popviewtitle;
	Button popviewbuttonstart;
	Button popviewbuttonend;
	
	public MyOverlay mOverlay = null;

	
	public static int STATUS_VOICESEARCH=1;
	public static int STATUS_EDITINPUT=2;
	
	void initLocationSet() {
		MyLog.d("已经被调用");
		mLocationClient = av.mLocationClient; // 声明LocationClient类
		
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式/
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02	
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
		
		
		
		mLocationClient.setLocOption(option);
	//	mLocationClient.start();

	}

	/**
	 * 
	 方法摘要 int describeContents()
	 * 
	 * java.lang.String getAddrStr() 获取详细地址信息 double getAltitude() 获取高度信息，目前没有实现
	 * java.lang.String getCity() 获取城市 java.lang.String getCityCode()
	 * 
	 * java.lang.String getCoorType()
	 * 获取所用坐标系，目前没有实现，以locationClientOption里设定的坐标系为准 float getDerect() 已过时。
	 * float getDirection() 获取手机当前的方向 java.lang.String getDistrict() 获取区/县信息
	 * java.lang.String getFloor() 获取楼层信息,仅室内定位时有效 double getLatitude() 获取纬度坐标
	 * int getLocType() 获取定位类型: 参考 定位结果描述 相关的字段 double getLongitude() 获取经度坐标
	 * java.lang.String getNetworkLocationType()
	 * 在网络定位结果的情况下，获取网络定位结果是通过基站定位得到的还是通过wifi定位得到的 int getOperators() 获取运营商信息
	 * java.lang.String getPoi() 已过时。 java.lang.String getProvince() 获取省份 float
	 * getRadius() 获取定位精度 int getSatelliteNumber() gps定位结果时，获取gps锁定用的卫星数 float
	 * getSpeed() 获取速度，仅gps定位结果时有速度信息 java.lang.String getStreet() 获取街道信息
	 * java.lang.String getStreetNumber() 获取街道号码 java.lang.String getTime()
	 * server返回的当前定位时间 boolean hasAddr() 是否有地址信息 boolean hasAltitude()
	 * 
	 * boolean hasPoi() 已过时。 boolean hasRadius()
	 * 
	 * boolean hasSateNumber()
	 * 
	 * boolean hasSpeed() 是否包含速度信息
	 */
	public void getCurrentLocation() {
		MyLog.d("定位");
		if (mLocationClient.isStarted()) {
			//单次请求定位
			mLocationClient.requestLocation();
		} else{
			
			mLocationClient.start();
			
		}
	}



	void initSearch() {
		mMKSearch = new MKSearch();
		mMKSearch.init(mBMapMan, new MySearchListener());

	}

	void searchBycity(String city) {

	}

	class MySearchListener implements MKSearchListener {
		@Override
		public void onGetAddrResult(MKAddrInfo result, int iError) {
			// 返回地址信息搜索结果
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
			// 返回驾乘路线搜索结果
		}

		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
			// 返回poi搜索结果
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
			// 返回公交搜索结果
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
			// 返回步行路线搜索结果
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			// 返回公交车详情信息搜索结果
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult result, int type,
				int error) {
			// 在此处理短串请求返回结果.
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub
			// 返回联想词信息搜索结果

		}

	}



	/*
	 * 设置一个据点信息
	 */
	public void setMapPoint() {

	}

	public void setMyLocationLayer() {

		// 定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(locdata);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// 修改定位数据后刷新图层生效
		mMapView.refresh();
	}

	// 继承MyLocationOverlay重写dispatchTap实现点击处理
	public class locationOverlay extends MyLocationOverlay {

		public locationOverlay(MapView mapView) {
			super(mapView);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean dispatchTap() {
			// TODO Auto-generated method stub
			// 处理点击事件,弹出泡泡
			// popupText.setBackgroundResource(R.drawable.popup);
			// popupText.setText("我的位置");
			// pop.showPopup(BMapUtil.getBitmapFromView(popupText),
			// new GeoPoint((int)(locData.latitude*1e6),
			// (int)(locData.longitude*1e6)),
			// 8);
			return true;
		}

	}

	public void setStartPoint() {

	}
	void initOverLay(){
		  mOverlay = new MyOverlay(getResources().getDrawable(R.drawable.point_empty),mMapView);	
		  mMapView.getOverlays().add(mOverlay);
	}
	void initPopOverLay(){
		 pop = new PopupOverlay(mMapView,popListener);
	}
	
	

    PopupClickListener popListener = new PopupClickListener(){
		@Override
		public void onClickedPopup(int index) {
			if ( index == 0){
				//更新item位置
			      pop.hidePop();
			      GeoPoint p = new GeoPoint(mCurItem.getPoint().getLatitudeE6()+5000,
			    		  mCurItem.getPoint().getLongitudeE6()+5000);
			      mCurItem.setGeoPoint(p);
			      mOverlay.updateItem(mCurItem);
			      mMapView.refresh();
			}
			else if(index == 2){
				//更新图标
//				mCurItem.setMarker(getResources().getDrawable(R.drawable.nav_turn_via_1));
//				mOverlay.updateItem(mCurItem);
//			    mMapView.refresh();
			}
		}
    };
	 public class MyOverlay extends ItemizedOverlay{

			public MyOverlay(Drawable defaultMarker, MapView mapView) {
				super(defaultMarker, mapView);
			}
			

			@Override
			public boolean onTap(int index){
				OverlayItem item = getItem(index);
				mCurItem = item ;
				popviewtitle.setText(item.getTitle());
				
				pop.showPopup(popview,item.getPoint(),60);
				    
				    return true;
				
			}
			
			@Override
			public boolean onTap(GeoPoint pt , MapView mMapView){
				if (pop != null){
	                pop.hidePop();
				}
				return false;
			}
	    	
	    }
	 //刷新地图信息，

}
