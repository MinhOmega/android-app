package com.instcar.android.util;

import android.content.Context;

/**
 * @ClassName DipToPx
 * @PackageName com.prdoor.moviebox.util
 * @创建人 dongrui
 * @修改日期 2012-11-19 上午3:34:48
 * @描述
 */
public class DipToPx {
	private Context context;

	public DipToPx(Context context) {
		this.context = context;
	}

	public int getPx(float dip) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}
	
	public void detory(){
		context=null;
	}
}
