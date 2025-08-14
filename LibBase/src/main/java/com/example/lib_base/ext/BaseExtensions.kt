package com.example.lib_base.ext

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.viewbinding.ViewBinding
import com.example.lib_base.FragmentBindingDelegate
import com.example.lib_base.GenericViewModelFactory
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.base.BaseDialogFragment
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ui.LoadingDialog



fun FragmentActivity.addFragment(containerId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.beginTransaction()
        .add(containerId, fragment, tag)
        .commit()
}

fun FragmentActivity.replaceFragment(containerId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment, tag)
        .commit()
}

fun FragmentManager.popBackStackIfNeeded() {
    if (backStackEntryCount > 0) popBackStack()
}

inline fun <reified VM : ViewModel> ComponentActivity.viewModelLazy(
    noinline factory: (() -> VM)? = null
): Lazy<VM> {
    // 使用 reified 关键字直接获取泛型类型，无需通过反射
    val viewModelClass = VM::class.java

    return viewModels {
        // 如果传入了 factory lambda，则使用它来创建 ViewModel
        factory?.let { GenericViewModelFactory(it) }
        // 否则，创建一个默认的 Factory，用于创建无参的 ViewModel
            ?: object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    if (modelClass.isAssignableFrom(viewModelClass)) {
                        try {
                            @Suppress("UNCHECKED_CAST")
                            return viewModelClass.getDeclaredConstructor().newInstance() as T
                        } catch (e: NoSuchMethodException) {
                            // 抛出更清晰的错误，指导开发者要么提供 factory，要么添加无参构造函数
                            throw IllegalArgumentException(
                                "ViewModel ${viewModelClass.simpleName} must have a no-arg constructor or you must provide a factory lambda.",
                                e
                            )
                        }
                    }
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
    }
}

inline fun <reified VM : ViewModel> Fragment.viewModelLazy(): Lazy<VM> {
    // 检查泛型类型是否合法
    val viewModelClass = VM::class.java
    if (!ViewModel::class.java.isAssignableFrom(viewModelClass)) {
        throw IllegalArgumentException("Generic type must be a subclass of ViewModel.")
    }

    // 返回一个 by viewModels 委托，传入自定义的 factory
    return viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(viewModelClass)) {
                    @Suppress("UNCHECKED_CAST")
                    return viewModelClass.getDeclaredConstructor().newInstance() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}

inline fun <reified VB : ViewBinding> Activity.bindings() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> Dialog.bindings() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> Fragment.bindings() =
    FragmentBindingDelegate(VB::class.java)

inline fun Fragment.doOnDestroyView(crossinline block: () -> Unit) =
    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            block.invoke()
        }
    })