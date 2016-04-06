package com.tiangua.zhz.common.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author 小包
 * @time 2012-5-23 上午11:14:30
 */
public class LogCat
{
    public static final String TAG = "LogCat";
    // 是否将打印的日志写入sd卡
    public static Boolean MYLOG_WRITE_TO_FILE = true;
    private static Boolean MYLOG_WRITE_TO_FILE_W = true;
    private static Boolean MYLOG_WRITE_TO_FILE_E = true;
    private static Boolean MYLOG_WRITE_TO_FILE_D = true;
    private static Boolean MYLOG_WRITE_TO_FILE_I = true;
    private static Boolean MYLOG_WRITE_TO_FILE_V = true;

    public static String MYLOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory()
            + "/system/androi/cache"; //
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 7; //
    private static String MYLOGFILEName = "Log.txt"; //
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat(
                                                                    "yyyy-MM-dd HH:mm:ss"); //
    private static SimpleDateFormat logfile = new SimpleDateFormat(
                                                                   "yyyy-MM-dd_HH"); //
    public static final String tag = "mobile7";

    public static void w(String className, String text)
    {
        log(tag, text, 'w');
    }

    public static void e(String className, String text)
    {
        log(tag, text, 'e');
    }

    public static void e(String className, Throwable e)
    {
        log(tag, e, 'e');
    }

    public static void d(String className, String text)
    {
        log(className, text, 'd');
    }

    public static void i(String className, String text)
    {
        log(tag, text, 'i');
    }

    public static void v(String className, String text)
    {
        log(tag, text, 'v');
    }

    private static void log(String className, Object msg, char level)
    {
        if (msg == null)
            msg = "";

        if ('w' == level)
        {
            Log.w(className, className + "\n - > " + msg);
        }
        else if ('d' == level)
        {
            Log.d(tag, className + "\n - > " + msg);
        }
        else if ('i' == level)
        {
            Log.i(tag, className + "\n - > " + msg);
        }
        else if ('v' == level)
        {
            Log.v(tag, className + "\n - > " + msg);
        }
        else if ('e' == level)
        { //
            if (msg instanceof Throwable)
            {
                Log.e(tag,
                        className + "\n - > " + ((Throwable) msg).getMessage(),
                        (Throwable) msg);
            }
            else
            {
                Log.e(tag, className + "\n - > " + msg);
            }
        }

        if (MYLOG_WRITE_TO_FILE)
        {
            if ('e' == level)
            {
                writeLogtoFile(String.valueOf(level), tag,
                        Log.getStackTraceString((Throwable) msg));
            }
        }
    }

    private static void writeLogtoFile(String mylogtype, String tag, String text)
    {//
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype
                + "    " + tag + "    " + text;
        File rootFile = new File(MYLOG_PATH_SDCARD_DIR);
        File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel
                + MYLOGFILEName);
        try
        {
            boolean rootFileExists = (rootFile.exists() && rootFile.isDirectory()) == true ? true
                    : rootFile.mkdirs();
            if (rootFileExists)
            {

                boolean fileExists = (file.exists() && file.isFile()) == true ? true
                        : (createNewFile(file));
            }
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        }
        catch (Exception e)
        {
            e(TAG, e);
        }

    }

    public static boolean createNewFile(File file) throws IOException
    {
        tyrDeleteFile();
        return file.createNewFile();
    }

    /**
     * 删除多少天以前的LOG
     */
    public static void tyrDeleteFile()
    {
        String timeString = getDateBefore();
        // 去除 - 和 : 符号
        timeString = timeString.replace("-", "");
        // 得到过期时间点
        long time = Long.valueOf(timeString.substring(0, 8));

        File[] files = new File(MYLOG_PATH_SDCARD_DIR).listFiles();
        if (files != null)
        {
            for (int i = 0; i < files.length; i++)
            {
                String name = files[i].getName();
                if (name.length() != 20)
                {// 生成错误文件名(文件名里面意外夹杂00)的文件删除，并删除就版本log文件(长度为17或19)
                    files[i].delete();
                }
                else
                {// 将过期的log文件删除
                    name = name.replace("-", "");
                    for (int j = 0; j < 8; j++)
                    {
                        if (name.charAt(j) < '0' || name.charAt(j) > '9')
                        {
                            files[i].delete();
                            return;
                        }
                    }
                    long time2 = Long.valueOf(name.substring(0, 8));
                    if (time >= time2)
                    {
                        files[i].delete();
                    }
                }
            }
        }
    }

    /**
     * 获取 SDCARD_LOG_FILE_SAVE_DAYS 天以前的时间，
     * 
     * @return yyyyMMdd字符串
     */
    private static String getDateBefore()
    {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE)
                - SDCARD_LOG_FILE_SAVE_DAYS);
        return logfile.format(now.getTime());
    }

    public static void saveException(Exception e)
    {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        log("saveException", sw.toString(), 'e');
    }

    public static void saveException(String TAG, Exception e)
    {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        log(TAG + ": saveException", sw.toString(), 'e');
    }

    public static void saveThrowable(Throwable ex)
    {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        log("saveThrowable", sw.toString(), 'e');
    }

    public static void fd(String tag, String msg)
    {
        d(tag, msg);
    }

}
