package com.coderui.gkhntutil;

import java.util.Map;

public class Task {
	private int task_id;//任务id
	private Map<String,Object> taskParams;//任务参数
	
	public Task(int task_id,Map<String,Object> taskParams){
		this.task_id=task_id;
		this.taskParams=taskParams;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public Map<String, Object> getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(Map<String, Object> taskParams) {
		this.taskParams = taskParams;
	}
}
