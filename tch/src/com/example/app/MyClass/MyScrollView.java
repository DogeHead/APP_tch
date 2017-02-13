package com.example.app.MyClass;

import com.example.app.MyInterface.OnLoadInterface;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView{
	OnLoadInterface onLoad;
	
	public MyScrollView(Context context)
    {
        super(context);
    }
    public MyScrollView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        View view = (View)getChildAt(getChildCount()-1);
        int d = view.getBottom();
        d -= (getHeight()+getScrollY());
//        Log.i("onScrollChanged","scrollY"+getScrollY());
        if(d<=50)
        {
            //you are at the end of the list in scrollview 
            //do what you wanna do here
        	loadData();
        }
        else
            super.onScrollChanged(l,t,oldl,oldt);
    }
    
    public void setOnLoad(OnLoadInterface load)
    {
    	onLoad=load;
    }
    public void loadData()
    {
    	if(onLoad!=null)
    	onLoad.onLoad();
    }
}
