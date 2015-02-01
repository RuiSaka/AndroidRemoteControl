package com.coderui.gkhntUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.coderui.gkhntDispatch.DispatchService;
import com.coderui.gkhntUIadapter.TaskListAdapter;
import com.coderui.gkhntutil.CharacterUtil;
import com.coderui.gkhntutil.Task;
import com.coderui.gkhntutil.XMLuntil;

public class TaskListActivity extends Activity implements ModelActivity {
	private String TAG = "TaskListActivity";
	private RelativeLayout loadMoreLaout;
	private Button btn_loadMore;
	private ListView listView;
	private Map<String, Object> taskParams;
	public static List<Map<String, String>> list=new ArrayList<Map<String,String>>();;
	private TaskListAdapter adapter;
	private boolean isLoadMore=false;
	private int loadTime=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_list);
		
		loadMoreLaout = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.loadmoredata, null);
		btn_loadMore = (Button) loadMoreLaout.findViewById(R.id.loadmore);
		btn_loadMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_loadMore.setText("加载中...");
				loadTime++;
				sendTask(loadTime);
			}
		});

		listView = (ListView) findViewById(R.id.task_of_list);
		listView.addFooterView(loadMoreLaout);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				Map<String,String> map=(Map<String, String>) list.get(arg2);
				Intent intent=new Intent(TaskListActivity.this,TaskListDetailActivity.class);
				intent.putExtra("id", arg2);
				startActivity(intent);
			}
		});
		sendTask(loadTime);
	}

	private void sendTask(int time) {
		init();
		// task_id==CharacterUtil.REQUESTTASTLIST
		taskParams = new HashMap<String, Object>();
		String send_xml = XMLuntil.structRequestXml(CharacterUtil.REQUESTTASTLIST,time);
		taskParams.put("xml", send_xml);
		Log.v(TAG, "发出请求任务");
		Task task_send = new Task(CharacterUtil.REQUESTTASTLIST, taskParams);
		DispatchService.addTask(task_send);

		// task_id==CharacterUtil.ACCEPTTASKLIST
		Log.v(TAG, "接收任务");
		Task task_receive = new Task(CharacterUtil.ACCEPTTASKLIST, null);
		DispatchService.addTask(task_receive);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		DispatchService.addActivity(this);
	}

	@Override
	public void refreshUI(Object... objects) {
		String xml = (String) objects[0];
		//list = XMLuntil.extractTaskList(xml);
		XMLuntil.extractTaskList(xml);
		adapter = new TaskListAdapter(TaskListActivity.this, list);
		if (isLoadMore) {
			adapter.notifyDataSetChanged();
		}
		Log.v(TAG,adapter.toString());
		listView.setAdapter(adapter);
		Log.v(TAG, "adapter");
		btn_loadMore.setText("加载更多");
	}
}
