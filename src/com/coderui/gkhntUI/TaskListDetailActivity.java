package com.coderui.gkhntUI;

import java.util.Map;

import com.coderui.gkhntutil.CharacterUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskListDetailActivity extends Activity {
	
	private TextView tv_detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_list_detail);
		
		tv_detail=(TextView) findViewById(R.id.textView1);
		Intent intent = getIntent();
		int id=intent.getIntExtra("id", 0);
		Map<String,String> map=(Map<String, String>) TaskListActivity.list.get(id);
		String detail="";
		for(int i=0;i<CharacterUtil.FORMFIELD.length;i++){
			detail=detail+map.get(CharacterUtil.FORMFIELD[i]);
		}
		tv_detail.setText(detail);
	}
}
