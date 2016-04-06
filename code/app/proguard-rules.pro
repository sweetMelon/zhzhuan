# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\android\adt\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep public class com.tiangua.zhz.R$*{
public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-dontwarn cn.dow.**
-keep class cn.dow.** { *; }

-dontwarn com.zhenhaozz.**
-keep class **.R$* { *;  }
-keep class com.zhenhaozz.**{*;}

-dontwarn adh.doi.jkd.**
-keepclassmembers class adh.doi.jkd.libs.adsbase.js.base.JsInterface_Impl {
    *;
}

-keep public class cn.waps.** {*;}
-keep public interface cn.waps.** {*;}
-dontwarn cn.waps.**

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
