package com.example.app.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.app.Utils.NetWorkHelper;
import com.example.app.Utils.Urls;

public class MyData {

	//获取信息所需key值
	public static final String KEY_ACCOUNT="account";
	public static final String KEY_PWD="password";
	
	
	//login失败返回值
	public final static String flag_success="success";
	public final static String flag_wrong="wrong account or pwd";
	public final static String flag_accountExist="AccountExist";
	
	//login成功返回key值
	public static final String KEY_USERNAME="user_name";

	
	public static String login(String userAccount,String userPwd)
	{
		String result;
		HttpClient httpClient=new DefaultHttpClient();
		try{
			 List<NameValuePair> params=new ArrayList<NameValuePair>();
			 params.add(new BasicNameValuePair(KEY_ACCOUNT, userAccount));
			 params.add(new BasicNameValuePair(KEY_PWD, userPwd));
			 
			 result=NetWorkHelper.downloadTextByPost(Urls.URL_LOGIN,
					 params);
			 return result;
	         
		}catch(Exception e){
			e.printStackTrace();
			return Urls.FAIL;
		}
		
	}
}
