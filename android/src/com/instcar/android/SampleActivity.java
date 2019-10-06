package com.instcar.android;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
 *@dujie 
 */
public class SampleActivity extends BaseActivity {

	Button go;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_upload_img_sample);
		go = (Button) findViewById(R.id.goonupload);
		go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SampleActivity.this.finish();
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
