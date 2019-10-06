package com.instcar.android;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.view.View;

import com.instcar.android.UploadCar1Fragment.onBandClickListener;
import com.instcar.android.UploadCar2Fragment.CallBackListener;
import com.instcar.android.UploadCar3Fragment.CallBack3Listener;
import com.instcar.android.entry.CarBand;
import com.instcar.android.sortlistview.SortModel;


@SuppressLint("NewApi")
public class VerfyCarActivity extends BaseActivity  {
	private View frangmentContainer;//容器
	
	private UploadCar1Fragment uploadCar1Fragment;
	private UploadCar2Fragment uploadCar2Fragment;
	private UploadCar3Fragment uploadCar3Fragment;
	FragmentTransaction ft;
	private SortModel band;
	private CarBand carBand;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verfy_car_main);
		initBaseNav();
		navbar.setText("汽车认证");
		navbar2.setText("请选择汽车品牌");
		btn_right.setVisibility(View.GONE);
		frangmentContainer = findViewById(R.id.verfy_car_layout_container);
		
		FragmentManager fragmentManager = getFragmentManager();
		ft = fragmentManager.beginTransaction();
		uploadCar1Fragment = new UploadCar1Fragment();
		uploadCar1Fragment.setCallBack(new onBandClickListener() {
			
			@Override
			public void click(SortModel band) {
				VerfyCarActivity.this.band=band;
				Bundle bundle = new Bundle();  
				
                bundle.putString("band",band.getName());  
                bundle.putString("pic",band.getPic());  
				uploadCar2Fragment = new UploadCar2Fragment();
				uploadCar2Fragment.setCallBackListener(new CallBackListener() {
					
					@Override
					public void click(CarBand ba) {
						carBand =ba;
			
						Bundle bundle2 = new Bundle();  
						bundle2.putString("name", carBand.name);
						bundle2.putString("band", VerfyCarActivity.this.band.getName());
						bundle2.putString("carid", ba.id);
						bundle2.putString("xilie", ba.xilie);
						bundle2.putString("pic", VerfyCarActivity.this.band.getPic());
						uploadCar3Fragment = new UploadCar3Fragment();
						uploadCar3Fragment.setArguments(bundle2);
						uploadCar3Fragment.setCallBack3Listener(new CallBack3Listener() {
							
							@Override
							public void click(int code) {
								VerfyCarActivity.this.finish();
								
							}
						});
						FragmentManager fragmentManager =getFragmentManager();
						FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
						fragmentTransaction.replace(R.id.verfy_car_layout_container, uploadCar3Fragment);
						fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();
					}
				});
				uploadCar2Fragment.setArguments(bundle);
				FragmentManager fragmentManager =getFragmentManager();
				FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.verfy_car_layout_container, uploadCar2Fragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
                
			}
		});
		ft.add(R.id.verfy_car_layout_container, uploadCar1Fragment, "upload1");
		ft.commit();
	}

	
	
}
