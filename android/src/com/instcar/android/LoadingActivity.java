package com.instcar.android;

import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.NetEntry;
import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;

/*
 *@dujie 
 */
public class LoadingActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_load);
		mHandler = new Handler() {
			String data;
			NetEntry entry;

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Intent intent = new Intent();
					intent.setClass(LoadingActivity.this, StartActivity.class);

					startActivity(intent);

					LoadingActivity.this.finish();
					break;
				case 2://进行登陆
					String uid = av.getUid();
					userdetail(uid);
					break;
				case HandleConfig.GETUSERINFO:

					data = msg.getData().getString("data");
					entry = new NetEntry(data);
					if (NetEntry.CODESUCESS.equals(entry.status)) {
						// 应该到main
						// 存储用户信息
						av.setUserData(entry.netentry);
						Intent i = new Intent(LoadingActivity.this,
								MainActivity.class);
						startActivity(i);
						LoadingActivity.this.finish();
					} else {
						// 应该到start
						Message message = new Message();
						message.what = 1;
						mHandler.sendMessageDelayed(message, 1000);
					}
					break;
				}

			};
		};

		String uid = av.getUid();// 获取详细信息成功
		MyLog.d(uid+"==uid");
		if (!StringUtils.isEmpty(uid) & !StringUtils.isEquals(uid, "")) {
			
			Message message = new Message();
			message.what = 2;
			mHandler.sendMessageDelayed(message, 1000);
		} else {
			//
			Message message = new Message();
			message.what = 1;
			mHandler.sendMessageDelayed(message, 1000);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
