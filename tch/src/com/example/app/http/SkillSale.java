package com.example.app.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.app.Utils.NetWorkHelper;
import com.example.app.Utils.Urls;
import com.example.app.model.GlobalParams;

import android.util.Log;

public class SkillSale {

	//��ȡ�س������keyֵ
	public static final String KEY_MAXID="maxid";
	public static final String KEY_ID="skill_id";
	public static final String KEY_NAME="skill_name";
	public static final String KEY_INTRO="skill_intro";
	public static final String KEY_PRICE="skill_price";
	public static final String KEY_TIME="skill_time";
	public static final String KEY_SALE="skill_sale";
	public static final String KEY_SCORE="skill_score";
	public static final String KEY_SALER_NAME="saler_name";
	public static final String KEY_SKILL_TYPE="skill_type";


	//��ȡskillʧ�ܷ���ֵ
	public static final String flag_unknownError="UnknownError";
	
	//��ȡ���������س� 
	public static String getTwoSkill(int maxID)
	{
		Map<String,Integer> params=new HashMap<String,Integer>();
		params.put(KEY_MAXID, maxID);
		String result;
		//����URL
		StringBuffer sbURL=new StringBuffer();
		sbURL.append(Urls.URL_GETSKILL).append("?");
		for(Map.Entry<String,Integer> entry:params.entrySet())
		{
			//���Value��String,������,����append(URLEncoder.encode(entry.getValue(), "utf-8"));
			sbURL.append(entry.getKey()).append("=").append(entry.getValue());  
			sbURL.append("&");  
		}
		sbURL.deleteCharAt(sbURL.length() - 1);
        /*����HTTP Get����*/  
        HttpGet httpRequest = new HttpGet(sbURL.toString());  
        
        try  
        {  
          /*�������󲢵ȴ���Ӧ*/  
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
          /*��״̬��Ϊ200 ok*/  
          if(httpResponse.getStatusLine().getStatusCode() == 200)   
          {  
            /*��*/  
            String strResult = EntityUtils.toString(httpResponse.getEntity());  
            return strResult;
          }  
          return Urls.NETWORK_ERROR;
        }  
        catch (Exception e)  
        {   
          e.printStackTrace();  
          return Urls.FAIL;
        }  

	}
	//��ȡָ�����͵��س�
	public static String getSelectSkill(String type)
	{
		Map<String,String> params=new HashMap<String,String>();
		params.put(KEY_SKILL_TYPE, type);
		String result;
		//����URL
		StringBuffer sbURL=new StringBuffer();
		sbURL.append(Urls.URL_GETSELSKILL).append("?");
		for(Map.Entry<String,String> entry:params.entrySet())
		{
			//���Value��String,������,����append(URLEncoder.encode(entry.getValue(), "utf-8"));
			try {
				sbURL.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("endcode fali","��������ʧ��");
				return Urls.FAIL;
			}  
			sbURL.append("&");  
		}
		sbURL.deleteCharAt(sbURL.length() - 1);
        /*����HTTP Get����*/  
        HttpGet httpRequest = new HttpGet(sbURL.toString());  
        
        try  
        {  
          /*�������󲢵ȴ���Ӧ*/  
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
          /*��״̬��Ϊ200 ok*/  
          if(httpResponse.getStatusLine().getStatusCode() == 200)   
          {  
            /*��*/  
            String strResult = EntityUtils.toString(httpResponse.getEntity());  
            return strResult;
          }  
          return Urls.NETWORK_ERROR;
        }  
        catch (Exception e)  
        {   
          e.printStackTrace();  
          return Urls.FAIL;
        }  

	}
	//ͨ���س�����ȡ�γ���ϸ��Ϣ
	public static String getSkillInfo(String name)
	{
		Map<String,String> params=new HashMap<String,String>();
		params.put(KEY_NAME,name );
		String result;
		//����URL
		StringBuffer sbURL=new StringBuffer();
		sbURL.append(Urls.URL_GETSKILLINFO).append("?");
		for(Map.Entry<String,String> entry:params.entrySet())
		{
			//���Value��String,������,����append(URLEncoder.encode(entry.getValue(), "utf-8"));
			try {
				sbURL.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("endcode fali","��������ʧ��");
				return Urls.FAIL;
			}  
			sbURL.append("&");  
		}
		sbURL.deleteCharAt(sbURL.length() - 1);
        /*����HTTP Get����*/  
        HttpGet httpRequest = new HttpGet(sbURL.toString());  
        
        try  
        {  
          /*�������󲢵ȴ���Ӧ*/  
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
          /*��״̬��Ϊ200 ok*/  
          if(httpResponse.getStatusLine().getStatusCode() == 200)   
          {  
            /*��*/  
            String strResult = EntityUtils.toString(httpResponse.getEntity());  
            return strResult;
          }  
          return Urls.NETWORK_ERROR;
        }  
        catch (Exception e)  
        {   
          e.printStackTrace();  
          return Urls.FAIL;
        }  

	}

	public static boolean getSkillImage(String name)
	{
		Map<String,String> params=new HashMap<String,String>();
		params.put(KEY_NAME,name );
		//����URL
		StringBuffer sbURL=new StringBuffer();
		sbURL.append(Urls.URL_GETSKILLIMAGE).append("?");
		for(Map.Entry<String,String> entry:params.entrySet())
		{
			//���Value��String,������,����append(URLEncoder.encode(entry.getValue(), "utf-8"));
			try {
				sbURL.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("endcode fali","��������ʧ��");
				return false;
			}  
			sbURL.append("&");  
		}
		sbURL.deleteCharAt(sbURL.length() - 1);
		Log.i("getSkillImage","here");
        /*����HTTP Get����*/  
        return NetWorkHelper.downLoadPicture(sbURL.toString(), name, Urls.IMAGE_SAVE_PATH);

	}
}
