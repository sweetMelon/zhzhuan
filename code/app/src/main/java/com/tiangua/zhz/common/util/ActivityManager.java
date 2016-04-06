package com.tiangua.zhz.common.util;

import java.util.Stack;

import android.app.Activity;

public class ActivityManager {
	private Stack<Activity> activityStack = new Stack<Activity>();
	private static ActivityManager instance = null;

	private ActivityManager() {
	}

	public static ActivityManager getActivityManager() {
		if (instance == null) {
			synchronized (ActivityManager.class) {
				instance = new ActivityManager();
			}
		}
		return instance;
	}

	public int activitySize()
	{
		return activityStack.size();
	}
	
	public void popActivity() {
		Activity activity = activityStack.pop();
		if (activity != null) {
			activity.finish();
		}
	}

	public void pushActivity(Activity activity) {
		activityStack.push(activity);
	}

	public void finishAll() {
		int size = activityStack.size();
		for (int i = 0; i < size; i++) {
			Activity activity = activityStack.pop();
			activity.finish();
		}
	}
}
