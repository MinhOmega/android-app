package com.instcar.android.thread;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;

import com.instcar.android.config.Config;
import com.instcar.android.config.HandleConfig;
import com.instcar.android.util.SdCard;
import com.mycommonlib.android.common.util.DigestUtils;
import com.mycommonlib.android.common.util.FileUtils;
import com.mycommonlib.android.common.util.ImageUtils;

/*
 * 通用thread；
 * 
 */

public class ImageThread implements Runnable {

	public ImageDownLoadCallBack callback;
	public String url;
	Drawable d;

	public interface ImageDownLoadCallBack {

		void Callback(Drawable d);
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public void setCallBack(ImageDownLoadCallBack call) {
		this.callback = call;
	}

	@Override
	public void run() {
		String md5key = DigestUtils.md5(url);
		if (SdCard.iSFileExit(SdCard.getUserIconPath()+md5key+".png")) {
			d = SdCard.getExistimg(SdCard.getUserIconPath(), md5key);
			
		} else {
			d = ImageUtils.getDrawableFromUrl(url, 5000);
			if (d != null) {
				SdCard.savePicture(SdCard.getUserIconPath(), d, md5key);
			}
		}
		if (callback != null) {
			callback.Callback(d);
		}
	}
}
