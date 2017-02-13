package com.example.app.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.app.R;

public class PopupWindowUtils{

	/*
	 * view 所依附的视图
	 * context
	 * id popupWindow的布局
	 * gravity 重心
	 * x the popup's x location offset
	 * y the popup's y location offset
	 */
	public static void showPopWindow(View view,Context context,int gravity,int layout,int x,int y)
	{
		View contentView=LayoutInflater.from(context).inflate(layout, null);
		
		final PopupWindow popupWindow=new PopupWindow(contentView,LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT,true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				//如果为true，事件将被拦截，
				return false;
			}
			
		});
		switch(layout)
		{
		case R.layout.popup_identy:
			final ImageView iv_close=(ImageView)contentView.findViewById(R.id.iv_close_popup);
			iv_close.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();
				}
				
			});
			break;
		case R.layout.popup_moreinfo:
			final LinearLayout iv_back=(LinearLayout)contentView.findViewById(R.id.ll_next_moreInfo);
			iv_back.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();
				}
				
			});
			break;
		}
		popupWindow.showAtLocation(view, gravity, x, y);
		
	}
	
}
