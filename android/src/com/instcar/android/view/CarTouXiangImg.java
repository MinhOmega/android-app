package com.instcar.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.instcar.android.config.Config;
import com.instcar.android.thread.ImageThread;
import com.instcar.android.thread.ImageThread.ImageDownLoadCallBack;
import com.instcar.android.util.SdCard;
import com.mycommonlib.android.common.util.DigestUtils;
import com.mycommonlib.android.common.util.FileUtils;
import com.mycommonlib.android.common.util.ImageUtils;

public class CarTouXiangImg extends ImageView {
	String url;
	private static final Xfermode MASK_XFERMODE;
	private Bitmap mask;
	private Paint paint;
	Drawable drawalbe;
	Handler handler;
	void initHandle(){
		handler =new Handler(){
			@Override
			public void handleMessage(Message msg) {
				setImageDrawable(drawalbe);
				super.handleMessage(msg);
			}
		};
	}
	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	public CarTouXiangImg(Context paramContext) {
		super(paramContext);
		initHandle();
	}

	public CarTouXiangImg(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initHandle();
	}

	public CarTouXiangImg(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		initHandle();
	}

	public void setNetImageSource(String url) {
		
		ImageThread t =new ImageThread();
		t.setUrl(url);
		t.setCallBack(new ImageDownLoadCallBack() {
			@Override
			public void Callback(Drawable d) {
				if(d!=null){
					drawalbe = d;
					handler.sendEmptyMessage(1);
				}
			}
		});
		Thread tt = new Thread(t);
		tt.start();
	}

	
	public Bitmap createMask() {
		int i = getWidth();
		int j = getHeight();
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint(1);
		localPaint.setColor(-16777216);
		float f1 = getWidth();
		float f2 = getHeight();
		RectF localRectF = new RectF(0.0F, 0.0F, f1, f2);
		localCanvas.drawOval(localRectF, localPaint);
		return localBitmap;
	}

	protected void onDraw(Canvas paramCanvas) {
	
		Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
		try {
			if (this.paint == null) {
				Paint localPaint1 = new Paint();
				this.paint = localPaint1;
				this.paint.setFilterBitmap(false);
				Paint localPaint2 = this.paint;
				Xfermode localXfermode1 = MASK_XFERMODE;
				@SuppressWarnings("unused")
				Xfermode localXfermode2 = localPaint2
						.setXfermode(localXfermode1);
			}
			float f1 = getWidth();
			float f2 = getHeight();
			int i = paramCanvas.saveLayer(0.0F, 0.0F, f1, f2, null, 31);
			int j = getWidth();
			int k = getHeight();
			localDrawable.setBounds(0, 0, j, k);
			localDrawable.draw(paramCanvas);
			if ((this.mask == null) || (this.mask.isRecycled())) {
				Bitmap localBitmap1 = createMask();
				this.mask = localBitmap1;
			}
			Bitmap localBitmap2 = this.mask;
			Paint localPaint3 = this.paint;
			paramCanvas.drawBitmap(localBitmap2, 0.0F, 0.0F, localPaint3);
			paramCanvas.restoreToCount(i);
			return;
		} catch (Exception localException) {
			StringBuilder localStringBuilder = new StringBuilder()
					.append("Attempting to draw with recycled bitmap. View ID = ");
			System.out.println("localStringBuilder==" + localStringBuilder);
		}
	}

	
}
