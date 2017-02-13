package com.example.app.HomeFragment;

import java.util.ArrayList;

import com.example.app.R;
import com.example.app.fragment.HomeFragment;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;
import android.widget.ImageView.ScaleType;

public class AdverFragment extends Fragment{
	View theView;
	final int MSG_ADVER_NEXT=1,
			MSG_ADVER_PREV=2,
			MSG_ADVER_CURRENT=3;
	private Resources resources;
	private Handler handler_adver;
	private ArrayList<RadioButton> radioList_adver;
	private Drawable img_point_nor,img_point_sel;//ѡ����δѡ���СԲ��
	private ViewFlipper viewFlipper;
	float xDown_viewFlipper,xCur_viewFlipper;//����ʱ����ʱ���ƶ�ʱ������
	private int[] images_viewFlipper=new int[]{
			R.drawable.ad1,R.drawable.ad2,R.drawable.ad3
	};
	private RadioGroup radioGroup_adver;
	boolean isSatisfy_viewFlipper=true;//�ж��Ƿ��Ѿ���������

	private Runnable run_ViewFlipper;
	int curAdver;//��ǰ���
	int interval_viewFlipper;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_viewflipper_adver,container,false);
		Log.i("AdverFragment","AdverFragment.onCreateView");
		InitControl();
		return theView;
		}
	@Override
	public void onPause(){
		super.onPause();
		handler_adver.removeCallbacks(run_ViewFlipper);
	}
	@Override
	public void onStart(){
		super.onStart();
		handler_adver.postDelayed(run_ViewFlipper,interval_viewFlipper);
	}
	
	private void InitControl()
	{
		resources=getResources();
		viewFlipper=(ViewFlipper)theView.findViewById(R.id.viewflipper_homeFragment_show);
		radioGroup_adver=(RadioGroup)theView.findViewById(R.id.radioGroup_home_adver);
		   //radioButton���
				radioList_adver=new ArrayList<RadioButton>();
				   //СԲ��ͼ��
				img_point_nor=resources.getDrawable(R.drawable.point_adver_nor);
				img_point_nor.setBounds(0,0,img_point_nor.getMinimumWidth(),
						img_point_nor.getMinimumHeight());
				img_point_sel=resources.getDrawable(R.drawable.point_adver_sel);
				img_point_sel.setBounds(0,0,img_point_sel.getMinimumWidth(),
						img_point_sel.getMinimumHeight());
		   //�ֲ����
				interval_viewFlipper=4000;
				curAdver=0;
				//threadFlag=true;
				   //Handler
				handler_adver=new Handler(){
					@Override
					public void handleMessage(Message msg){
						switch(msg.what){
						case MSG_ADVER_NEXT:
						{
							showNext();
							curAdver = updatePoint(curAdver+1);
						}break;
						case MSG_ADVER_PREV:
						{
							showPrevious();
							curAdver = updatePoint(curAdver-1);
						}break;
						case MSG_ADVER_CURRENT:
						{
							curAdver=updatePoint(curAdver);
						}break;
						}
					}
				};
		InitViewFlipper();
		
		InitRadioGroup();
	}
	
	/*
	 * ��ʼ��viewFlipper
	 */
	private void InitViewFlipper(){
		for(int i=0;i<images_viewFlipper.length;i++){
			ImageView iv=new ImageView(this.getContext());
			iv.setImageResource(images_viewFlipper[i]);
			//����ͼƬƥ��imageView��ʽ
			iv.setScaleType(ScaleType.FIT_CENTER);
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			viewFlipper.addView(iv,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		//һ��ʼ�Ǵ�������
		viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this.getContext(),
				R.anim.push_left_in));
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this.getContext(),
				R.anim.push_left_out));
		viewFlipper.setAutoStart(false);
		viewFlipper.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);

				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:{
					xDown_viewFlipper=event.getRawX();

					isSatisfy_viewFlipper=true;
					break;
					}
				case MotionEvent.ACTION_MOVE:{
					xCur_viewFlipper=event.getRawX();
					if(isSatisfy_viewFlipper&&xCur_viewFlipper-xDown_viewFlipper<-50){
						handler_adver.sendEmptyMessage(MSG_ADVER_NEXT);
						
						isSatisfy_viewFlipper=false;
					}else if(isSatisfy_viewFlipper&&xCur_viewFlipper-xDown_viewFlipper>50){
						handler_adver.sendEmptyMessage(MSG_ADVER_PREV);
						
						isSatisfy_viewFlipper=false;
					}
					}break;
				case MotionEvent.ACTION_UP:

					isSatisfy_viewFlipper=false;
					break;
				}
				return true;
			}
			
		});
		//��ʼ��Thread
		run_ViewFlipper=new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				Log.i("viewFlipper","1");
				handler_adver.sendEmptyMessage(MSG_ADVER_NEXT);
				handler_adver.postDelayed(this, interval_viewFlipper);
			}
		};
		
	}
	//��ʾviewFlipper����һ��ͼ/��һ��ͼ
		private void showNext(){
				// ��Ӷ���,��������
    			viewFlipper.setInAnimation(AnimationUtils.loadAnimation(AdverFragment.this.getContext(),
    					R.anim.push_left_in));
    			viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(AdverFragment.this.getContext(),
    					R.anim.push_left_out));
    			viewFlipper.showNext();
			}
		private void showPrevious(){
				//��������
				viewFlipper.setInAnimation(AnimationUtils.loadAnimation(AdverFragment.this.getContext(),
    					R.anim.push_right_in));
    			viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(AdverFragment.this.getContext(),
    					R.anim.push_right_out));
    			viewFlipper.showPrevious();
			}
	//����viewFlipper
		/*
		 * ��ʼ��RadioGroup
		 */
		private void InitRadioGroup(){
			//����СԲ��
			for(int i=0;i<images_viewFlipper.length;i++){
				RadioButton radioButton=(RadioButton)LayoutInflater.from(this.getContext()).inflate(
						R.layout.roundpoint,null);
				radioButton.setClickable(false);
				radioList_adver.add(radioButton);
				radioGroup_adver.addView(radioButton);
			}
			Log.i("InitRadoiGroup","0");
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler_adver.sendEmptyMessage(MSG_ADVER_CURRENT);
					Log.i("InitRadoiGroup","new Thread");
				}
				
			}).start();
		}
		//�����µ�index
		private int updatePoint(int newPos){
			if(newPos<0){
				newPos=images_viewFlipper.length-1;
			}
			else if(newPos>=images_viewFlipper.length)
			{
				newPos=0;
			}
			//�Ƚ�ԭԲ��ı�Ϊδ����״̬
			radioList_adver.get(curAdver).setCompoundDrawables(img_point_nor,null,null,null);	
			//����Բ��ı伤��״̬
			radioList_adver.get(newPos).setCompoundDrawables(img_point_sel,null,null,null);
			return newPos;
		}
		//����RadioGroup
}
