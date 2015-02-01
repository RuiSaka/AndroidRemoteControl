package com.coderui.gkhntDispatch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.coderui.gkhntLogic.LogicThread;
import com.coderui.gkhntUI.ModelActivity;
import com.coderui.gkhntutil.CharacterUtil;
import com.coderui.gkhntutil.Task;

@SuppressLint("HandlerLeak")
public class DispatchService extends Service {
	String TAG="DispatchService";
	public static Queue<Task> tasks = new LinkedList<Task>();// 任务列表
	public static List<Activity> activities = new ArrayList<Activity>();
	public boolean isRun;// 是否运行线程

	private static Socket socket;
	private static InputStream is;
	private static OutputStream os;
	private ModelActivity model;
	
	//发送消息给服务器
	private void sendInfo(String xml){
		try {
			DispatchService.os.write(xml.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//接收来自服务器的数据
	private void acceptInfo(Message msg){
		try {
			byte[] buf=new byte[20480];
			int length=is.read(buf);
			String receive=new String(buf,0,length,"UTF-8");
//			String receive = "";
//			int messageSize=0;
//			byte[] buffer = new byte[1024];
//			while ((messageSize = is.read(buffer)) != -1) {
//				Log.v(TAG,String.valueOf(is.read(buffer)));
//				String xml = new String(buffer, 0, messageSize, "UTF-8");
//				receive = receive + xml;
//				Log.v(TAG,"xmlxml");
//			}
			Log.v(TAG,"接收到消息");
			msg.obj = receive;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 根据消息不同处理不同的逻辑业务
			switch (msg.what) {
			case CharacterUtil.LOG_IN:
				model = (ModelActivity) getActivityByName("GhkntActivity");
				model.refreshUI(msg.obj);
				break;
			case CharacterUtil.ACCEPTTASKLIST:
				model = (ModelActivity) getActivityByName("TaskListActivity");
				model.refreshUI(msg.obj);
				break;
			case CharacterUtil.ACCEPTQUERYRESULT:
				model = (ModelActivity) getActivityByName("QueryDetialActivity");
				Log.v(TAG,"刷新UI");
				model.refreshUI(msg.obj);
			case CharacterUtil.ACCPETADVANCE:
				model = (ModelActivity) getActivityByName("AdvancelActivity");
				Log.v(TAG,"刷新UI");
				model.refreshUI(msg.obj);
			default:
				break;
			}
		}
	};

	// 将activity加入到列表中
	public static void addActivity(Activity a) {
		activities.add(a);
	}

	// 根据activity的名字获取activity的对象
	public Activity getActivityByName(String name) {
		if (activities != null) {
			for (Activity activity : activities) {
				if (activity != null) {
					if (activity.getClass().getName().indexOf(name) > 0) {
						activities.remove(0);//移除activity，否则可能会是已经退出的activity
						return activity;
					}
				}
			}
		}
		return null;
	}

	// 添加任务到任务列队中
	public static void addTask(Task t) {
		tasks.add(t);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		isRun = true;
		Thread thread = new LogicThread(this);
		thread.start();
	}

	// 处理任务,发回更新消息给主线程
	public void doTask(Task t) {
		Message msg = handler.obtainMessage();
		msg.what = t.getTask_id();
		Map<String, Object> taskParams = new HashMap<String, Object>();
		switch (t.getTask_id()) {
		/**
		 * 登陆到服务器
		 */
		case CharacterUtil.LOG_IN:
			taskParams = t.getTaskParams();
			String ipAddress = (String) taskParams.get("ipAdress");
			int port = (Integer) taskParams.get("port");
			try {
				DispatchService.socket = new Socket(ipAddress, port);
				DispatchService.is = socket.getInputStream();
				DispatchService.os = socket.getOutputStream();
				DispatchService.os.write("Client has connected".getBytes("UTF-8"));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		/**
		 * 请求任务单列表
		 */
		case CharacterUtil.REQUESTTASTLIST:
			taskParams = t.getTaskParams();
			String send_xml = (String) taskParams.get("xml");
			sendInfo(send_xml);
			break;
		/**
		 * 接收服务器的消息
		 */
		case CharacterUtil.ACCEPTTASKLIST:
			acceptInfo(msg);
			break;
			/**
			 * 发送查询数据请求
			 */
		case CharacterUtil.SENDQUERYREQUEST:
			taskParams = t.getTaskParams();
			String query_xml = (String) taskParams.get("xml");
			sendInfo(query_xml);
			break;
			/**
			 * 接收服务器的查询结果
			 */
		case CharacterUtil.ACCEPTQUERYRESULT:
			acceptInfo(msg);
			break;
		case CharacterUtil.ACCPETADVANCE:
			acceptInfo(msg);
			break;
		default:
			break;
		}
		handler.sendMessage(msg);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
