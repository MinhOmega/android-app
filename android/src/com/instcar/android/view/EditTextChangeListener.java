package com.instcar.android.view;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.instcar.android.util.MyLog;
import com.mycommonlib.android.common.util.StringUtils;
public class EditTextChangeListener implements TextWatcher {
	public int count=0;
	public EditText e;
	
	public EditTextChangeListener(EditText e,int count) {
		this.count = count;
		this.e =e;
		
	}
	

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		String str = s.toString();
		 MyLog.d("incar", str);
		
		if((StringUtils.vilidateLength(str)==count)){
			
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

}
