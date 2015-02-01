package com.coderui.gkhntUI;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.coderui.gkhntDispatch.DispatchService;
import com.coderui.gkhntutil.CharacterUtil;
import com.coderui.gkhntutil.Task;
import com.coderui.gkhntutil.XMLuntil;

public class QueryDetialActivity extends Activity implements ModelActivity {
	String TAG="QueryDetialActivity";
	private Spinner spinner;
	private EditText et_task_number;
	private TextView tv_start_date,tv_end_date;
	private Button btn_search,btn_reset; 
	private int int_search_type;
	private String task_single_number,str_start_date,str_end_date;
	private String[] search_type;
	private Calendar calendar;
	private Map<String, Object> taskParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_detial);
		
		search_type=getResources().getStringArray(R.array.SEARCH_TYPE);
		
		spinner=(Spinner) findViewById(R.id.spinner_search_type);
		et_task_number=(EditText) findViewById(R.id.et_task_single_number);
		tv_start_date=(TextView) findViewById(R.id.tv_start_date);
		tv_end_date=(TextView) findViewById(R.id.tv_end_date);
		btn_search=(Button) findViewById(R.id.btn_search);
		btn_reset=(Button) findViewById(R.id.btn_reset);
		
		tv_start_date.setText(CharacterUtil.getCurrentDate());
		tv_start_date.setOnClickListener(new MyOnClickListener());

		tv_end_date.setText(CharacterUtil.getCurrentDate());
		tv_end_date.setOnClickListener(new MyOnClickListener());
		
		btn_search.setOnClickListener(new MyOnClickListener());
		btn_reset.setOnClickListener(new MyOnClickListener());
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, search_type);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int_search_type=arg2;
				Log.v(TAG,"选择了"+arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				int_search_type=0;
			}
		});
	}
	
	private void setDatePicker(final TextView date){
		calendar = Calendar.getInstance();
		DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DATE, dayOfMonth);
				date.setText(year+"-"+format(monthOfYear+1)+"-"+format(dayOfMonth));
			}
		};
		new DatePickerDialog(this, dateSet,
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE)).show();
	}
	
	private String format(int x)  
    {  
      String s=""+x;  
      if(s.length()==1) s="0"+s;  
      return s;  
    }  

	@Override
	public void init() {
		// TODO Auto-generated method stub
		DispatchService.addActivity(this);
		task_single_number=et_task_number.getText().toString();
		str_start_date=tv_start_date.getText().toString();
		str_end_date=tv_end_date.getText().toString();
		taskParams = new HashMap<String, Object>();
		//获取搜索消息的xml格式
		String xml=XMLuntil.constructQueryXml(int_search_type, task_single_number, str_start_date, str_end_date);
		Log.v(TAG,xml);
		taskParams.put("xml", xml);
		// task_id==CharacterUtil.SENDQUERYREQUEST
		Task task_send = new Task(CharacterUtil.SENDQUERYREQUEST, taskParams);
		DispatchService.addTask(task_send);
		
		//task_id=CharacterUtil.ACCEPTQUERYRESULT
		Task task_accept =new Task(CharacterUtil.ACCEPTQUERYRESULT,null);
		DispatchService.addTask(task_accept);
	}

	@Override
	public void refreshUI(Object... objects) {
		// TODO Auto-generated method stub
		String xml = (String) objects[0];
		Intent intent=new Intent(QueryDetialActivity.this,QueryResultActivity.class);
		intent.putExtra("result", xml);
		startActivity(intent);
	}
	
	class MyOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.tv_start_date:
				setDatePicker(tv_start_date);
				break;
			case R.id.tv_end_date:
				setDatePicker(tv_end_date);
				break;
			case R.id.btn_search:
				init();
				break;
			case R.id.btn_reset:
				break;
			}
		}
	}
}

