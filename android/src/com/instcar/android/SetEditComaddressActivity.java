package com.instcar.android;

import com.instcar.android.config.HandleConfig;
import com.instcar.android.entry.NetEntry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 
 * @dujie
 * 
 */
public class SetEditComaddressActivity extends BaseActivity {

	Button addcar;
	EditText edit;
	TextView tip;
	int max=40;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_detail_edit_textinfo);
		
		btn_right = (ImageButton) findViewById(R.id.btn_right);
		btn_left = (ImageButton) findViewById(R.id.btn_left);
		navbar2 = (TextView) findViewById(R.id.top_bar_tip);
		navbar = (TextView) findViewById(R.id.text_top_bar);
		tip = (TextView) findViewById(R.id.user_info_edit_tip);
		tip.setText("您还可以输入"+max+"字");
		edit = (EditText) findViewById(R.id.edit_text_input);
		edit.setText(av.getUserData().signature);
		edit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int a = edit.getText().length();
				int shengyu = max -a;
				tip.setText("您还可以输入"+shengyu+"字");
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		navbar2.setText("签名最大不超过40");
		navbar.setText("编辑签名");

		btn_right.setImageResource(R.drawable.btn_save);
		btn_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showProcessDialog("正在保存..");
				useredit("signature", edit.getText().toString());
			}
		});
		btn_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SetEditComaddressActivity.this.finish();
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		initHandle();
	}

	public void initHandle() {
		mHandler = new Handler() {
			public int count = 60;

			public void handleMessage(Message msg) {
				String data;
				NetEntry entry;

				switch (msg.what) {

				case HandleConfig.EDITUSERINFO:// 验证手机号码有效性返回的数据
					dismissProcessDialog();
					data = msg.getData().getString("data");
					entry = new NetEntry(data);
					if (NetEntry.CODESUCESS.equals(entry.status)) {
						av.getUserData().signature = edit.getText().toString();
						setResult(RESULT_OK);
						finish();
					} else {
						showToastError("保存失败..");
					}
					break;

				}
			};
		};

	}

	public void finish() {
		super.finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
