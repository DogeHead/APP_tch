package com.example.app.fragment;

import com.example.app.LoginActivity;
import com.example.app.R;
import com.example.app.model.GlobalParams;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ForthFragment extends Fragment implements OnClickListener{
	View theView;
	private final int MSG_ISLOGINING=0;
	
	private final int REQ_LOGIN_CODE=1;
	public static final String RESULT_KEY="login_result";
	public static final String RESULT_YES="y";
	public static final String RESULT_NO="n";
	private Button bt_exitOrLogin;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_forth,container,false);
		Log.i("ForthFragment","ForthFragment.onCreateView");
		
		InitControl();
		return theView;
	}
	@Override
	public void onPause(){
		super.onPause();

	}
	@Override
	public void onStart(){
		super.onStart();

	}

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.i("ForthFragment.onActivityResult", "requestCode="+requestCode);
    	switch(requestCode)
    	{
    	case REQ_LOGIN_CODE:
    		String result = data.getExtras().getString(RESULT_KEY);//得到新Activity 关闭后返回的数据\
    		Log.i("ForthFragment.onActivityResult",result);
    		if(result.equals(RESULT_YES)){
    			setLoginState();
    		}
    		break;
    	}
        
        
    }
	private void InitControl(){
		bt_exitOrLogin=(Button)theView.findViewById(R.id.bt_exitOrLogin_forth);
		
		bt_exitOrLogin.setOnClickListener(this);
		InitLoginButton();
		

	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bt_exitOrLogin_forth:
			if(false==GlobalParams.isLoging)
			{
				Intent intent=new Intent();
				intent.setClass(this.getContext(), LoginActivity.class);
				startActivityForResult(intent,REQ_LOGIN_CODE);
			}
			else{
				setExitState();
			}
			break;
		}
	}
	private void setLoginState()
	{
		bt_exitOrLogin.setText("退出登录");
		bt_exitOrLogin.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.button_red));
	}
	private void setExitState()
	{
		GlobalParams.isLoging=false;
		GlobalParams.username="未登陆";
		bt_exitOrLogin.setText("登录");
		bt_exitOrLogin.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.button_blue2));

	}
	public void InitLoginButton()
	{
		if(true==GlobalParams.isLoging)
		{
			bt_exitOrLogin.setText("退出登录");
			bt_exitOrLogin.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.button_red));
		}
		else
		{
			bt_exitOrLogin.setText("登录");
			bt_exitOrLogin.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.button_blue2));
		}
	}
}