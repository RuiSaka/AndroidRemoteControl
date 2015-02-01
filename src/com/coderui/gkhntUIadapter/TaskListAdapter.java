package com.coderui.gkhntUIadapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coderui.gkhntUI.R;

public class TaskListAdapter extends BaseAdapter {
	String TAG="TaskListAdapter";
	private Context context;
	private List<Map<String,String>> list;
	
	public TaskListAdapter(Context context,List<Map<String,String>> list){
		Log.v(TAG,"TaskListAdapter");
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.task_item, null);
			listItemView = new ListItemView();
			listItemView.num_task=(TextView) convertView.findViewById(R.id.tv_num_task);
			listItemView.date=(TextView) convertView.findViewById(R.id.tv_date);
			listItemView.project=(TextView) convertView.findViewById(R.id.tv_project);
			listItemView.staty=(TextView) convertView.findViewById(R.id.tv_staty_task);
			convertView.setTag(listItemView);
		}else{
			listItemView=(ListItemView) convertView.getTag();
		}
		String numberOfTask=list.get(position).get("任务单号");
		String date=list.get(position).get("操作日期");
		String projectName=list.get(position).get("工程名称");
		String statyOfTask=list.get(position).get("任务状态");
		listItemView.num_task.setText(numberOfTask);
		listItemView.date.setText(date);
		listItemView.project.setText(projectName);
		listItemView.staty.setText(statyOfTask);
		return convertView;
	}
	public final class ListItemView{
		TextView num_task;
		TextView date;
		TextView project;
		TextView staty;
	}
}
