package com.example.app.model;


import android.os.Environment;


public class GlobalParams {
		
	public static String username="�ǳ�";
	public static String account="�˺�";
	
	//��ǰ�Ƿ��½
	public static boolean isLoging=false;
	public static PersonInfo personInfo=null;
	
	
	//���ش洢·�����������SD����Ĭ�ϴ���SD������������ڣ���Ĭ�ϴ����ֻ��ڴ���
	public static String LOCAL_SAVE_PATH = Environment.getExternalStorageDirectory()+"/.tch/";
	
	public static String IMAGE_SAVE_PATH = Environment.getExternalStorageDirectory()+"/cuimi/pictures/";

}
