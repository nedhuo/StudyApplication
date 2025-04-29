package com.example.lib_base.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.base.BaseDialogFragment
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.widget.LoadingDialog


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