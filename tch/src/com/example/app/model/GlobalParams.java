package com.example.app.model;


import android.os.Environment;


public class GlobalParams {
		
	public static String username="昵称";
	public static String account="账号";
	
	//当前是否登陆
	public static boolean isLoging=false;
	public static PersonInfo personInfo=null;
	
	
	//本地存储路径，如果存在SD卡则默认存在SD卡，如果不存在，则默认存在手机内存中
	public static String LOCAL_SAVE_PATH = Environment.getExternalStorageDirectory()+"/.tch/";
	
	public static String IMAGE_SAVE_PATH = Environment.getExternalStorageDirectory()+"/cuimi/pictures/";

}
