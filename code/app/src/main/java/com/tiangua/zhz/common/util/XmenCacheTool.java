package com.tiangua.zhz.common.util;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class XmenCacheTool {


    private static final String TAG = XmenCacheTool.class.getSimpleName();

    private static final String TERMINAL_FILE = ".xmen";

    private static boolean isSendFlag = false;


    public static String cleanTerminal(Context context) {
        String terminal = PreferenceUtil.getPreferenceUtil(context).getString("xmen", "");
        boolean hasSDCard = DevDevice.getInstance().hasSDcard();
        if (hasSDCard) {
            FileUtil.cleanFileFromSDCard(TERMINAL_FILE);
        } else {
            FileUtil.cleanFileFromCache(context, TERMINAL_FILE);
        }

        return terminal;
    }

    /**
     * 获取xmen有限从内存缓存中获取，如果没有则从本地缓存中拿
     */
    public static String getXmen(Context context) {

        String xmen = PreferenceUtil.getPreferenceUtil(context).getString("xmen", "");
        if (xmen != null && xmen.trim().length() > 0) {

            String encodeString = "";
            boolean hasSDCard = DevDevice.getInstance().hasSDcard();
            if (hasSDCard) {
                encodeString = FileUtil.readFileFromSDCard(TERMINAL_FILE);
            } else {
                encodeString = FileUtil.readFileFromCache(context, TERMINAL_FILE);
            }
        /*如果没有缓存则现有规则生成之后再做文件缓存*/
            if (TextUtils.isEmpty(encodeString)) {
                encodeString = xmen;
                if (hasSDCard) {
                    FileUtil.writeFileFromSDCard(encodeString, TERMINAL_FILE);
                } else {
                    FileUtil.writeFileFromCache(encodeString, context, TERMINAL_FILE);
                }
            }
        }
        else {
            String encodeString = "";
            boolean hasSDCard = DevDevice.getInstance().hasSDcard();
            if (hasSDCard) {
                encodeString = FileUtil.readFileFromSDCard(TERMINAL_FILE);
            } else {
                encodeString = FileUtil.readFileFromCache(context, TERMINAL_FILE);
            }
        /*如果没有缓存则现有规则生成之后再做文件缓存*/
            if (!TextUtils.isEmpty(encodeString)) {
                xmen = encodeString;
                PreferenceUtil.getPreferenceUtil(context).put("xmen", xmen);
            }
        }
    return xmen;
}



}
