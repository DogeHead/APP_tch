package com.example.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.Utils.NetWorkHelper;
import com.example.app.Utils.Urls;
import com.example.app.fragment.ForthFragment;
import com.example.app.fragment.HomeFragment;
import com.example.app.http.MyData;
import com.example.app.model.GlobalParams;

public class LoginActivity extends Activity implements OnClickListener{
	
	private static final int MSG_LOGIN_SUCCESS=100;
	private static final int MSG_UNKOWN_ERROR=101;
	private static final int MSG_WRONG_USER=102;
	public static final int RESULT_CODE=1;
	private EditText et_account,
	et_pwd;
	private Button bt_login;
	private String result="no";
	private String userName="未命名";
	private Intent resultIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		Log.i("LoginActivity","LoginActivity.onCreate");
		resultIntent=new Intent();
		//先将返回的intent值设为login失败
		resultIntent.putExtra(ForthFragment.RESULT_KEY,ForthFragment.RESULT_NO);

		this.setResult(RESULT_CODE, resultIntent);
		//获得各控件句柄
		InitControl();
		
	}
	private void InitControl(){
		et_account=(EditText)findViewById(R.id.et_account_loginAty);
		et_pwd=(EditText)findViewById(R.id.et_pwd_loginAty);
		bt_login=(Button)findViewById(R.id.btn_login_loginAty);
		
		bt_login.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_login_loginAty:
			loginButtonClick();

			break;
		}
	}
	
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
            super.handleMessage(msg);
            
            switch (msg.what) {
			case MSG_LOGIN_SUCCESS:
				loginSuccess();
				
				break;
			case MSG_UNKOWN_ERROR:
				Toast.makeText(LoginActivity.this, "异常错误，请举报",Toast.LENGTH_SHORT).show();
				break;
			case MSG_WRONG_USER:
				Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
            }}
	};
	private void loginSuccess()
	{
		GlobalParams.account=et_account.getText().toString();
		GlobalParams.username=userName;
		GlobalParams.isLoging=true;
		resultIntent.putExtra(ForthFragment.RESULT_KEY, ForthFragment.RESULT_YES);
		this.setResult(RESULT_CODE, resultIntent);
		this.finish();
	}
	private void loginButtonClick()
	{
		String account=et_account.getText().toString(),
				pwd=et_account.getText().toString();
		if(account.length()<6){
				Toast.makeText(this, "账号长度需>=6", Toast.LENGTH_SHORT).show();
				return;
			}
		if(pwd.length()<6){
			Toast.makeText(this, "密码长度需>=6", Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread(new Runnable(){

			@Override
			public void run() {
					 result=MyData.login(et_account.getText().toString(),et_pwd.getText().toString());
					 if(result.equals(Urls.FAIL))
						{
							Log.i("getSkill","异常错误");
							handler.sendEmptyMessage(MSG_UNKOWN_ERROR);
							return;
						}
					 if(result.equals(MyData.flag_wrong))
					 {
						 Log.i("login","fail");
						 handler.sendEmptyMessage(MSG_WRONG_USER);
						 return;
					 }
					 Log.i("login","success");
					 
					//JSONObject jsonObject=JSONObject.fromObject(result);
					 try {
						JSONObject json =new JSONObject(result);
						userName=json.getString(MyData.KEY_USERNAME);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.i("json.get","fail");
						e.printStackTrace();
						return;
					}
					 handler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
			}	
		}).start();
	}
}


