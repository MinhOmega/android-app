package com.instcar.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.instcar.android.entry.Line;
import com.instcar.android.entry.MyOverLayItem;
import com.instcar.android.util.MyLog;

public class MapBaseV2Activity extends BaseActivity {
	//定位相关
	locationOverlay myLocationOverlay = null; //定位图层
	BDLocation currentLocation = null;
	public LocationData locdata = null; 
	public LocationClient mLocationClient = null;
	public BDLocationListener myLocationListener = new MyLocationListenner();
	
	//地图相关
	MapView mMapView = null;
	MapController mMapController = null;
	//MKMapViewListener 用于处理地图事件回调
	MKMapViewListener mMapListener = null;
	BMapManager mBMapMan = null;
	
	
	public View popview;//弹出框
	public PopupOverlay   pop  = null;//
	
	
	TextView popviewtitle;
	Button popviewbuttonstart;
	Button popviewbuttonend;
	public MyOverlay mOverlay = null;
	
	BDLocation myBaidulocation;
	RouteOverlay  routeOverlay =null;
	MKRoute route = null;
	MKSearch mSearch = null;	// 搜索模块，也可去掉地图模块独立使用
	// 定位图层
	/**
	 * MKMapViewListener 用于处理地图事件回调
	 */
	


	MKSearch mMKSearch = null;// 搜索的变量
	 ArrayList<MyOverLayItem>  mItems = new ArrayList<MyOverLayItem>(); // 地图的搜索据点信息 //所有据点
	 Map<String,MyOverLayItem> mitemsMap =new HashMap<String,MyOverLayItem>();//搜索的据点，用map形式存储
	 Map<String,Object> mitemmap_beixuan_zhongdian =new HashMap<String,Object>();//备选重点
	 
	 

	 
	
	 OverlayItem mCurItem = null;//当前据点信息
	
	 OverlayItem mStartItem=null; //起点item
	
	 OverlayItem mEndItem=null; //重点item
	

	
	public static int STATUS_VOICESEARCH=1;
	public static int STATUS_EDITINPUT=2;
	 public boolean isfirststart=true;//如果是第一次,启用定位，更新起点信息，然后就置为FALSE、；不会再定位
	 public boolean ishandlocation=true;//如果是第一次,启用定位，更新起点信息，然后就置为FALSE、；不会再定位

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(null);
	}
	//初始化定位东西
	void initLocationSet() {
		
		mLocationClient = new LocationClient( this );
        locdata = new LocationData();
        mLocationClient.registerLocationListener( myLocationListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
       
        //定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		//设置定位数据
	    myLocationOverlay.setData(locdata);
	    //添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();

	}

	/*
	 * 获取到location信息后  刷新界面的回调，
	 */
	void LocationRefresh(BDLocation location){
		
	}
	
	void initRouteOverlay(){
		mSearch = new MKSearch();
		 mSearch.init(mBMapMan, new MKSearchListener(){

			@Override
			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
				MyLog.d(error+"----");
				if (error != 0 || res == null) {
					  showToastError("路径规划错误");
				}else{
					routeOverlay.removeAll();
					 route = res.getPlan(0).getRoute(0);
					   routeOverlay.setData(res.getPlan(0).getRoute(0));
					   
					   mMapView.refresh();
				}
			}

			@Override
			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult arg0,
					int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult arg0,
					int arg1) {
				// TODO Auto-generated method stub
				
			}
			 
			 
		 });
		  routeOverlay = new RouteOverlay(MapBaseV2Activity.this, mMapView);
		  routeOverlay.setStMarker(getResources().getDrawable(R.drawable.point_start));
		  routeOverlay.setEnMarker(getResources().getDrawable(R.drawable.point_end));
		  mMapView.getOverlays().add(routeOverlay);
		  
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





	//init 定位图层
	public void initMyLocationLayer() {

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


	//据点图层
	void initOverLay(){
		  mOverlay = new MyOverlay(getResources().getDrawable(R.drawable.point_empty),mMapView);	
		  mMapView.getOverlays().add(mOverlay);
	}
	//弹出框图层
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
    //据点图层
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
	  public class MyLocationListenner implements BDLocationListener {
	    	
	        @Override
	        public void onReceiveLocation(BDLocation location) {
	        	myBaidulocation = location;
	            if (location == null)
	                return ;
	            StringBuffer sb = new StringBuffer(256);
	            sb.append("Poi time : ");
	            sb.append(location.getTime());
	            sb.append("\nerror code : ");
	            sb.append(location.getLocType());
	            sb.append("\nlatitude : ");
	            sb.append(location.getLatitude());
	            sb.append("\nlontitude : ");
	            sb.append(location.getLongitude());
	            sb.append("\nradius : ");
	            sb.append(location.getRadius());
	            if (location.getLocType() == BDLocation.TypeNetWorkLocation){
	                sb.append("\naddr : ");
	                sb.append(location.getAddrStr());
	           } 
	            if(location.hasPoi()){
	                 sb.append("\nPoi:");
	                 sb.append(location.getPoi());
	           }else{             
	                 sb.append("noPoi information");
	            }
	           
	            locdata.latitude = location.getLatitude();
	            locdata.longitude = location.getLongitude();
	            //如果不显示定位精度圈，将accuracy赋值为0即可
	            locdata.accuracy = location.getRadius();
	            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
	            locdata.direction = location.getDerect();
	            //更新定位数据
	            myLocationOverlay.setData(locdata);
	            //更新图层数据执行刷新后生效
	            if(mMapView!=null){
	            	mMapView.refresh();
	            }
	            //是手动触发请求或首次定位时，移动到定位点
	            	//移动地图到定位点
	              // mMapController.animateTo(new GeoPoint((int)(locdata.latitude* 1e6), (int)(locdata.longitude *  1e6)));
				LocationRefresh( location);
	            //首次定位完成
	        }
	        
	        public void onReceivePoi(BDLocation poiLocation) {
	            if (poiLocation == null){
	                return ;
	            }
	        }
}
}
