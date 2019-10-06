package com.instcar.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RatingBar;

public class MyRateingBar extends RatingBar {

	public MyRateingBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyRateingBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyRateingBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
