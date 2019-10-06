package com.instcar.android.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
public class SdCard
{

	public static final int SD_CARD_NOT_EXIST = 708;
	public static final int SD_CARD_SPACE_LESS = 709;

	public static boolean isSdCardExist()
	{
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	public static boolean iSFileExit(String file)
	{

		File f = new File(file);

		return f.exists();
	}

	/**
	 * 存储图
	 * 
	 * @param bitName
	 *            图片名称
	 * @param mBitmap
	 *            图
	 */
	public static void saveMyBitmap(String bitName, Bitmap mBitmap)
	{
		File f = new File(bitName);
		try
		{
			f.createNewFile();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
		}
		FileOutputStream fOut = null;
		try
		{
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try
		{
			fOut.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			fOut.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取已经存在的图片
	 * 
	 * @param couponID
	 *            优惠券id
	 * @return
	 */
	public static Drawable getExistimg(String path, String img_id)
	{
		Drawable d = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist)
		{
			String iconpath = path + img_id + ".png";
			File file = new File(iconpath);
			if (file.exists())
			{
				Bitmap bit = BitmapFactory.decodeFile(iconpath);
				d = new BitmapDrawable(bit);
			}
		}
		return d;
	}
	/**
	 * 获取已经存在的图片
	 * 
	 * @param couponID
	 *            优惠券id
	 * @return
	 */
	public static Bitmap getExistimgBitmap(String path, String img_id)
	{
		Drawable d = null;
		Bitmap bit=null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist)
		{
			String iconpath = path + img_id + ".png";
			File file = new File(iconpath);
			if (file.exists())
			{
				 bit = BitmapFactory.decodeFile(iconpath);
			}
		}
		return bit;
	}

	public static Bitmap drawableToBitmap(Drawable drawable)
	{

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 保存图片
	 * 
	 * @param path
	 *            保存路径
	 * @param d图片
	 * @param name图片名称
	 */
	public static synchronized void savePicture(String path, Drawable d, String name)
	{
		if (path == null || path.length() == 0) return;
		if (d != null)
		{
			Bitmap bit = SdCard.drawableToBitmap(d);
			File directory = new File(path);
			// Create the folder if it doesn't exist:
			if (!directory.exists())
			{
				directory.mkdirs();
			}
			String iconpath = path + name + ".png";
			SdCard.saveMyBitmap(iconpath, bit);
			bit.recycle();
		}
	}
	public static synchronized String savePictureBitmap(String path, Bitmap d, String name)
	{
		String iconpath="";
		if (path == null || path.length() == 0) return "";
		if (d != null)
		{
			Bitmap bit = d;
			File directory = new File(path);
			// Create the folder if it doesn't exist:
			if (!directory.exists())
			{
				directory.mkdirs();
			}
			 iconpath = path + name + ".png";
			SdCard.saveMyBitmap(iconpath, bit);
			//bit.recycle();
		}
		return iconpath;
	}

	/**
	 * 得到优惠券大图路径
	 */
	public static String getUserIconPath()
	{
		String path = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist)
		{ // 如果SD卡存在，则获取跟目录
			File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			path = sdDir.toString() + "/.incar/user_icon/";
			File p = new File(path);
			if (!p.exists())
			{
				p.mkdir();
				return path;
			}
		}
		return path;
	}
	
	/**
	 * 得到优惠券大图路径
	 */
	public static String getRewardPath()
	{
		String path = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist)
		{ // 如果SD卡存在，则获取跟目录
			File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			path = sdDir.toString() + "/.incar/icon/";
			File p = new File(path);
			if (!p.exists())
			{
				p.mkdir();
				return path;
			}
		}
		return path;
	}

	public static long getAvailaleSize()
	{
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		/* 块大小 */
		long blockSize = stat.getBlockSize();
		/* 块数量 */
		long availableBlocks = stat.getAvailableBlocks();
		/* 返回bit大小值 */
		return availableBlocks * blockSize / 1024;
		// (availableBlocks * blockSize)/1024 KIB 单位
		// (availableBlocks * blockSize)/1024 /1024 MIB单位

	}

	/**
	 * 
	 * @param ctx
	 * @param size
	 * @return 在下载线程中执行
	 */

	public static int sdCardOperaThread(long size)
	{
		int sd = -1;
		if (!isSdCardExist())
		{
			return SD_CARD_NOT_EXIST;
		}
		else if (size > getAvailaleSize())
		{
			return SD_CARD_SPACE_LESS;
		}
		return sd;
	}

	/**
	 * 
	 * @param ctx
	 * @param size
	 * @return 在下载线程中执行
	 */

	public static int sdCardOperaThreadMsg(Handler handler, long size)
	{
		int sd = -1;
		if (!isSdCardExist())
		{
			handler.sendEmptyMessage(SD_CARD_NOT_EXIST);
			return SD_CARD_NOT_EXIST;
		}
		else if (size > getAvailaleSize())
		{
			handler.sendEmptyMessage(SD_CARD_SPACE_LESS);
			return SD_CARD_SPACE_LESS;
		}
		return sd;
	}
}
