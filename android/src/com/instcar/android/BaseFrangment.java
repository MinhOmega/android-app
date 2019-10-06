package com.instcar.android;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import com.instcar.android.config.Config;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.service.CommonService;
import com.instcar.android.service.UploadImageService;
import com.instcar.android.thread.CommonThread;
import com.instcar.android.util.ApplicationVar;
import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("NewApi")
public class BaseFrangment  extends Fragment{
	ProgressDialog progressDialog;
	public ApplicationVar av;
	public Handler mHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		av =(ApplicationVar) getActivity().getApplication();
	}
	
	public void showProcessDialog(String message) {
		dismissProcessDialog();
		progressDialog = ProgressDialog.show(getActivity(), "请稍后", message, true, false);
	}

	public void dismissProcessDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

	}
	public void showToastError(String message) {
		ToastUtils.show(getActivity(), message);
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
	/*
	 * h获取 收藏linelist
	 */
	public void queryfavlinelist(String page,String rows) {
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("page", page);
		param.put("rows", rows);
		
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiserverlinefavoritelist());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.QUERYFAVLIST);
		new Thread(thread).start();
		
	}
	/*
	 * h获取 收藏linelist
	 */
	public void getuserrooms(String uid) {
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("user_id", uid);
		// 需要修改1
		CommonService service = new CommonService(
				Config.apiservergetuserrooms());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setHandle(mHandler);
		thread.setService(service);
		// 需要修改2
		thread.setwhat(HandleConfig.GETUSERROOMS);
		new Thread(thread).start();
		
	}
	public void addcar(String car_id, String license, String car1, String car2) {
		Map<String, String> param = new IdentityHashMap<String, String>();
		param.put("car_id", car_id);
		param.put("license", license);
		param.put("cars[]", car1+"&cars[]="+car2);

		CommonService service = new CommonService(Config.apiserveruseraddcar());
		service.setParam(param);
		service.setAv(av);
		CommonThread thread = new CommonThread();
		thread.setwhat(HandleConfig.ADDCAR);
		thread.setHandle(mHandler);
		thread.setService(service);
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
}
