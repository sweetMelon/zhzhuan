package com.tiangua.zhz.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.Display;

public class ScreenUtil {

	/**
	 * 获取屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * dip convert to px
	 * 
	 * @param context
	 * @param padding_in_dp
	 * @return
	 */
	public static int dip2Px(Context context, int padding_in_dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (padding_in_dp * scale + 0.5f);
	}

	/**
	 * px convert to dip
	 * 
	 * @param pxValue
	 * @param scale
	 * @return
	 */
	public static int px2dip(float pxValue, float scale) {
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * px convert to sp
	 * 
	 * @param pxValue
	 * @param fontScale
	 * @return
	 */
	public static int px2sp(float pxValue, float fontScale) {
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * sp convert to px
	 * 
	 * @param spValue
	 * @param fontScale
	 * @return
	 */
	public static int sp2px(float spValue, float fontScale) {
		return (int) (spValue * fontScale + 0.5f);
	}
}
