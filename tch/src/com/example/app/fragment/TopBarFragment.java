package com.example.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.R;

public class TopBarFragment extends Fragment{
	private View theView;
	private TextView tv;
	private ImageView iv;
	private String tvText;
	private Activity activity;
	public TopBarFragment(String tvText,Activity act)
	{
		this.tvText=tvText;
		activity=act;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_topbar,container,false);
		Log.i("TopBarFragment",tvText);
		
		InitControl();
		
		return theView;
		}
	
	private void InitControl()
	{
		tv=(TextView)theView.findViewById(R.id.tv_text_topbar);
		iv=(ImageView)theView.findViewById(R.id.iv_back_topbar);
		
		tv.setText(tvText);
		iv.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity.finish();
			}
			
		});
	}
}