package com.example.app.Utils;

import android.os.Environment;

public class Urls {
	
	public static final String ROOT="http://42.96.134.20:8081/";
	public static final String URL_LOGIN=ROOT+"login";
	public static final String URL_REGISTER=ROOT+"register";
	public static final String URL_GETSKILL=ROOT+"getSkill";
	public static final String URL_GETSELSKILL=ROOT+"getSelSkill";
	public static final String URL_GETSKILLINFO=ROOT+"getSkillInfo";
	public static final String URL_GETSKILLIMAGE=ROOT+"getSkillImage";
	
	public static final String FAIL="fail";
	public static final String NETWORK_ERROR="network_error";
	//register
	
    public static final int DIVISION_BIG=30;
    
    public static final String EXTRA_COURSENAME="courseName";
    public static final String EXTRA_VIEW="view";
    
    
    public static String LOCAL_SAVE_PATH = Environment.getExternalStorageDirectory()+"/";
    public static String IMAGE_SAVE_PATH=LOCAL_SAVE_PATH+"tch/images/";
}
