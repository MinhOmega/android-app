package com.instcar.android;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.instcar.android.util.MyLog;

/**
 * 
 * 
 * @author dj880618
 */
@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {

	ViewPager mPager; // 左右滑动的框
	ImageButton btn_opt1;// 左边的操作按钮
	ImageButton btuser;// 用户
	ImageButton btsetting;// 设置按钮

	ImageView round1;
	ImageView round2;
	ImageView round3;

	TextView nav_hint;// 显示提示信息用的
	View v;
	long currentmillions = 0;
	private boolean iszhankai = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_main2);

		mPager = (ViewPager) findViewById(R.id.pager);
		v = findViewById(R.id.menu);
		nav_hint = (TextView) findViewById(R.id.text_nav);
		btn_opt1 = (ImageButton) findViewById(R.id.button_opt);
		btn_opt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (iszhankai == true) {
					Animation animation = AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.menu_left_out);
					v.startAnimation(animation);

				}
			}
		});
		btuser = (ImageButton) findViewById(R.id.button_user);
		btsetting = (ImageButton) findViewById(R.id.button_set);

		btuser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(MainActivity.this, SetActivity.class);
				startActivity(i);

			}
		});
		round1 = (ImageView) findViewById(R.id.round1);
		round2 = (ImageView) findViewById(R.id.round2);
		round3 = (ImageView) findViewById(R.id.round3);

		mPager.setAdapter(new MyAdapter(getFragmentManager()));
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pos) {
				// TODO Auto-generated method stub
				switch (pos) {
				case 0:
					round1.setImageResource(R.drawable.common_yuan);
					;
					round2.setImageResource(R.drawable.common_yuan_white);
					;
					round3.setImageResource(R.drawable.common_yuan_white);
					;

					nav_hint.setText(av.getUserData().phone + ",欢迎来到易行");
					break;
				case 1:
					round1.setImageResource(R.drawable.common_yuan_white);
					;
					round2.setImageResource(R.drawable.common_yuan);
					;
					round3.setImageResource(R.drawable.common_yuan_white);
					;
					nav_hint.setText(av.getUserData().phone + ",您当前的出行计划如下");
					break;
				case 2:
					round1.setImageResource(R.drawable.common_yuan_white);
					;
					round2.setImageResource(R.drawable.common_yuan_white);
					;
					round3.setImageResource(R.drawable.common_yuan);
					;
					nav_hint.setText(av.getUserData().phone + ",您的常用线路");
					break;

				default:
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		nav_hint.setText(av.getUserData().phone+",欢迎来到易行");
	}

	public class MyAdapter extends FragmentStatePagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Fragment getItem(int position) {
			MyLog.d(position + "==position");

			Bundle args = new Bundle();
			args.putInt("num", position);
			Fragment f = null;
			switch (position) {
			case 0:
				f = new ChoiceFragment();
				break;
			case 1:
				f = new TodayWayFragment();
				break;
			case 2:
				f = new CommonWayFragment();
				break;

			default:
				f = new ChoiceFragment();
				break;
			}
			return f;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			long time = System.currentTimeMillis();
			if (time - currentmillions < 2000) {
				dismissProcessDialog();
				MainActivity.this.finish();
			} else {
				currentmillions = time;
				showToastError("请再按一次推出");

			}
		}
		return false;
	}
}
