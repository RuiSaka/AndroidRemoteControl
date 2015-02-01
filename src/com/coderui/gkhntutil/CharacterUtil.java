package com.coderui.gkhntutil;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.coderui.gkhntUI.R;

public class CharacterUtil {
	public static final String[] FORMFIELD = { "任务单号", "合同号", "单位名称", "工程名称",
			"工程地址", "施工部位", "输送距离", "配比号", "计划数量", "完成数量", "累计车数", "操作员",
			"操作日期", "操作时间", "任务状态", "备注" };
	public static final Integer[] IMAGES = { R.drawable.delete,
			R.drawable.backup, R.drawable.contacts, R.drawable.message,
			R.drawable.shartdown, R.drawable.restart };
	public static final String[] FUNCTIONS={"删除数据库","备份数据库","监控用户","发送提醒","关闭计算机","重启计算机"};
	/**
	 * task_id
	 */
	public static final int LOG_IN = 0;// 客户端连接成功
	public static final int REQUESTTASTLIST = 1;// 向服务器请求任务单列表
	public static final int ACCEPTTASKLIST = 2;// 接收服务器的任务列表
	public static final int SENDQUERYREQUEST = 3;// 向客户端发送查询请求
	public static final int ACCEPTQUERYRESULT = 4;// 接收查询结果
	public static final int DELETEDATABASE=5;//备份数据库
	public static final int BACKUPDATABASE=6;//备份数据库
	public static final int CONTROLUSERS=7;//监控用户列表
	public static final int MESSAGE=8;//提醒消息
	public static final int SHARTDOWN=9;//关闭计算机
	public static final int RESTARTCOPUTER=10;//重启计算机
	public static final int ACCPETADVANCE=11;//接收高级操作回应

	public static final int PORT = 7000;// 端口号

	private static final String dateFrom = "yyyy-MM-dd";// 输出的日期格式

	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFrom);
		Date curentDate = new Date(System.currentTimeMillis());
		return sdf.format(curentDate);
	}
}
