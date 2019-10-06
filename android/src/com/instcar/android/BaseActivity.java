package com.instcar.android;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.instcar.android.config.Config;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.CarPoint;
import com.instcar.android.entry.Line;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.service.CommonService;
import com.instcar.android.service.UploadImageService;
import com.instcar.android.thread.AuthPhoneThread;
import com.instcar.android.thread.CommonThread;
import com.instcar.android.thread.GetCarThread;
import com.instcar.android.thread.GetPhoneCodeThread;
import com.instcar.android.thread.GetUserInfoThread;
import com.instcar.android.thread.LoginThread;
import com.instcar.android.thread.UserRegisterThread;
import com.instcar.android.util.ApplicationVar;
import com.instcar.android.util.MyLog;
import com.instcar.android.util.SdCard;
import com.mycommonlib.android.common.util.ImageUtils;
import com.mycommonlib.android.common.util.JSONUtils;
import com.mycommonlib.android.common.util.StringUtils;
import com.mycommonlib.android.common.util.ToastUtils;

/**
 * 
 * @dujie
 * 
 */
public class BaseActivity extends Activity {
	ProgressDialog progressDialog;
	public Handler mHandler;
	ImageButton btn_left;
	ImageButton btn_right;
	TextView navbar;
	TextView navbar2;

	public ApplicationVar av;

	public Drawable image;
	public String filename;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		av = (ApplicationVar) getApplication();
	}

	public void showToastError(String message) {
		ToastUtils.show(this, message);
	}

	public void setEditStatus(EditText e, int status) {

		if (status == 1) {
			e.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_agree_ok, 0);
		} else {
			e.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_agree_no, 0);
		}
	}

	public void showProcessDialog(String message) {
		dismissProcessDialog();
		progressDialog = ProgressDialog.show(this, "请稍后", message, true, false);
	}

	public void dismissProcessDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

	}

	public void initBaseNav(){
		
		try {
			btn_right = (ImageButton) findViewById(R.id.btn_right);
			btn_left = (ImageButton) findViewById(R.id.btn_left);
			navbar  =(TextView) findViewById(R.id.text_top_bar);
			navbar2  =(TextView) findViewById(R.id.top_bar_tip);
			
			btn_left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BaseActivity.this.finish();
				}
			});
		} catch (Exception e) {

		}
		
	}
	/*
	 * 用户注册
	 */
	public void userRegister(String phoneNum, String smsid, String authcode,
			String password) {
		Map<String, String> param = new HashMap<String, String>();
		// smsid="11632456";

		param.put("phone", phoneNum);
		param.put("password", password);
		param.put("authcode", authcode);
		param.put("smsid", smsid);
		CommonService service = new CommonService(
				Config.apiserveruserregister());
		service.setParam(param);
		service.setAv(av);
		UserRegisterThread thread = new UserRegisterThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start();

	}

	public void getAuthCode(String phoneNum) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("phone", phoneNum);
		CommonService service = new CommonService(
				Config.apiserverusergetauthcode());
		service.setParam(param);
		service.setAv(av);
		GetPhoneCodeThread thread = new GetPhoneCodeThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start();

	}

	public void AuthPhone(String phoneNum) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("phone", phoneNum);
		CommonService service = new CommonService(
				Config.apiuserCheckUserPhone());
		service.setParam(param);
		service.setAv(av);
		AuthPhoneThread phoneThread = new AuthPhoneThread();
		phoneThread.setHandle(mHandler);
		phoneThread.setService(service);
		new Thread(phoneThread).start();

	}

	public void login(String phone, String password) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("phone", phone);
		param.put("password", password);

		CommonService service = new CommonService(Config.apiserveruserlogin());
		service.setParam(param);
		service.setAv(av);
		LoginThread thread = new LoginThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start();

	}

	public void userdetail(String uid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", uid);

		CommonService service = new CommonService(Config.apiserveruserdetail());
		service.setParam(param);
		service.setAv(av);
		GetUserInfoThread thread = new GetUserInfoThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start();

	}

	// 获取用户汽车列表
	public void getcar(String car_id) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("car_id", car_id);

		CommonService service = new CommonService(Config.apiserverusergetcars());
		service.setParam(param);
		service.setAv(av);
		GetCarThread thread = new GetCarThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start();

	}

	// 增加汽车
	public void useredit(String key,String value) {
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put(key, value);
		param.put(key, value);
		param.put(key, value);
		
		CommonService service = new CommonService(Config.apiserveruseredit());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setwhat(HandleConfig.EDITUSERINFO);
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start();
		
	}
	// 增加汽车
	public void addcar(String car_id, String license, String car1, String car2) {
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("car_id", car_id);
		param.put("license", license);
		param.put("cars[]", car1);
		param.put("cars[]", car1);

		MyLog.d(car1);
		MyLog.d(car2);
		CommonService service = new CommonService(Config.apiserveruseraddcar());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setwhat(HandleConfig.ADDCAR);
		thread.setHandle(mHandler);
		thread.setService(service);
		new Thread(thread).start();

	}

	// 用户实名认证
	public void realnamerequest(String id_cards1, String id_cards2) {
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("id_cards[]", id_cards1);
		param.put("id_cards[]", id_cards2);

		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserveruserrealnamerequest());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.REALNAMEREQUEST);
		new Thread(thread).start();

	}
	// 获取room详细信息
	public void getroominfo(String roomid) {
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("room_id", roomid);
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiservergetroominfo());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.GETROOMINFO);
		new Thread(thread).start();
		
	}
	public void getdetailbyphone(String phone) {
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("phone", phone);
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiservergetbyphone());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUERYUSERDETAIL);
		new Thread(thread).start();
		
	}

	// 图片上传
	public void imageupload(String type, String user_id, Bitmap photo) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("user_id", user_id);
		params.put("type", type);



		// 需要修改1
		UploadImageService service = new UploadImageService(
				Config.apiserverimageupload());
		service.params = params;
		service.photo =photo;
		service.setAv(av);

		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.UPLOADIMAGE);
		new Thread(thread).start();

	}
	// 用户实名认证
	public void requestcarxilie(String name) {
		name =name.replace("_a.jpg", "");
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("aliasname", name);

		// 需要修改1
		CommonService service = new CommonService(
				Config.apiservercarlist());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.CARLIST);
		new Thread(thread).start();

	}
	
	public Drawable getUrlImage(final String url){
		try {
			
	
		  filename=""; 
		try {
			 filename = URLEncoder.encode(url,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(SdCard.iSFileExit(SdCard.getUserIconPath()+filename)){
			image = Drawable.createFromPath(SdCard.getUserIconPath()+filename);
			
		}else{
			
			Thread t = new Thread(new Runnable() {
				public  Drawable da=null;
				@Override
				public void run() {
					image= ImageUtils.getDrawableFromUrl(url, 5000);
					SdCard.savePicture(SdCard.getUserIconPath(), image, filename);
					
					mHandler.sendEmptyMessage(HandleConfig.SETHEADIMG);
				}
			});
			t.start();
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return image;
		
		
	}
	//根据name 获取car的icon
	public void getCariconByname(){
		
		
	}
	
	/*
	 * 查询手边据点信息
	 */
	public void querynearPiont(Double lat,Double lng){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("page", "1");
		param.put("rows", "20");
		param.put("lat", String.valueOf(lat));
		param.put("lng", String.valueOf(lng));

		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserverpointnearestlist());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUERYNEARPOINT);
		new Thread(thread).start();

		
	}
	
	/*
	 *  模糊查询据点信息
	 */
	public void queryPiont(String wd,String city){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("page", "1");
		param.put("rows", "20");
		param.put("wd", wd);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserverpointlist());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUERYPOINT);
		new Thread(thread).start();
		
		
	}
	
	public void queryLineList(String wd){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("page", "1");
		param.put("rows", "20");
		param.put("wd", wd);
		param.put("all", "1");
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserverlinelist());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUERYLINELIST);
		new Thread(thread).start();
		
	}
	public void queryRoomListByLineid(String lineid){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("line_id", lineid);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiservergetlinerooms());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUERTLINEROOMS);
		new Thread(thread).start();
		
	}
	public void joinRoom(String roomid,String userid){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("room_id", roomid);
		param.put("userid", userid);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserverjoinroom());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.JOINROOM);
		new Thread(thread).start();
		
	}
	public void QueryRoomUsers(String roomid){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("room_id", roomid);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apigetroomusers());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUERYROOMUSERS);
		new Thread(thread).start();
		
	}
	
	public void queryLineDetail(String lineid){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("lineid", lineid);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiListLineByid());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUERYLINEDETAIL);
		new Thread(thread).start();
		
	}
	public void joinroom(String roomid,String user_id){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("room_id", roomid);
		param.put("user_id", user_id);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserverroomjoin());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.JOINROOM);
		new Thread(thread).start();
		
	}
	public void addfav(String lineid){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("lineid", lineid);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserveraddfavorite());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.ADDFAV);
		new Thread(thread).start();
		
	}
	public void quitroom(String roomid,String user_id){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("room_id", roomid);
		param.put("user_id", user_id);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserverroomquit());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUITROOM);
		new Thread(thread).start();
		
	}
	/*参数	类型
user_id	int
line_id	int
price	int
description	string
start_time	date
max_seat_num	int

*/
	public void createRoom(Line line,String desc ,String date,String numseat){
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("user_id", av.getUserData().id);
		param.put("line_id", line.id);
		param.put("price", line.price);
		param.put("description", desc);
		param.put("start_time", date);
		param.put("max_seat_num", numseat);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiservercreateroom());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.CREATEROOM);
		new Thread(thread).start();
		
	}
	//解析据点jsonlist
	public NetEntry decodePointList(String s){
		NetEntry entry = new NetEntry(s);
		return entry;
		
		
		
	}

	public List<Line> decoeLineList(String data){
		List<Line> arr = new ArrayList<Line>();
		try {
			JSONObject json = new JSONObject(data);
			JSONObject datalist = json.getJSONObject("data");
			JSONArray jsonlist = datalist.getJSONArray("list");
			if(jsonlist!=null&&jsonlist.length()>0){
				for (int i = 0; i < jsonlist.length(); i++) {
					
					Line l = deoceLine(jsonlist.get(i));
					if(l!=null){
						arr.add(l);
					}
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arr;
		
	}
	
	
	public Line deoceLine(Object obj){
		Line l = null;
		try{
			
		if(obj==null) return null;
		JSONObject json = (JSONObject)obj;
		l = new Line();
		l.id=json.getString("id");
		l.name=json.getString("name");
		l.description=json.getString("description");
		l.price=json.getString("price");
		l.addtime=json.getString("addtime");
		l.modtime=json.getString("modtime");
		if(json.getJSONArray("list")!=null){
			JSONArray innerList = json.getJSONArray("list");
			
			if(innerList.length()>0){
				for (int i = 0; i < innerList.length(); i++) {
					JSONObject innerdata = innerList.getJSONObject(i);
					CarPoint p = new CarPoint();
					p.id = innerdata.getString("point_id");
					p.price = innerdata.getString("price");
					l.list.add(p);
				}
			}
		}
		
		}catch (Exception e) {

	}
		return l;
	}
}
