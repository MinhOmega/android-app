package com.instcar.android;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.instcar.android.util.MyLog;

/**
 * 
 * 
 * @author dj880618
 * 
 */
@SuppressLint("NewApi")
public class StartActivity extends BaseActivity {
	static final int NUM_ITEMS = 4;

	MyAdapter mAdapter;

	ViewPager mPager;
	LinearLayout btregister;
	LinearLayout btLogin;
	LinearLayout btforgetPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_start);

		mAdapter = new MyAdapter(getFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				
				if(position==2){
					btforgetPass.setVisibility(View.GONE);
					btLogin.setVisibility(View.VISIBLE);
					
				}
				if(position==3){
					MyLog.d("ss");
					btforgetPass.setVisibility(View.VISIBLE);
					btLogin.setVisibility(View.GONE);
					
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int count) {
				
			}
		});

		// Watch for button clicks.
		 btregister = (LinearLayout) findViewById(R.id.button_register);
		 btregister.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(StartActivity.this, RegisterActivity.class);
				startActivity(i);
			}
		});
		 btLogin = (LinearLayout) findViewById(R.id.button_login);
		 btLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(StartActivity.this, LoginActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);  
			}
		});
		 
		 btforgetPass = (LinearLayout) findViewById(R.id.button_forgetpassword);
		 
	}

	public  class MyAdapter extends FragmentStatePagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}
		
		@Override
		public Fragment getItem(int position) {
			MyLog.d(position+"==position");
			
			Bundle args = new Bundle();
			args.putInt("num", position);
			ArrayListFragment f =  new ArrayListFragment();
			f.setArguments(args);
			return f;
		}
	}

	@SuppressLint("ValidFragment")
	 class ArrayListFragment extends Fragment {
		int mNum;

		public ArrayListFragment() {
			
		}
		

		/**
		 * When creating, retrieve this instance's number from its arguments.
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
		}

		/**
		 * The Fragment's UI is just a simple text view showing its instance
		 * number.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(
					R.layout.layout_activity_start_fragment_pager, container,
					false);
			LinearLayout l = (LinearLayout) v.findViewById(R.id.start_frangent);
			
			switch (mNum) {
			case 0:
				l.setBackgroundResource(R.drawable.pic_1);

				break;
			case 1:
				l.setBackgroundResource(R.drawable.pic_2);
				
				break;
			case 2:
				l.setBackgroundResource(R.drawable.pic_3);
				
				break;
			case 3:
				l.setBackgroundResource(R.drawable.pic_4);
				LinearLayout content = (LinearLayout) v.findViewById(R.id.start_frangent_content);
				
				content.setVisibility(View.VISIBLE);
				
				content.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(StartActivity.this, LoginActivity.class);
						startActivity(i);
						overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);  
					}
				});
				break;


			default:
				l.setBackgroundResource(R.drawable.pic_1);
				break;
			}
			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

		}

	}

}
