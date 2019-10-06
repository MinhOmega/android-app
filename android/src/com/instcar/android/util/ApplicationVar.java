package com.instcar.android.util;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.instcar.android.entry.Line;
import com.instcar.android.entry.NetDataEntry;
import com.mycommonlib.android.common.util.StringUtils;

/**
 * 
 */

@SuppressLint("NewApi")
public class ApplicationVar extends Application {
	private String phone; // 
	private String username; // 
	private String cookie; //
	private String uid; //
	private NetDataEntry userdata; //
	private Line currentLine; //
	
	XMPPConnection connection ;
	
	public Line getCurrentLine() {
		return currentLine;
	}
	public void setCurrentLine(Line currentLine) {
		this.currentLine = currentLine;
	}

	public LocationClient mLocationClient;
	private MyCarLocationListener mMyLocationListener;
    
	
	private SharedPreferences pref;
	
	  private static ApplicationVar mInstance = null;
	    public boolean m_bKeyRight = true;
	   public BMapManager mBMapManager = null;
	
	@Override
	public void onCreate() {
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		super.onCreate();
		mLocationClient = new LocationClient(getApplicationContext());
		mMyLocationListener = new MyCarLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mInstance= this;
		initEngineManager(this);
	}
	/**
	 * 
	 */
	public class MyCarLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
		MyLog.d(sb.toString());
			Log.i("BaiduLocationApiDem", sb.toString());
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			
		}
	}

	
	public void setUserData(NetDataEntry userdata){
		this.userdata = userdata;
		SharedPreferences.Editor mEditor = pref.edit();
		mEditor.putString("id", userdata.id);
		mEditor.putString("phone", userdata.phone);
		mEditor.putString("name", userdata.name);
		mEditor.putString("headpic", userdata.headpic);
		mEditor.commit();
		
		
		
	}
	public NetDataEntry getUserData(){
		NetDataEntry  a = new NetDataEntry();
		a.id = pref.getString("id", "");
		a.phone = pref.getString("phone", "");
		a.name = pref.getString("name", "");
		a.headpic = pref.getString("headpic", "");
		return a;
	}

	
	public void setUid(String uid){
		
		if(!StringUtils.isEmpty(uid.trim())){
			SharedPreferences.Editor mEditor = pref.edit();
			mEditor.putString("uid", uid);
			mEditor.commit();
			this.uid = uid;
		}
		
	}
	public String getUid(){
		String s = pref.getString("uid", "");
		this.uid = s;
		return this.uid;
		
	}
public void setStringCookie(String cookie){
	
	if(!StringUtils.isEmpty(cookie.trim())){
		SharedPreferences.Editor mEditor = pref.edit();
		mEditor.putString("cookie", cookie);
		mEditor.commit();
		this.cookie = cookie;
	}
	
}

public String getStringCookie(){
	String s = pref.getString("cookie", "");
	this.cookie = s;
	return this.cookie;
}
public void initEngineManager(Context context) {
    if (mBMapManager == null) {
        mBMapManager = new BMapManager(context);
    }

    if (!mBMapManager.init(new MyGeneralListener())) {
        Toast.makeText(ApplicationVar.getInstance().getApplicationContext(), 
                "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
    }
}

public static ApplicationVar getInstance() {
	return mInstance;
}

public void initOpenFireConnect(){
	ConnectionConfiguration config = new ConnectionConfiguration(
			"115.28.231.132", 13000);
	config.setSASLAuthenticationEnabled(false);  
	config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);  
	connection = new XMPPConnection(config);
	try {
		connection.connect();
	} catch (XMPPException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public XMPPConnection  getOpenfireConnect(){
	if(connection==null){
		initOpenFireConnect();
	}
	return connection;
}
// 常用事件监听，用来处理通常的网络错误，授权验证错误等
public static class MyGeneralListener implements MKGeneralListener {
    
    @Override
    public void onGetNetworkState(int iError) {
        if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
            Toast.makeText(ApplicationVar.getInstance().getApplicationContext(), "您的网络出错啦！",
                Toast.LENGTH_LONG).show();
        }
        else if (iError == MKEvent.ERROR_NETWORK_DATA) {
            Toast.makeText(ApplicationVar.getInstance().getApplicationContext(), "输入正确的检索条件！",
                    Toast.LENGTH_LONG).show();
        }
        // ...
    }

    @Override
    public void onGetPermissionState(int iError) {
    	//非零值表示key验证未通过
        if (iError != 0) {
            //授权Key错误：
            Toast.makeText(ApplicationVar.getInstance().getApplicationContext(), 
                    "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "+iError, Toast.LENGTH_LONG).show();
            ApplicationVar.getInstance().m_bKeyRight = false;
        }
        else{
        	ApplicationVar.getInstance().m_bKeyRight = true;
        	Toast.makeText(ApplicationVar.getInstance().getApplicationContext(), 
                    "key认证成功", Toast.LENGTH_LONG).show();
        }
    }
}
	

}
