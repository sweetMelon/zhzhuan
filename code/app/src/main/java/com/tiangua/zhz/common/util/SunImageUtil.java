
package com.tiangua.zhz.common.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class SunImageUtil {

    private static final String LOG = "SunImageUtil";

    /**
     * 获取sd卡图片文件，并压缩
     *
     * @param srcPath
     * @return
     */
    public static Bitmap compressImageFromFile(String srcPath, int width, int height) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        Log.d(LOG, "图片原始大小:" + getBitmapsize(bitmap));
        Log.d(LOG, "图片原始宽度：" + w + "图片原始高度:" + h);
        // float hh = 800f;//
        // float ww = 480f;//
        // int be = 1;
        // if (w > h && w > ww) {
        // be = (int) (newOpts.outWidth / ww);
        // } else if (w < h && h > hh) {
        // be = (int) (newOpts.outHeight / hh);
        // }
        // if (be <= 0)
        // be = 1;
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = calculateInSampleSize(newOpts, width, height);// 设置采样率

        newOpts.inPreferredConfig = Config.ARGB_4444;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        Log.d(LOG, "图片压缩后宽度：" + bitmap.getWidth() + "图片压缩后高度:" + bitmap.getHeight());
        long unZipSize = getBitmapsize(bitmap);
        String countSize = changeBitmapSize(unZipSize);
        Log.d(LOG, "压缩后的图片大小：" + countSize);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * 获取图片大小
     *
     * @param bitmap
     * @return
     */
    @SuppressLint("NewApi")
    public static long getBitmapsize(Bitmap bitmap) {

        long bmSize = 0l;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            bmSize = bitmap.getByteCount();
        } else {
            // Pre HC-MR1
            bmSize = bitmap.getRowBytes() * bitmap.getHeight();
        }
        return bmSize;
    }

    /**
     * 计算压缩比例值
     *
     * @param options   解析图片的配置信息
     * @param reqWidth  所需图片压缩尺寸最小宽度
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return
     */
    // public static int calculateInSampleSize(BitmapFactory.Options options,
    // int reqWidth, int reqHeight) {
    // // 保存图片原宽高值
    // final int height = options.outHeight;
    // final int width = options.outWidth;
    // // 初始化压缩比例为1
    // int inSampleSize = 1;
    //
    // // 当图片宽高值任何一个大于所需压缩图片宽高值时,进入循环计算系统
    // if (height > reqHeight || width > reqWidth) {
    //
    // final int halfHeight = height / 2;
    // final int halfWidth = width / 2;
    //
    // // 压缩比例值每次循环两倍增加,
    // // 直到原图宽高值的一半除以压缩值后都~大于所需宽高值为止
    // while ((halfHeight / inSampleSize) >= reqHeight
    // && (halfWidth / inSampleSize) >= reqWidth) {
    // inSampleSize *= 2;
    // }
    // }
    //
    // return inSampleSize;
    // }
    // 计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 把bitmap转换成String
    public static String bitmapToString(String filePath) {

        Bitmap bm = compressImageFromFile(filePath, 480, 800);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        String unZipSize = changeBitmapSize(b.length);
        Log.d(LOG, "上传之前图片的大小:" + unZipSize);
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static String changeBitmapSize(long bitmapByte) {
        String bitmapSize = null;
        long kb = bitmapByte / 1024;
        if (kb / 1024 > 0) {
            bitmapSize = (kb / 1024) + "mb";
        } else {
            bitmapSize = kb + "kb";
        }
        return bitmapSize;
    }

    /**
     * 去掉协议字符串后，判断域名中是否有//转义字符并去除
     *
     * @param imgUrl
     * @return
     */
    public static String getImageUrl(String imgUrl) {
        StringBuilder protocal = new StringBuilder(imgUrl.substring(0, imgUrl.indexOf("://") + 3));
        String domain = imgUrl.substring(imgUrl.indexOf("://") + 3);
        if (domain.contains("//"))
            domain = domain.replaceAll("//", "/");
        return protocal.append(domain.toString()).toString();
    }
}
