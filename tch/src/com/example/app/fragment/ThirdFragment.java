package com.example.app.fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.app.MyCollectionActivity;
import com.example.app.MyGoodsActivity;
import com.example.app.R;
import com.example.app.MyClass.CircleImageView;
import com.example.app.Utils.Urls;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

public class ThirdFragment extends Fragment implements OnClickListener{
	/*
	 * 该fragment的布局文件
	 */
	private View theView;
	/*
	 * 圆形头像
	 */
	private CircleImageView iv_pic;
	/*
	 * 弹窗
	 */
	private PopupWindow popupWindow;
	/*
	 * 我的 标签
	 */
	private TextView tv_my;
	private static final int REQUEST_CAMERA = 1; //相机 
    private static final int REQUEST_PIC = 0;  //相册
    private static final int REQUEST_CUT = 2;//剪切
    private Bitmap myBitmap;  
    private byte[] mContent;  

    private Button btn_myorder,
    btn_mycollection;
    private RadioButton rbt_mycollection,
    rbt_waitPay,//待支付
    rbt_waitLearn,//待上课
    rbt_waitEvaluate;//待评价
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_third,container,false);
		Log.i("ThirdFragment","ThirdFragment.onCreateView");
		
		InitControl();
		return theView;
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.iv_pic_third:
			showPopWindow(theView, this.getContext(), Gravity.CENTER, R.layout.popup_identy, 0, 0);
			break;
		case R.id.bt_selectFromPic_pop:
			Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);  
            getImage.addCategory(Intent.CATEGORY_OPENABLE);  
            getImage.setType("image/jpeg");  
            startActivityForResult(getImage, REQUEST_PIC);
			break;
		case R.id.bt_takePic_pop:
			Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");  
            startActivityForResult(getImageByCamera, REQUEST_CAMERA);
			break;
		case R.id.btn_myCollection_third:
		case R.id.rbt_myCollection_third:
			Intent intent1=new Intent();
			intent1.setClass(this.getContext(),MyCollectionActivity.class);
			startActivity(intent1);
			break;
		case R.id.btn_myorder_third:
		case R.id.rbt_waitPay_third:
		case R.id.rbt_waitLearn_third:
		case R.id.rbt_waitEvaluate_third:
			Intent intent2=new Intent();
			intent2.putExtra(Urls.EXTRA_VIEW,v.getId());
			intent2.setClass(this.getContext(),MyGoodsActivity.class);
			startActivity(intent2);
			break;
			
		case R.id.ll_next_moreInfo:
		case R.id.iv_close_popup:
			popupWindow.dismiss();
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		ContentResolver resolver=this.getActivity().getContentResolver();
		Log.i("onActivityResult","in");
		switch(requestCode)
		{
		case REQUEST_CAMERA:
		try{
			popupWindow.dismiss();
			Bundle extras=data.getExtras();
			myBitmap=(Bitmap)extras.get("data");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
            mContent = baos.toByteArray(); 
            iv_pic.setImageBitmap(getPicFromBytes(mContent, null));
            Log.i("onActivityResult","拍照");
		}catch(Exception e){
		}
			break;
		case REQUEST_PIC:
			try{
				//获得图片Uri
				Uri originalUri=data.getData();
				//
				Intent intent=new Intent();
				
				intent.setAction("com.android.camera.action.CROP");
				intent.setDataAndType(originalUri,"image/*");
				intent.putExtra("crop", "true");  
                intent.putExtra("aspectX", 1);// 裁剪框比例  
                intent.putExtra("aspectY", 1);  
                intent.putExtra("outputX", 150);// 输出图片大小  
                intent.putExtra("outputY", 150);  
                intent.putExtra("return-data", true);
                this.startActivityForResult(intent, REQUEST_CUT);             
//				//将图片内容解析成字节数组
//				mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));  
//                // 将字节数组转换为ImageView可调用的Bitmap对象  
//                myBitmap = getPicFromBytes(mContent, null);  
//                // //把得到的图片绑定在控件上显示  
//                iv_pic.setImageBitmap(myBitmap);
////                iv_pic.setBackground(new BitmapDrawable(myBitmap));
//				Log.i("onActivityResult","获得相册图片");
				
			}catch(Exception e){
			}
			break;
		case REQUEST_CUT:
			popupWindow.dismiss();
			if(resultCode == Activity.RESULT_OK){
			Bitmap bmap = data.getParcelableExtra("data");  
			iv_pic.setImageBitmap(bmap);
			
			// 图像保存到文件中  
//            FileOutputStream foutput = null;  
//            try {  
//                foutput = new FileOutputStream(this.imageFile);  
//                bmap.compress(Bitmap.CompressFormat.PNG, 100, foutput);  
//            } catch (FileNotFoundException e) {  
//                e.printStackTrace();  
//            }finally{  
//                if(null != foutput){  
//                    try {  
//                        foutput.close();  
//                    } catch (IOException e) {  
//                        e.printStackTrace();  
//                    }  
//                }  
			}
//			Uri originalUri=data.getData();
//			//将图片内容解析成字节数组
//			try {
//				Log.i("onActivityResult","try");
//				mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));
//				// 将字节数组转换为ImageView可调用的Bitmap对象  
//				myBitmap = getPicFromBytes(mContent, null);  
//				iv_pic.setImageBitmap(myBitmap);
//			}catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
            
			
			break;
		}
		
	}
	private void InitControl()
	{
		iv_pic=(CircleImageView)theView.findViewById(R.id.iv_pic_third);
		tv_my=(TextView)theView.findViewById(R.id.txt_ThirdFragment_me);
		btn_myorder=(Button)theView.findViewById(R.id.btn_myorder_third);
		rbt_mycollection=(RadioButton)theView.findViewById(R.id.rbt_myCollection_third);
		rbt_waitPay=(RadioButton)theView.findViewById(R.id.rbt_waitPay_third);
		rbt_waitLearn=(RadioButton)theView.findViewById(R.id.rbt_waitLearn_third);
		rbt_waitEvaluate=(RadioButton)theView.findViewById(R.id.rbt_waitEvaluate_third);
		btn_mycollection=(Button)theView.findViewById(R.id.btn_myCollection_third);
		
		iv_pic.setOnClickListener(this);
		btn_myorder.setOnClickListener(this);
		rbt_mycollection.setOnClickListener(this);
		rbt_waitPay.setOnClickListener(this);
		rbt_waitLearn.setOnClickListener(this);
		rbt_waitEvaluate.setOnClickListener(this);
		btn_mycollection.setOnClickListener(this);
	}

	
	private void showPopWindow(View view,Context context,int gravity,int layout,int x,int y)
	{
		View contentView=LayoutInflater.from(context).inflate(layout, null);
		
		popupWindow=new PopupWindow(contentView,LayoutParams.MATCH_PARENT,
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
			final Button bt_takePic=(Button)contentView.findViewById(R.id.bt_takePic_pop);
			final Button bt_selectFromPic=(Button)contentView.findViewById(R.id.bt_selectFromPic_pop);
			final ImageView iv_close=(ImageView)contentView.findViewById(R.id.iv_close_popup);
			iv_close.setOnClickListener(this);
			bt_takePic.setOnClickListener(this);
			bt_selectFromPic.setOnClickListener(this);
			break;
		case R.layout.popup_moreinfo:
			final LinearLayout iv_back=(LinearLayout)contentView.findViewById(R.id.ll_next_moreInfo);
			iv_back.setOnClickListener(this);
			break;
		}
		popupWindow.showAtLocation(view, gravity, x, y);
		
	}
	
	
	public static Bitmap getPicFromBytes ( byte[] bytes , BitmapFactory.Options opts )  
    {  
        if (bytes != null)  
            if (opts != null)  
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);  
            else  
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);  
        return null;  
    }  
  
    public static byte[] readStream ( InputStream inStream ) throws Exception  
    {  
        byte[] buffer = new byte[1024];  
        int len = -1;  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        while ((len = inStream.read(buffer)) != -1)  
        {  
            outStream.write(buffer, 0, len);  
        }  
        byte[] data = outStream.toByteArray();  
        outStream.close();  
        inStream.close();  
        return data;  
    }  
}