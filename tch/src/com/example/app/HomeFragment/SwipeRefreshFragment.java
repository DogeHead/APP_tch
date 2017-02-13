package com.example.app.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.app.R;
import com.example.app.MyClass.RefreshLayout;
import com.example.app.MyClass.RefreshLayout.OnLoadListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SwipeRefreshFragment extends Fragment{
	final int MSG_REFRESH=1;
	
	private View theView;
	private SwipeRefreshLayout swipeRefreshLayout;
	
	private RefreshLayout refreshLayout;
	
	private Handler handler;
	private ListView lv_show;
	private List<String> mDatas=new ArrayList<String>(Arrays.asList("c","html","java","c++"));
	private ArrayAdapter<String> mAdapter;
;	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_swiperefresh,container,false);
		Log.i("SwipeRefreshFragment","SwipeRefreshFragment.onCreateView");
		
		InitControl();
		return theView;
		}
	private void InitControl(){
		refreshLayout=(RefreshLayout)theView.findViewById(R.id
				.swipeRefresh_swipeRefreshFragement_refresh);
		lv_show=(ListView)theView.findViewById(R.id.lv_swipeRefreshFragment_show);
		
		InitHandler();
		
		InitSwipeRefreshLayout();
		
		InitListView();
		
	}
	private void InitHandler(){
		handler=new Handler(){
			public void handleMessage(Message msg){
				switch(msg.what){
				case MSG_REFRESH:
				{
					mDatas.addAll(Arrays.asList("JSON","JS"));
					mAdapter.notifyDataSetChanged();
					swipeRefreshLayout.setRefreshing(false);
					break;
				}
				}
			}
		};
	}
	private void InitSwipeRefreshLayout(){
		/*
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(MSG_REFRESH);
			}
			
		});
		*/
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Log.i("refreshLayout","onRefresh");
				refreshLayout.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mDatas.add(new Date().toGMTString());
				mAdapter.notifyDataSetChanged();
				refreshLayout.setRefreshing(false);
			}
			
		},1000);
			}
			
		});
		
		refreshLayout.setOnLoadListener(new OnLoadListener(){

			@Override
			public void onLoad() {
				// TODO Auto-generated method stub
				Log.i("refreshLayout","onLoad");
				refreshLayout.postDelayed(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mDatas.add(new Date().toGMTString());
						mAdapter.notifyDataSetChanged();
						refreshLayout.setLoading(false);
					}
					
				}, 1500);
			}
			
		});
	}
	private void InitListView(){
		mAdapter=new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,mDatas);
		lv_show.setAdapter(mAdapter);
	}
}
