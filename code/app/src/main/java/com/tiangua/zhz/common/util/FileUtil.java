package com.tiangua.zhz.common.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by adamFeng on 2016/3/26.
 */
public class FileUtil {

    /**
     * Clean File From T Card.
     */
    public static void cleanFileFromSDCard(String openidFile) {
        try {
            String sDStateString = android.os.Environment.getExternalStorageState();
            if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
                File SDFile = android.os.Environment.getExternalStorageDirectory();
                File myFile = new File(SDFile.getAbsolutePath() + openidFile);
                if (myFile.exists()) {
                    myFile.delete();
                }
            }
        } catch (Exception e) {
        }

    }

    /**
     * 清除本地缓存
     *
     * @param context
     */
    public static void cleanFileFromCache(Context context, String openidFile) {
        try {
            File file = context.getDir(openidFile, Context.MODE_PRIVATE);
            if (file != null && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
        }

    }

    /**
     * Write File from SDCard.
     *
     * @param content
     */
    public static void writeFileFromSDCard(String content, String openidFile) {
        String sDStateString = android.os.Environment.getExternalStorageState();
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            try {
                File SDFile = android.os.Environment.getExternalStorageDirectory();
//				File myDir = new File(SDFile.getAbsolutePath() + File.separator + Constants.PLATFORMID_FOLDER);
                File myFile = new File(SDFile.getAbsolutePath() + File.separator + openidFile);
                if (!myFile.getParentFile().exists()) {
                    myFile.getParentFile().mkdir();
                    myFile.createNewFile();
                } else if (!myFile.exists()) {
                    myFile.createNewFile();
                }
                FileOutputStream outputStream = new FileOutputStream(myFile);
                outputStream.write(content.getBytes());
                outputStream.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Write File From Cache.
     *
     * @param content
     * @param context
     */
    public static void writeFileFromCache(String content, Context context, String openidFile) {
        try {
            FileOutputStream fos = context.openFileOutput(openidFile, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    /**
     * Read File From Cache.
     *
     * @param context
     * @return
     */
    public static String readFileFromCache(Context context, String openidFile) {
        try {
            FileInputStream fis = context.openFileInput(openidFile);
            if (fis == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[fis.available() == 0 ? 1024 : fis.available()];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.close();
            fis.close();
            return baos.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Read File From TCard.
     *
     * @param openidFile
     * @return
     */
    public static String readFileFromSDCard(String openidFile) {
        String sDStateString = android.os.Environment.getExternalStorageState();
        String line = "";
        String Result = "";
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            try {
                File SDFile = android.os.Environment.getExternalStorageDirectory();
                File myFile = new File(SDFile.getAbsolutePath() + File.separator + openidFile);
                if (myFile.exists()) {
                    FileInputStream inputStream = new FileInputStream(myFile);
                    InputStreamReader inputReader = new InputStreamReader(inputStream);
                    BufferedReader bufReader = new BufferedReader(inputReader);

                    while ((line = bufReader.readLine()) != null) {
                        if (line.trim().length() > 0) {
                            Result += line;
                        }
                    }
                    inputStream.close();
                    return Result;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }
}
