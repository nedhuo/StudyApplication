package com.example.lib_base.ext

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.example.lib_base.FragmentBindingDelegate
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.base.BaseDialogFragment
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ui.LoadingDialog


fun BaseActivity.showLoading(message: String = "Loading..."): LoadingDialog {
    val dialog = LoadingDialog()
    dialog.show(supportFragmentManager, message)
    return dialog
}

fun BaseFragment.showLoading(message: String = "Loading..."): LoadingDialog {
    val dialog = LoadingDialog()
    dialog.show(childFragmentManager, message)
    return dialog
}

fun BaseDialogFragment.showLoading(message: String = "Loading..."): LoadingDialog {
    val dialog = LoadingDialog()
    dialog.show(childFragmentManager, message)
    return dialog
}

fun LoadingDialog.hideLoading() {
    dismiss()
}

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