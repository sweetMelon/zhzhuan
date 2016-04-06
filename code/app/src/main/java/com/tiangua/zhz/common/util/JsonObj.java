package com.tiangua.zhz.common.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JsonObj {

    public static Object jsonToObject(JSONObject json, Class<?> clz) {
        Object object = null;
        try {
            // 返回所有成员变量
            Field[] fields = clz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    // get field interview author
                    int t_interview_author = f.getModifiers();
                    if (t_interview_author == Modifier.PRIVATE) {
                        f.setAccessible(true);
                        // get field name
                        String t_name = f.getName();
                        if(json.has(t_name)){
                            // instance an object
                            object = clz.newInstance();
                            // get field type
                            String t_type = f.getType().getSimpleName();
                            // get 属性的值
                            Object value = f.get(clz);
                            if (t_type != null && t_type.trim().length() > 0) {
                                if (t_type.equalsIgnoreCase("string")) {
                                    String v = json.getString(t_name);
                                    f.set(object, v);
                                } else if (t_type.equalsIgnoreCase("float") || t_type.equalsIgnoreCase("double")) {
                                    double float_v = json.getDouble(t_name);
                                    f.set(object, float_v);
                                } else if (t_type.equalsIgnoreCase("long")) {
                                    long long_v = json.getLong(t_name);
                                    f.set(object, long_v);
                                } else if (t_type.equalsIgnoreCase("int")
                                        || t_type.equalsIgnoreCase("integer")) {
                                    int int_v = json.getInt(t_name);
                                    f.set(object, int_v);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return object;
    }

    public static JSONObject getJsonObject(JSONObject json, String name) {
        JSONObject jsonObject = null;
        try {
            jsonObject = json.getJSONObject(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray getJsonArray(JSONObject json, String name) {
        JSONArray jsonObject = null;
        try {
            jsonObject = json.getJSONArray(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String getString(JSONObject json, String name) {
        String result = "";
        try {
            result = json.getString(name);
            if (result == null)
                result = "";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static long getLong(JSONObject json, String name) {
        long result = 0l;
        try {
            result = json.getLong(name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static int getInt(JSONObject json, String name) {
        int result = -1;
        try {
            result = json.getInt(name);
        } catch (Exception e) {
            result = -1;
            e.printStackTrace();
        }
        return result;
    }
}
