package com.example.lib_base.utils

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.*

/**
 * Activity管理器
 */
object ActivityManager {
    private val activityStack = Stack<WeakReference<Activity>>()

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        activityStack.push(WeakReference(activity))
    }

    /**
     * 移除Activity
     */
    fun removeActivity(activity: Activity) {
        activityStack.removeAll { it.get() == activity || it.get() == null }
    }

    /**
     * 获取当前Activity
     */
    fun getCurrentActivity(): Activity? {
        cleanupStack()
        return if (activityStack.isEmpty()) null else activityStack.peek().get()
    }

    /**
     * 结束当前Activity
     */
    fun finishCurrentActivity() {
        getCurrentActivity()?.finish()
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(clazz: Class<out Activity>) {
        activityStack.forEach { ref ->
            ref.get()?.let { activity ->
                if (activity.javaClass == clazz) {
                    activity.finish()
                }
            }
        }
        cleanupStack()
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        activityStack.forEach { ref ->
            ref.get()?.finish()
        }
        activityStack.clear()
    }

    /**
     * 清理已经被回收的Activity引用
     */
    private fun cleanupStack() {
        activityStack.removeAll { it.get() == null }
    }
}