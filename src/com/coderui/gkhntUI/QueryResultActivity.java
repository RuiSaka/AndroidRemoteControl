package com.coderui.gkhntUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QueryResultActivity extends Activity {
	private TextView tv_result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_result);
		Intent intent=getIntent();
		String result=intent.getStringExtra("result");
		
		tv_result=(TextView) findViewById(R.id.results);
		tv_result.setText("ËÑË÷½á¹ûÎª:"+result);
	}
}
