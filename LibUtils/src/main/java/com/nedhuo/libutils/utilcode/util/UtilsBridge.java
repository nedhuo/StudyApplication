package com.nedhuo.libutils.utilcode.util;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import com.nedhuo.libutils.utilcode.util.ActivityUtils;
import com.nedhuo.libutils.utilcode.util.AdaptScreenUtils;
import com.nedhuo.libutils.utilcode.util.AppUtils;
import com.nedhuo.libutils.utilcode.util.BarUtils;
import com.nedhuo.libutils.utilcode.util.ConvertUtils;
import com.nedhuo.libutils.utilcode.util.DebouncingUtils;
import com.nedhuo.libutils.utilcode.util.EncodeUtils;
import com.nedhuo.libutils.utilcode.util.EncryptUtils;
import com.nedhuo.libutils.utilcode.util.FileIOUtils;
import com.nedhuo.libutils.utilcode.util.FileUtils;
import com.nedhuo.libutils.utilcode.util.GsonUtils;
import com.nedhuo.libutils.utilcode.util.ImageUtils;
import com.nedhuo.libutils.utilcode.util.IntentUtils;
import com.nedhuo.libutils.utilcode.util.JsonUtils;
import com.nedhuo.libutils.utilcode.util.KeyboardUtils;
import com.nedhuo.libutils.utilcode.util.NotificationUtils;
import com.nedhuo.libutils.utilcode.util.PermissionUtils;
import com.nedhuo.libutils.utilcode.util.ProcessUtils;
import com.nedhuo.libutils.utilcode.util.RomUtils;
import com.nedhuo.libutils.utilcode.util.SDCardUtils;
import com.nedhuo.libutils.utilcode.util.SPUtils;
import com.nedhuo.libutils.utilcode.util.ScreenUtils;
import com.nedhuo.libutils.utilcode.util.ServiceUtils;
import com.nedhuo.libutils.utilcode.util.ShellUtils;
import com.nedhuo.libutils.utilcode.util.SizeUtils;
import com.nedhuo.libutils.utilcode.util.StringUtils;
import com.nedhuo.libutils.utilcode.util.ThreadUtils;
import com.nedhuo.libutils.utilcode.util.ThrowableUtils;
import com.nedhuo.libutils.utilcode.util.TimeUtils;
import com.nedhuo.libutils.utilcode.util.ToastUtils;
import com.nedhuo.libutils.utilcode.util.UriUtils;
import com.nedhuo.libutils.utilcode.util.Utils;
import com.nedhuo.libutils.utilcode.util.UtilsActivityLifecycleImpl;
import com.nedhuo.libutils.utilcode.util.ViewUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;

import static android.Manifest.permission.CALL_PHONE;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/03/19
 *     desc  :
 * </pre>
 */
class UtilsBridge {

    static void init(Application app) {
        UtilsActivityLifecycleImpl.INSTANCE.init(app);
    }

    static void unInit(Application app) {
        UtilsActivityLifecycleImpl.INSTANCE.unInit(app);
    }

    static void preLoad() {
        preLoad(AdaptScreenUtils.getPreLoadRunnable());
    }

    ///////////////////////////////////////////////////////////////////////////
    // UtilsActivityLifecycleImpl
    ///////////////////////////////////////////////////////////////////////////
    static Activity getTopActivity() {
        return UtilsActivityLifecycleImpl.INSTANCE.getTopActivity();
    }

    static void addOnAppStatusChangedListener(final com.nedhuo.libutils.utilcode.util.Utils.OnAppStatusChangedListener listener) {
        UtilsActivityLifecycleImpl.INSTANCE.addOnAppStatusChangedListener(listener);
    }

    static void removeOnAppStatusChangedListener(final com.nedhuo.libutils.utilcode.util.Utils.OnAppStatusChangedListener listener) {
        UtilsActivityLifecycleImpl.INSTANCE.removeOnAppStatusChangedListener(listener);
    }

    static void addActivityLifecycleCallbacks(final com.nedhuo.libutils.utilcode.util.Utils.ActivityLifecycleCallbacks callbacks) {
        UtilsActivityLifecycleImpl.INSTANCE.addActivityLifecycleCallbacks(callbacks);
    }

    static void removeActivityLifecycleCallbacks(final com.nedhuo.libutils.utilcode.util.Utils.ActivityLifecycleCallbacks callbacks) {
        UtilsActivityLifecycleImpl.INSTANCE.removeActivityLifecycleCallbacks(callbacks);
    }

    static void addActivityLifecycleCallbacks(final Activity activity,
                                              final com.nedhuo.libutils.utilcode.util.Utils.ActivityLifecycleCallbacks callbacks) {
        UtilsActivityLifecycleImpl.INSTANCE.addActivityLifecycleCallbacks(activity, callbacks);
    }

    static void removeActivityLifecycleCallbacks(final Activity activity) {
        UtilsActivityLifecycleImpl.INSTANCE.removeActivityLifecycleCallbacks(activity);
    }

    static void removeActivityLifecycleCallbacks(final Activity activity,
                                                 final com.nedhuo.libutils.utilcode.util.Utils.ActivityLifecycleCallbacks callbacks) {
        UtilsActivityLifecycleImpl.INSTANCE.removeActivityLifecycleCallbacks(activity, callbacks);
    }

    static List<Activity> getActivityList() {
        return UtilsActivityLifecycleImpl.INSTANCE.getActivityList();
    }

    static Application getApplicationByReflect() {
        return UtilsActivityLifecycleImpl.INSTANCE.getApplicationByReflect();
    }

    static boolean isAppForeground() {
        return UtilsActivityLifecycleImpl.INSTANCE.isAppForeground();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ActivityUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isActivityAlive(final Activity activity) {
        return ActivityUtils.isActivityAlive(activity);
    }

    static String getLauncherActivity(final String pkg) {
        return ActivityUtils.getLauncherActivity(pkg);
    }

    static Activity getActivityByContext(Context context) {
        return ActivityUtils.getActivityByContext(context);
    }

    static void startHomeActivity() {
        ActivityUtils.startHomeActivity();
    }

    static void finishAllActivities() {
        ActivityUtils.finishAllActivities();
    }

    ///////////////////////////////////////////////////////////////////////////
    // AppUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isAppRunning(@NonNull final String pkgName) {
        return com.nedhuo.libutils.utilcode.util.AppUtils.isAppRunning(pkgName);
    }

    static boolean isAppInstalled(final String pkgName) {
        return com.nedhuo.libutils.utilcode.util.AppUtils.isAppInstalled(pkgName);
    }

    static boolean isAppDebug() {
        return com.nedhuo.libutils.utilcode.util.AppUtils.isAppDebug();
    }

    static void relaunchApp() {
        com.nedhuo.libutils.utilcode.util.AppUtils.relaunchApp();
    }

    ///////////////////////////////////////////////////////////////////////////
    // BarUtils
    ///////////////////////////////////////////////////////////////////////////
    static int getStatusBarHeight() {
        return com.nedhuo.libutils.utilcode.util.BarUtils.getStatusBarHeight();
    }

    static int getNavBarHeight() {
        return BarUtils.getNavBarHeight();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ConvertUtils
    ///////////////////////////////////////////////////////////////////////////
    static String bytes2HexString(final byte[] bytes) {
        return ConvertUtils.bytes2HexString(bytes);
    }

    static byte[] hexString2Bytes(String hexString) {
        return ConvertUtils.hexString2Bytes(hexString);
    }

    static byte[] string2Bytes(final String string) {
        return ConvertUtils.string2Bytes(string);
    }

    static String bytes2String(final byte[] bytes) {
        return ConvertUtils.bytes2String(bytes);
    }

    static byte[] jsonObject2Bytes(final JSONObject jsonObject) {
        return ConvertUtils.jsonObject2Bytes(jsonObject);
    }

    static JSONObject bytes2JSONObject(final byte[] bytes) {
        return ConvertUtils.bytes2JSONObject(bytes);
    }

    static byte[] jsonArray2Bytes(final JSONArray jsonArray) {
        return ConvertUtils.jsonArray2Bytes(jsonArray);
    }

    static JSONArray bytes2JSONArray(final byte[] bytes) {
        return ConvertUtils.bytes2JSONArray(bytes);
    }

    static byte[] parcelable2Bytes(final Parcelable parcelable) {
        return ConvertUtils.parcelable2Bytes(parcelable);
    }

    static <T> T bytes2Parcelable(final byte[] bytes,
                                  final Parcelable.Creator<T> creator) {
        return ConvertUtils.bytes2Parcelable(bytes, creator);
    }

    static byte[] serializable2Bytes(final Serializable serializable) {
        return ConvertUtils.serializable2Bytes(serializable);
    }

    static Object bytes2Object(final byte[] bytes) {
        return ConvertUtils.bytes2Object(bytes);
    }

    static String byte2FitMemorySize(final long byteSize) {
        return ConvertUtils.byte2FitMemorySize(byteSize);
    }

    static byte[] inputStream2Bytes(final InputStream is) {
        return ConvertUtils.inputStream2Bytes(is);
    }

    static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        return ConvertUtils.input2OutputStream(is);
    }

    static List<String> inputStream2Lines(final InputStream is, final String charsetName) {
        return ConvertUtils.inputStream2Lines(is, charsetName);
    }

    ///////////////////////////////////////////////////////////////////////////
    // DebouncingUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isValid(@NonNull final View view, final long duration) {
        return DebouncingUtils.isValid(view, duration);
    }

    ///////////////////////////////////////////////////////////////////////////
    // EncodeUtils
    ///////////////////////////////////////////////////////////////////////////
    static byte[] base64Encode(final byte[] input) {
        return com.nedhuo.libutils.utilcode.util.EncodeUtils.base64Encode(input);
    }

    static byte[] base64Decode(final byte[] input) {
        return EncodeUtils.base64Decode(input);
    }

    ///////////////////////////////////////////////////////////////////////////
    // EncryptUtils
    ///////////////////////////////////////////////////////////////////////////
    static byte[] hashTemplate(final byte[] data, final String algorithm) {
        return EncryptUtils.hashTemplate(data, algorithm);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FileIOUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean writeFileFromBytes(final File file,
                                      final byte[] bytes) {
        return com.nedhuo.libutils.utilcode.util.FileIOUtils.writeFileFromBytesByChannel(file, bytes, true);
    }

    static byte[] readFile2Bytes(final File file) {
        return com.nedhuo.libutils.utilcode.util.FileIOUtils.readFile2BytesByChannel(file);
    }

    static boolean writeFileFromString(final String filePath, final String content, final boolean append) {
        return com.nedhuo.libutils.utilcode.util.FileIOUtils.writeFileFromString(filePath, content, append);
    }

    static boolean writeFileFromIS(final String filePath, final InputStream is) {
        return FileIOUtils.writeFileFromIS(filePath, is);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FileUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isFileExists(final File file) {
        return com.nedhuo.libutils.utilcode.util.FileUtils.isFileExists(file);
    }

    static File getFileByPath(final String filePath) {
        return com.nedhuo.libutils.utilcode.util.FileUtils.getFileByPath(filePath);
    }

    static boolean deleteAllInDir(final File dir) {
        return com.nedhuo.libutils.utilcode.util.FileUtils.deleteAllInDir(dir);
    }

    static boolean createOrExistsFile(final File file) {
        return com.nedhuo.libutils.utilcode.util.FileUtils.createOrExistsFile(file);
    }

    static boolean createOrExistsDir(final File file) {
        return com.nedhuo.libutils.utilcode.util.FileUtils.createOrExistsDir(file);
    }

    static boolean createFileByDeleteOldFile(final File file) {
        return com.nedhuo.libutils.utilcode.util.FileUtils.createFileByDeleteOldFile(file);
    }

    static long getFsTotalSize(String path) {
        return com.nedhuo.libutils.utilcode.util.FileUtils.getFsTotalSize(path);
    }

    static long getFsAvailableSize(String path) {
        return com.nedhuo.libutils.utilcode.util.FileUtils.getFsAvailableSize(path);
    }

    static void notifySystemToScan(File file) {
        FileUtils.notifySystemToScan(file);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GsonUtils
    ///////////////////////////////////////////////////////////////////////////
    static String toJson(final Object object) {
        return com.nedhuo.libutils.utilcode.util.GsonUtils.toJson(object);
    }

    static <T> T fromJson(final String json, final Type type) {
        return com.nedhuo.libutils.utilcode.util.GsonUtils.fromJson(json, type);
    }

    static Gson getGson4LogUtils() {
        return GsonUtils.getGson4LogUtils();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImageUtils
    ///////////////////////////////////////////////////////////////////////////
    static byte[] bitmap2Bytes(final Bitmap bitmap) {
        return com.nedhuo.libutils.utilcode.util.ImageUtils.bitmap2Bytes(bitmap);
    }

    static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format, int quality) {
        return com.nedhuo.libutils.utilcode.util.ImageUtils.bitmap2Bytes(bitmap, format, quality);
    }

    static Bitmap bytes2Bitmap(final byte[] bytes) {
        return com.nedhuo.libutils.utilcode.util.ImageUtils.bytes2Bitmap(bytes);
    }

    static byte[] drawable2Bytes(final Drawable drawable) {
        return com.nedhuo.libutils.utilcode.util.ImageUtils.drawable2Bytes(drawable);
    }

    static byte[] drawable2Bytes(final Drawable drawable, final Bitmap.CompressFormat format, int quality) {
        return com.nedhuo.libutils.utilcode.util.ImageUtils.drawable2Bytes(drawable, format, quality);
    }

    static Drawable bytes2Drawable(final byte[] bytes) {
        return com.nedhuo.libutils.utilcode.util.ImageUtils.bytes2Drawable(bytes);
    }

    static Bitmap view2Bitmap(final View view) {
        return com.nedhuo.libutils.utilcode.util.ImageUtils.view2Bitmap(view);
    }

    static Bitmap drawable2Bitmap(final Drawable drawable) {
        return com.nedhuo.libutils.utilcode.util.ImageUtils.drawable2Bitmap(drawable);
    }

    static Drawable bitmap2Drawable(final Bitmap bitmap) {
        return ImageUtils.bitmap2Drawable(bitmap);
    }

    ///////////////////////////////////////////////////////////////////////////
    // IntentUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isIntentAvailable(final Intent intent) {
        return com.nedhuo.libutils.utilcode.util.IntentUtils.isIntentAvailable(intent);
    }

    static Intent getLaunchAppIntent(final String pkgName) {
        return com.nedhuo.libutils.utilcode.util.IntentUtils.getLaunchAppIntent(pkgName);
    }

    static Intent getInstallAppIntent(final File file) {
        return com.nedhuo.libutils.utilcode.util.IntentUtils.getInstallAppIntent(file);
    }

    static Intent getInstallAppIntent(final Uri uri) {
        return com.nedhuo.libutils.utilcode.util.IntentUtils.getInstallAppIntent(uri);
    }

    static Intent getUninstallAppIntent(final String pkgName) {
        return com.nedhuo.libutils.utilcode.util.IntentUtils.getUninstallAppIntent(pkgName);
    }

    static Intent getDialIntent(final String phoneNumber) {
        return com.nedhuo.libutils.utilcode.util.IntentUtils.getDialIntent(phoneNumber);
    }

    @RequiresPermission(CALL_PHONE)
    static Intent getCallIntent(final String phoneNumber) {
        return com.nedhuo.libutils.utilcode.util.IntentUtils.getCallIntent(phoneNumber);
    }

    static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return com.nedhuo.libutils.utilcode.util.IntentUtils.getSendSmsIntent(phoneNumber, content);
    }

    static Intent getLaunchAppDetailsSettingsIntent(final String pkgName, final boolean isNewTask) {
        return IntentUtils.getLaunchAppDetailsSettingsIntent(pkgName, isNewTask);
    }


    ///////////////////////////////////////////////////////////////////////////
    // JsonUtils
    ///////////////////////////////////////////////////////////////////////////
    static String formatJson(String json) {
        return JsonUtils.formatJson(json);
    }

    ///////////////////////////////////////////////////////////////////////////
    // KeyboardUtils
    ///////////////////////////////////////////////////////////////////////////
    static void fixSoftInputLeaks(final Activity activity) {
        KeyboardUtils.fixSoftInputLeaks(activity);
    }

    ///////////////////////////////////////////////////////////////////////////
    // NotificationUtils
    ///////////////////////////////////////////////////////////////////////////
    static Notification getNotification(NotificationUtils.ChannelConfig channelConfig,
                                        com.nedhuo.libutils.utilcode.util.Utils.Consumer<NotificationCompat.Builder> consumer) {
        return NotificationUtils.getNotification(channelConfig, consumer);
    }

    ///////////////////////////////////////////////////////////////////////////
    // PermissionUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isGranted(final String... permissions) {
        return PermissionUtils.isGranted(permissions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    static boolean isGrantedDrawOverlays() {
        return PermissionUtils.isGrantedDrawOverlays();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ProcessUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isMainProcess() {
        return ProcessUtils.isMainProcess();
    }

    static String getForegroundProcessName() {
        return ProcessUtils.getForegroundProcessName();
    }

    static String getCurrentProcessName() {
        return ProcessUtils.getCurrentProcessName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // RomUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isSamsung() {
        return com.nedhuo.libutils.utilcode.util.RomUtils.isSamsung();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ScreenUtils
    ///////////////////////////////////////////////////////////////////////////
    static int getAppScreenWidth() {
        return ScreenUtils.getAppScreenWidth();
    }

    ///////////////////////////////////////////////////////////////////////////
    // SDCardUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isSDCardEnableByEnvironment() {
        return SDCardUtils.isSDCardEnableByEnvironment();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ServiceUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isServiceRunning(final String className) {
        return ServiceUtils.isServiceRunning(className);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ShellUtils
    ///////////////////////////////////////////////////////////////////////////
    static com.nedhuo.libutils.utilcode.util.ShellUtils.CommandResult execCmd(final String command, final boolean isRooted) {
        return ShellUtils.execCmd(command, isRooted);
    }

    ///////////////////////////////////////////////////////////////////////////
    // SizeUtils
    ///////////////////////////////////////////////////////////////////////////
    static int dp2px(final float dpValue) {
        return com.nedhuo.libutils.utilcode.util.SizeUtils.dp2px(dpValue);
    }

    static int px2dp(final float pxValue) {
        return com.nedhuo.libutils.utilcode.util.SizeUtils.px2dp(pxValue);
    }

    static int sp2px(final float spValue) {
        return com.nedhuo.libutils.utilcode.util.SizeUtils.sp2px(spValue);
    }

    static int px2sp(final float pxValue) {
        return SizeUtils.px2sp(pxValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // SpUtils
    ///////////////////////////////////////////////////////////////////////////
    static com.nedhuo.libutils.utilcode.util.SPUtils getSpUtils4Utils() {
        return SPUtils.getInstance("Utils");
    }

    ///////////////////////////////////////////////////////////////////////////
    // StringUtils
    ///////////////////////////////////////////////////////////////////////////
    static boolean isSpace(final String s) {
        return com.nedhuo.libutils.utilcode.util.StringUtils.isSpace(s);
    }

    static boolean equals(final CharSequence s1, final CharSequence s2) {
        return com.nedhuo.libutils.utilcode.util.StringUtils.equals(s1, s2);
    }

    static String getString(@StringRes int id) {
        return com.nedhuo.libutils.utilcode.util.StringUtils.getString(id);
    }

    static String getString(@StringRes int id, Object... formatArgs) {
        return com.nedhuo.libutils.utilcode.util.StringUtils.getString(id, formatArgs);
    }

    static String format(@Nullable String str, Object... args) {
        return StringUtils.format(str, args);
    }


    ///////////////////////////////////////////////////////////////////////////
    // ThreadUtils
    ///////////////////////////////////////////////////////////////////////////
    static <T> com.nedhuo.libutils.utilcode.util.Utils.Task<T> doAsync(final Utils.Task<T> task) {
        com.nedhuo.libutils.utilcode.util.ThreadUtils.getCachedPool().execute(task);
        return task;
    }

    static void runOnUiThread(final Runnable runnable) {
        com.nedhuo.libutils.utilcode.util.ThreadUtils.runOnUiThread(runnable);
    }

    static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        com.nedhuo.libutils.utilcode.util.ThreadUtils.runOnUiThreadDelayed(runnable, delayMillis);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ThrowableUtils
    ///////////////////////////////////////////////////////////////////////////
    static String getFullStackTrace(Throwable throwable) {
        return ThrowableUtils.getFullStackTrace(throwable);
    }

    ///////////////////////////////////////////////////////////////////////////
    // TimeUtils
    ///////////////////////////////////////////////////////////////////////////
    static String millis2FitTimeSpan(long millis, int precision) {
        return TimeUtils.millis2FitTimeSpan(millis, precision);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ToastUtils
    ///////////////////////////////////////////////////////////////////////////
    static void toastShowShort(final CharSequence text) {
        com.nedhuo.libutils.utilcode.util.ToastUtils.showShort(text);
    }

    static void toastCancel() {
        ToastUtils.cancel();
    }

    private static void preLoad(final Runnable... runs) {
        for (final Runnable r : runs) {
            ThreadUtils.getCachedPool().execute(r);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // UriUtils
    ///////////////////////////////////////////////////////////////////////////
    static Uri file2Uri(final File file) {
        return com.nedhuo.libutils.utilcode.util.UriUtils.file2Uri(file);
    }

    static File uri2File(final Uri uri) {
        return UriUtils.uri2File(uri);
    }


    ///////////////////////////////////////////////////////////////////////////
    // ViewUtils
    ///////////////////////////////////////////////////////////////////////////
    static View layoutId2View(@LayoutRes final int layoutId) {
        return com.nedhuo.libutils.utilcode.util.ViewUtils.layoutId2View(layoutId);
    }

    static boolean isLayoutRtl() {
        return ViewUtils.isLayoutRtl();
    }


    ///////////////////////////////////////////////////////////////////////////
    // Common
    ///////////////////////////////////////////////////////////////////////////
    static final class FileHead {

        private String                        mName;
        private LinkedHashMap<String, String> mFirst = new LinkedHashMap<>();
        private LinkedHashMap<String, String> mLast  = new LinkedHashMap<>();

        FileHead(String name) {
            mName = name;
        }

        void addFirst(String key, String value) {
            append2Host(mFirst, key, value);
        }

        void append(Map<String, String> extra) {
            append2Host(mLast, extra);
        }

        void append(String key, String value) {
            append2Host(mLast, key, value);
        }

        private void append2Host(Map<String, String> host, Map<String, String> extra) {
            if (extra == null || extra.isEmpty()) {
                return;
            }
            for (Map.Entry<String, String> entry : extra.entrySet()) {
                append2Host(host, entry.getKey(), entry.getValue());
            }
        }

        private void append2Host(Map<String, String> host, String key, String value) {
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                return;
            }
            int delta = 19 - key.length(); // 19 is length of "Device Manufacturer"
            if (delta > 0) {
                key = key + "                   ".substring(0, delta);
            }
            host.put(key, value);
        }

        public String getAppended() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : mLast.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            return sb.toString();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String border = "************* " + mName + " Head ****************\n";
            sb.append(border);
            for (Map.Entry<String, String> entry : mFirst.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }

            sb.append("Rom Info           : ").append(RomUtils.getRomInfo()).append("\n");
            sb.append("Device Manufacturer: ").append(Build.MANUFACTURER).append("\n");
            sb.append("Device Model       : ").append(Build.MODEL).append("\n");
            sb.append("Android Version    : ").append(Build.VERSION.RELEASE).append("\n");
            sb.append("Android SDK        : ").append(Build.VERSION.SDK_INT).append("\n");
            sb.append("App VersionName    : ").append(com.nedhuo.libutils.utilcode.util.AppUtils.getAppVersionName()).append("\n");
            sb.append("App VersionCode    : ").append(AppUtils.getAppVersionCode()).append("\n");

            sb.append(getAppended());
            return sb.append(border).append("\n").toString();
        }
    }
}
