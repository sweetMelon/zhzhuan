package com.tiangua.zhz.common.network;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 调用接口的时回调
 * @author x_liaolijun
 */
public abstract class IHttpReqTaskListener {
	/**
	 * 调用接口前执行的操作
	 */
	public abstract void onPreExecute();


	public abstract void onPostExeute(JSONObject json);
	
	public abstract void onPostExeute(JSONArray json);
	
	public abstract void onError(JSONObject json);
	
	public abstract void dismissPD();
}
