package com.tiangua.zhz.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取设备信息和本地软件安装列表并发送给服务器端
 */
public class DevDevice {

    /** 没有网络 */
    public static final int NETWORKTYPE_INVALID = 0;
    /** wap网络 */
    public static final int NETWORKTYPE_WAP = 1;
    /** 2G网络 */
    public static final int NETWORKTYPE_2G = 2;
    /** 3G和3G以上网络，或统称为快速网络 */
    public static final int NETWORKTYPE_3G = 3;
    /** wifi网络 */
    public static final int NETWORKTYPE_WIFI = 4;

    private boolean isEmulator;
    private List<PackageInfo> appPackage = null;
    private final String INIT_CONFIGURATION_SHARED = "init_shared";
    private final String APP_KEY = "uid";
    private final String APP_CHANNEL = "channel";
    private static DevDevice instance = null;

    private DevDevice() {
    }

    public static DevDevice getInstance() {
        if (instance == null) {
            synchronized (DevDevice.class) {
                if (instance == null) {
                    instance = new DevDevice();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        getDeviceInfo(context);
    }

    public String getChannelId(Context context) {
        String packageID = null;
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(
                        context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    packageID = String.valueOf(applicationInfo.metaData.get("UMENG_CHANNEL"));
                }
            } catch (Exception e) {
                LogCat.e("DevDevice", e);
                packageID = null;
            }
        }
        return packageID;
    }

    /**
     * 生成手机参数json
     * @return
     * @author swm
     * @time 2014-2-19 上午10:42:28
     */
    public String getDeviceInfo(Context context) {
        String deviceInfo = null;
        JSONObject json = new JSONObject();
        try {
            json.put("osname", getOSVersionName());
            json.put("oscode", String.valueOf(getOSVersionCode()));
            json.put("mobiletype", getModel());
            json.put("romname", getROMName());
            json.put("imei", getIMEI(context));
            json.put("imsi", getIMSI(context));
            json.put("networktype", getNetWorkType(context));
            json.put("phonetype", getPhoneType(context));
            json.put("country", getCountry());
            json.put("language", getLanguage());
            json.put("appvname", getSoftVersionName(context));
            json.put("appvcode", getSoftVersionCode(context));
            json.put("mac", getWlanMacAddress(context));
            json.put("ip", getLocalIpAddress());
            json.put("androidid", getAndroidID(context));
            deviceInfo = json.toString();
        } catch (Exception e) {
            LogCat.e("DevDevice", e);
            deviceInfo = null;
        }
        return deviceInfo;
    }

    public boolean hasSDcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public int getOSVersionCode() {
        int osVersionCode = Build.VERSION.SDK_INT;
        return osVersionCode;
    }

    // 注意:模拟器的IMEI为0
    public String getIMEI(Context context) {
        String imei = null;
        try {
            if (context != null) {
                TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = telephony.getDeviceId();
                String deviceSoftVersion = telephony.getDeviceSoftwareVersion();
                String networkOperatorName = telephony.getNetworkOperatorName();
                if (deviceId == null) {
                    return "Null";
                } else if (deviceId.length() == 0) {
                    return "Null";
                }
                if (deviceId.toLowerCase().equals(
                        "000000000000000")) {
                    return "Null";
                }
                imei = deviceId;
            }
        } catch (Exception e) {
            LogCat.e("DevDevice", e);
            imei = "Null";
        }
        return imei;
    }

    /**
     * 获取mac地址
     */
    public String getWlanMacAddress(Context context) {
        String mac = null;
        try {
            if (context != null) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                mac = wifiInfo.getMacAddress();
            }
        } catch (Exception e) {
            LogCat.e("DevDevice", e);
            mac = null;
        }
        return mac;
    }

    /**
     * 获取ip地址
     * @return
     */
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LogCat.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }


    public String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getOSVersionName() {
        return Build.VERSION.RELEASE;
    }

    public String getSoftVersionName(Context context) {
        String versionName = null;
        try {
            if (context != null) {
                PackageInfo packageinfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), 0);
                versionName = packageinfo.versionName;
            }
        } catch (Exception e) {
            LogCat.e("DevDevice", e);
            versionName = null;
        }
        return versionName;
    }

    public String getSoftVersionCode(Context context) {
        String versionCode = null;
        try {
            if (context != null) {
                PackageInfo packageinfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), 0);
                versionCode = String.valueOf(packageinfo.versionCode);
            }
        } catch (Exception e) {
            LogCat.e("DevDevice", e);
            versionCode = null;
        }
        return versionCode;
    }

    // 注意:模拟器会返回"SDK"标志
    public String getModel() {
        String model = Build.MODEL;
        model = replaceBlank(model);
        return model;
    }

    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    private static String getROMName() {
        return Build.ID;
    }

    // 获取SIM卡的IMSI码
    private String getIMSI(Context context) {
        String imsi = null;
        try {
            if (context != null) {
                TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imsi = telephony.getSubscriberId();
            }
        } catch (Exception e) {
            LogCat.e("DevDevice", e);
            imsi = null;
        }
        return imsi;
    }
    /**
     * 获取手机类型 移动 电信 联通
     * @return
     */
    private String getPhoneType(Context context){
        String phoneType = null;
        try {
            if (context != null) {
                TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                switch (telephony.getPhoneType()){
                    case TelephonyManager.PHONE_TYPE_CDMA:
                        phoneType = "cdma";
                        break;
                    case TelephonyManager.PHONE_TYPE_GSM:
                        phoneType = "gsm";
                        break;
                    case TelephonyManager.PHONE_TYPE_NONE:
                        phoneType = "none";
                        break;
                    case TelephonyManager.PHONE_TYPE_SIP:
                        phoneType = "sip";
                        break;
                }
            }
        } catch (Exception e) {
            LogCat.e("DevDevice", e);
            phoneType = null;
        }
        return phoneType;
    }

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context 上下文
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G},          *{@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}* <p>{@link #NETWORKTYPE_WIFI}
     */
    public String getNetWorkType(Context context) {
        String networkType = "";
        int mNetWorkType = 0;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G)
                        : NETWORKTYPE_WAP;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        switch (mNetWorkType){
            case NETWORKTYPE_WIFI:
                networkType = "wifi";
                break;

            case NETWORKTYPE_2G:
                networkType = "2g";
                break;

            case NETWORKTYPE_3G:
                networkType = "3g";
                break;

            case NETWORKTYPE_WAP:
                networkType = "wap";
                break;

            case NETWORKTYPE_INVALID:
                networkType = "invalid";
                break;
        }
        return networkType;
    }
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
    /**
     * Role:Telecom service providers获取手机服务商信息 <BR>
     * 需要加入权限<uses-permission
     * android:name="android.permission.READ_PHONE_STATE"/> <BR>
     * Date:2012-3-12 <BR>
     *
     * @author CODYY)peijiangping 50
     */

    // public String getProvidersName()
    // {
    // return android.os.Build.MODEL;
    // }
    public String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String ProvidersName = telephonyManager.getNetworkOperatorName();
        String result = null;
        try {
            result = URLEncoder.encode(ProvidersName, "UTF-8");
        } catch (Exception e) {
            LogCat.e("DevDevice", e);
            result = null;
        }
        return result;
    }

    private boolean getWifiState(Context context) {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
            if (wifiManager.isWifiEnabled() && ipAddress != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            Log.e("getWifiState", "PleaseInitializeGEDeviceClass");
            return false;
        }
    }

    private static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    private static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * get all app pkg in phone
     * @param context
     * @return
     */
    public String getAllInstalledApp(Context context) {
        StringBuilder sb = new StringBuilder();
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(
                0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                sb.append(packageInfo.packageName + ",");
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

}
