package com.coderui.gkhntLogic;

import android.util.Log;

import com.coderui.gkhntDispatch.DispatchService;
import com.coderui.gkhntutil.Task;

public class LogicThread extends Thread {
	
	private DispatchService service;
	
	public LogicThread(DispatchService service){
		this.service=service;
	}
	
	@Override
	public void run() {
		while(service.isRun){
			Task task=null;
			if(DispatchService.tasks!=null){
				task=DispatchService.tasks.poll();
				if(task!=null){
					Log.v("»ŒŒÒID",String.valueOf(task.getTask_id()));
					service.doTask(task);
				}
			}
			try{
				sleep(1000);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
