package com.coderui.gkhntutil;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import com.coderui.gkhntUI.TaskListActivity;

import android.util.Log;
import android.util.Xml;

public class XMLuntil{
	
	private static String TAG="XMLutil";
	/**
	 * 构造申请任务单请求
	 * <request>
	 * 		<type>1</type> 1表示请求任务单列表
	 * 		<params></params>
	 * </request>
	 * 
	 */
	public static String structRequestXml(int type,Object params){
		String xml="";
		StringWriter stringWriter=new StringWriter();
		XmlSerializer xmlSerializer=Xml.newSerializer();
		try{
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("UTF-8", true);
			xmlSerializer.startTag(xml, "request");
			xmlSerializer.startTag(xml, "type");
			xmlSerializer.text(String.valueOf(type));
			xmlSerializer.endTag(xml, "type");
			xmlSerializer.startTag(xml, "params");
			xmlSerializer.text(String.valueOf(params));
			xmlSerializer.endTag(xml, "params");
			xmlSerializer.endTag(xml, "request");
			xmlSerializer.endDocument();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return stringWriter.toString();
	}
	
	/**
	 * 构造查询数据的xml
	 * <query>
	 * 		<type>3</type>
	 * 		<querytype>queryType</querytype>
	 * 		<task>queryTaskNumber</task>
	 * 		<start>start_date</start>
	 * 		<end>end_date</end>
	 * </query>
	 * @return
	 */
	public static String constructQueryXml(int queryType,String queryTaskNumber,String start_date,String end_date){
		String xml="";
		StringWriter stringWriter=new StringWriter();
		XmlSerializer xmlSerializer=Xml.newSerializer();
		try{
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("UTF-8", true);
			xmlSerializer.startTag(xml, "query");
			xmlSerializer.startTag(xml, "type");
			xmlSerializer.text(String.valueOf(3));
			xmlSerializer.endTag(xml, "type");
			xmlSerializer.startTag(xml, "querytype");
			xmlSerializer.text(String.valueOf(queryType));
			xmlSerializer.endTag(xml, "querytype");
			xmlSerializer.startTag(xml, "task");
			xmlSerializer.text(queryTaskNumber);
			xmlSerializer.endTag(xml, "task");
			xmlSerializer.startTag(xml, "start");
			xmlSerializer.text(start_date);
			xmlSerializer.endTag(xml, "start");
			xmlSerializer.startTag(xml, "end");
			xmlSerializer.text(end_date);
			xmlSerializer.endTag(xml, "end");
			xmlSerializer.endTag(xml, "query");
			xmlSerializer.endDocument();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return stringWriter.toString();
	}
	
	//用pull解析任务单数据
	public static List<Map<String,String>> extractTaskList(String xml){
		List<Map<String,String>> list=null;
		Map<String,String> map=null;
		ByteArrayInputStream is=null;
		try{
			if(xml!=null&& !xml.trim().equals("")){
				is=new ByteArrayInputStream(xml.getBytes("UTF-8"));
			}
			XmlPullParser parser=Xml.newPullParser();
			parser.setInput(is,"UTF-8");//设置输入流
			/*
			 * pull读到xml后 返回数字
			 * 读取到xml的声明返回数字0 START_DOCUMENT;
			 * 读取到xml的结束返回数字1 END_DOCUMENT ;
			 * 读取到xml的开始标签返回数字2 START_TAG
			 * 读取到xml的结束标签返回数字3 END_TAG
			 * 读取到xml的文本返回数字4 TEXT
			 */
			int type=parser.getEventType();
			while(type!=XmlPullParser.END_DOCUMENT){
				switch(type){
				case XmlPullParser.START_DOCUMENT:
					list=TaskListActivity.list;
					//list=new ArrayList<Map<String,String>>();
					break;
				case XmlPullParser.START_TAG:
					if("task".equals(parser.getName())){
						map=new HashMap<String,String>();
					}else if("messages".equals(parser.getName())){
						String receivetype=parser.getAttributeValue(0);
						Log.v(TAG, "获取messages节点"+receivetype);
					}else{
						String element=parser.getName();
						type=parser.next();
						String elementText=parser.getText();
						map.put(element,elementText);
					}
					break;
				case XmlPullParser.END_TAG:
					if("task".equals(parser.getName())){
						list.add(map);
						map=null;
					}
					break;
				}
				type=parser.next();
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析出任务单生产情况回馈xml
	 * <message>
	 * 		<summy></summy>
	 * </message>
	 * @param xml
	 * @return
	 */
	public static String extractTaskListProduct(String xml){
		ByteArrayInputStream is=null;
		String summy="0.0";
		try{
			if(xml!=null&& !xml.trim().equals("")){
				is=new ByteArrayInputStream(xml.getBytes("UTF-8"));
			}
			XmlPullParser parser=Xml.newPullParser();
			parser.setInput(is,"UTF-8");//设置输入流
			int type=parser.getEventType();
			while(type!=XmlPullParser.END_DOCUMENT){
				switch(type){
				case XmlPullParser.START_TAG:
					if("summy".equals(parser.getName())){
						summy=parser.getText();
					}
					break;
				}
				type=parser.next();
			}
			return summy;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}
