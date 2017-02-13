package com.example.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SearchMoreActivity extends FragmentActivity{

	EditText et_search;
	Button bt_search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_more);
		Log.i("SearchMoreActivity","SearchMoreActivity.onCreate");

		
		InitControl();
	}
	private void InitControl()
	{
		et_search=(EditText)findViewById(R.id.et_SearchMoreActivity_search);
		bt_search=(Button)findViewById(R.id.bt_SearchMoreActivity_search);

	}
}
