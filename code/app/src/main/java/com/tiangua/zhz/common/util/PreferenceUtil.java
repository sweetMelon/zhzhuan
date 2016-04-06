package com.tiangua.zhz.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PreferenceUtil {
    public static final String DEFAULT_NAME = "tiangua_preference";

    private static SharedPreferences mSharedPreferences;
    private static Editor mEditor;
    private static PreferenceUtil instance = null;

    private PreferenceUtil() {
    }

    public synchronized static PreferenceUtil getPreferenceUtil(Context context) {
        if (instance == null) {
            synchronized (PreferenceUtil.class) {
                if (instance == null) {
                    instance = new PreferenceUtil();
                }
            }
        }
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(DEFAULT_NAME,
                    Context.MODE_PRIVATE);
        }
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }
        return instance;
    }

    public void put(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void put(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void put(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void put(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void putTransact(String key, boolean value) {
        mEditor.putBoolean(key, value);
    }

    public void putTransact(String key, long value) {
        mEditor.putLong(key, value);
    }

    public void putTransact(String key, int value) {
        mEditor.putInt(key, value);
    }

    public void putTransact(String key, String value) {
        mEditor.putString(key, value);
    }

    public void commitTransact(){
        mEditor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    // public void put(Param_Schema[] scheme){
    // for(int i = 0;i<scheme.length;i++){
    // mEditor.putString(scheme[i].ParamName, scheme[i].ParamValue);
    // }
    // mEditor.commit();
    // }

    public void putMap(Map<String, String> params) {
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            mEditor.putString(entry.getKey(), entry.getValue());
        }
        mEditor.commit();
    }

    public String getString(String key, String defaultValue) {
        String value = "";
        value = mSharedPreferences.getString(key, defaultValue);
        return value;

    }

    public long getLong(String key, long defaultValue) {
        long value = 0l;
        value = mSharedPreferences.getLong(key, defaultValue);
        return value;
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }

    public void remove(String key, boolean isEnc) {

        mEditor.remove(key);
        mEditor.commit();
    }
}
