package com.instcar.android.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mycommonlib.android.common.util.TimeUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.util.Log;

/**
 * 
 *
 */
public class CommonUtil
{

	public static String getTimedescByTime(String time){
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String restr = time;
		 try {
			Date date = sdf.parse( time);
			Long Ltime = date.getTime();
			Long now = TimeUtils.getCurrentTimeInLong();
			Long s = now-Ltime;
			float min = s/(1000*60);
			if(min<60){
				restr = min+"分钟前发布";
			}
			if(min>60&&min<1440){
				restr =(int)( min/60)+"小时前发布";
			}
			if(min>1440){
				restr = (int)( min/(60*24))+"天前发布";
			}
		 } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return restr;
	}
	
}
