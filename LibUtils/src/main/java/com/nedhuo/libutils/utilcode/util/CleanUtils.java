package com.nedhuo.libutils.utilcode.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.Utils;
import com.blankj.utilcode.util.UtilsBridge;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : utils about clean
 * </pre>
 */
public final class CleanUtils {

    private CleanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Clean the internal cache.
     * <p>directory: /data/data/package/cache</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalCache() {
        return UtilsBridge.deleteAllInDir(com.blankj.utilcode.util.Utils.getApp().getCacheDir());
    }

    /**
     * Clean the internal files.
     * <p>directory: /data/data/package/files</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalFiles() {
        return UtilsBridge.deleteAllInDir(com.blankj.utilcode.util.Utils.getApp().getFilesDir());
    }

    /**
     * Clean the internal databases.
     * <p>directory: /data/data/package/databases</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalDbs() {
        return UtilsBridge.deleteAllInDir(new File(com.blankj.utilcode.util.Utils.getApp().getFilesDir().getParent(), "databases"));
    }

    /**
     * Clean the internal database by name.
     * <p>directory: /data/data/package/databases/dbName</p>
     *
     * @param dbName The name of database.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalDbByName(final String dbName) {
        return com.blankj.utilcode.util.Utils.getApp().deleteDatabase(dbName);
    }

    /**
     * Clean the internal shared preferences.
     * <p>directory: /data/data/package/shared_prefs</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalSp() {
        return UtilsBridge.deleteAllInDir(new File(com.blankj.utilcode.util.Utils.getApp().getFilesDir().getParent(), "shared_prefs"));
    }

    /**
     * Clean the external cache.
     * <p>directory: /storage/emulated/0/android/data/package/cache</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanExternalCache() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && UtilsBridge.deleteAllInDir(com.blankj.utilcode.util.Utils.getApp().getExternalCacheDir());
    }

    /**
     * Clean the custom directory.
     *
     * @param dirPath The path of directory.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanCustomDir(final String dirPath) {
        return UtilsBridge.deleteAllInDir(UtilsBridge.getFileByPath(dirPath));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void cleanAppUserData() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        am.clearApplicationUserData();
    }
}
