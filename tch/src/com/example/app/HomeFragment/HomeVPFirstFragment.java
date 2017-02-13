package com.example.app.HomeFragment;

import java.util.ArrayList;

import com.example.app.R;
import com.example.app.SpecialListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeVPFirstFragment extends Fragment implements OnClickListener{
	
	private View theView;
	private ArrayList<Button> btnArr=new ArrayList<Button>();
	private final int ID_START=1000;
	private String[] btnStr=new String[]{
			"游戏","摄影","学习","乐器","美工","运动","电脑","全部"
	};
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_home_vp_first,container,false);
		Log.i("HomeVPFirstFragment","HomeVPFirstFragment.onCreateView");
		InitSelectButton();
		return theView;
		}
	
	
	private void InitSelectButton() {
		// TODO Auto-generated method stub
		btnArr.add((Button)theView.findViewById(R.id.btn_game));
		btnArr.add((Button)theView.findViewById(R.id.btn_photograph));
		btnArr.add((Button)theView.findViewById(R.id.btn_study));
		btnArr.add((Button)theView.findViewById(R.id.btn_instrument));
		btnArr.add((Button)theView.findViewById(R.id.btn_art));
		btnArr.add((Button)theView.findViewById(R.id.btn_sports));
		btnArr.add((Button)theView.findViewById(R.id.btn_computer));
		btnArr.add((Button)theView.findViewById(R.id.btn_more));
		for(int i=0;i<btnArr.size();++i)
		{
			btnArr.get(i).setId(ID_START+i);
			btnArr.get(i).setOnClickListener(this);
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(HomeVPFirstFragment.this.getContext(),SpecialListActivity.class);
		intent.putExtra(SpecialListActivity.EX_TYPE,btnStr[v.getId()-ID_START]);
		startActivity(intent);
	}
}
