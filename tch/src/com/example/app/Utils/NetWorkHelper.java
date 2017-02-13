package com.example.app.Utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class NetWorkHelper {

	private static String TAG="NetWorkHelper";
	public static String downloadTextByPost(String navUrl,List<NameValuePair> params){
		HttpPost httpPost=new HttpPost(navUrl);
		HttpResponse httpResponse =null;
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient client=new DefaultHttpClient();
			
			//请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			//读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,5000);
			httpResponse=client.execute(httpPost);
			
			if(httpResponse.getStatusLine().getStatusCode()==200){
				String result=EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
				
				return result;
			}
			else
				return "";
		}catch(Exception e){
			Log.i(TAG,"获取数据失败");
			return "";
		}
	}
	
	public static String downloadTextByPost(String navUrl,Map<String,Object> map){
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		for(String key:map.keySet()){
			list.add(new BasicNameValuePair(key,map.get(key).toString()));
		}
		return downloadTextByPost(navUrl,list);
	}
	
	
	
	public static boolean downLoadPicture(String url,String imageName,String localPath){
		try{
			String fileName = localPath+imageName;	
			Log.i(TAG,"filePath:"+fileName);
			
			File f = new File(fileName);
			if(f.exists()) return true;
			
			File pathFile = new File(localPath);
			if(!pathFile.exists()){
				pathFile.mkdirs();
			}
			if(!new File(fileName).exists()){
				byte[] bs = NetWorkHelper.getImage(url);
				Bitmap bm = BitmapFactory.decodeByteArray(bs, 0, bs.length);
				NetWorkHelper.saveImageFile(bm, fileName);
			}
			return true;
		}catch(Exception e){
			Log.v(TAG,"error:"+e.toString());
			return false;
		}
	}
	
	private static void saveImageFile(Bitmap bm, String fileName) throws IOException {
        File myCaptureFile = new File(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        Log.i("saveImageFile","here");
        bos.flush();
        bos.close();
    }
	
	private static byte[] getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return readStream(inStream);
        }
        return null;
    }
	
	private static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
}
