package com.coderui.gkhntUIadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coderui.gkhntUI.R;
import com.coderui.gkhntutil.CharacterUtil;

public class AdvanceAdapter extends BaseAdapter {
	private Context context;

	public AdvanceAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return CharacterUtil.IMAGES.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AdvanceFunction advance;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.advance_tool_item, null);
			advance=new AdvanceFunction();
			advance.imageview=(ImageView) convertView.findViewById(R.id.imageView);
			advance.function=(TextView) convertView.findViewById(R.id.function);
			convertView.setTag(advance);
		}else{
			advance=(AdvanceFunction) convertView.getTag();
		}
		advance.imageview.setImageResource(CharacterUtil.IMAGES[position]);
		advance.function.setText(CharacterUtil.FUNCTIONS[position]);
		return convertView;
	}
	class AdvanceFunction{
		ImageView imageview;
		TextView function;
	}
}
