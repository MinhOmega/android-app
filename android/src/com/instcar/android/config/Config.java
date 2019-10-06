package com.instcar.android.config;

import java.io.File;

import com.mycommonlib.android.common.util.FileUtils;

import android.os.Environment;

public class Config {

	
	public static String HTTPCODESUCCESS="200";
	
	public static String BASEURL="http://qd00.shopbigbang.com:8086/";
	
	
	public static String getBaseUrl(){
		
		return BASEURL;
	}
	/*
	 * 用户手机号码验证
	 */
	public static String apiuserCheckUserPhone(){
		
		return BASEURL+"server/user/checkuserphone";
	}
	
	/*
	 * 验证用户名是否有效
	 */
	public static String apiserverusercheckusername(){
		
		return BASEURL+"server/user/checkusername";
	}
	/*
	 *  用户获取短信验证码
	 */
	public static String apiserverusergetauthcode(){
		
		return BASEURL+"server/user/getauthcode";
	}
	
	/*
	 *  用户注册
	 */
	public static String apiserveruserregister(){
		
		return BASEURL+"server/user/register";
	}
	
	/*
	 *  用户登陆
	 */
	public static String apiserveruserlogin(){
		
		return BASEURL+"server/user/login";
	}
	/*
	 *  用户详细信息获取
	 */
	public static String apiserveruserdetail(){
		
		return BASEURL+"server/user/detail";
	}
	
	/*
	 *  用户详细信息获取
	 */
	public static String apiserveruseredit(){
		
		return BASEURL+"server/user/edit";
	}
	
	/*
	 *  增加car
	 */
	public static String apiserveruseraddcar(){
		
		return BASEURL+"server/user/addcar";
	}
	
	/*
	 *  获取car
	 */
	public static String apiserverusergetcars(){
		
		return BASEURL+"server/user/getcars";
	}
	
	/*
	 *  实名认证
	 */
	public static String apiserveruserrealnamerequest(){
		
		return BASEURL+"server/user/realnamerequest";
	}
	
	
	/*
	 *  实名认证
	 */
	public static String apiserveruserlogout(){
		
		return BASEURL+"server/user/logout";
	}
	
	/*
	 *  
	 */
	public static String apiserverimageupload(){
		
		return BASEURL+"server/image/upload";
	}
	
	
	/*
	 *   根据品牌查询汽车
	 */
	public static String apiservercarlist(){
		
		return BASEURL+"server/car/list";
	}
	
	/*
	 *   查询周边据点
	 */
	public static String apiserverpointnearestlist(){
		
		return BASEURL+"server/point/nearestlist";
	}
	/*
	 * 
	 */
	public static String apiserverpointlist(){
		
		return BASEURL+"server/point/list";
	}
	
	public static String apiserverlinelist(){
		
		return BASEURL+"server/line/listline";
	}
	public static String apiservercreateroom(){
		
		return BASEURL+"server/room/create";
	}
	public static String apiservergetroominfo(){
		
		return BASEURL+"server/room/getroominfo";
	}
	public static String apiservergetbyphone(){
		
		return BASEURL+"server/user/getbyphone";
	}
	
	public static String apiservergetlinerooms(){
		
		return BASEURL+"server/room/getlinerooms";
	}
	
	
	public static String apiserverjoinroom(){
		
		return BASEURL+"server/room/book";
	}
	
	
	public static String apiListLineByid(){
		return BASEURL+"server/line/listlinebyid";
	}
	
	public static String apigetroomusers(){
		
		return BASEURL+"server/room/getroomusers";
	}
	
	public static String apiserverlinefavoritelist(){
		
		return BASEURL+"server/line/favoritelist";
	}
	public static String apiserveraddfavorite(){
		
		return BASEURL+"server/line/favorite";
	}
	
	public static String apiserverroomjoin(){
		
		return BASEURL+"server/room/book";
	}
	
	public static String apiservergetuserrooms(){
		
		return BASEURL+"server/room/getuserrooms";
	}
	public static String apiserverroomquit(){
		
		return BASEURL+"server/room/unbook";
	}
	
	public static String getOpenFireIp(){
		return "115.28.231.132";
	}
	public static int getOpenFirePort(){
		return 13000;
	}
	
	public static  String getSDPath(){ 
	       File sdDir = null; 
	       
	       boolean sdCardExist = Environment.getExternalStorageState()   
	                           .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在 
	       if   (sdCardExist)   
	       {                               
	         sdDir = Environment.getExternalStorageDirectory();//获取跟目录 
	      }   
	       return sdDir.toString(); 
	       
	}
	//获取file跟目录
	public static  String getFilePath(){
		String str = getSDPath()+".incar/";
		if(!FileUtils.isFolderExist(str)){
			FileUtils.makeDirs(str);
		}
		
		return str;
	}
	
}
