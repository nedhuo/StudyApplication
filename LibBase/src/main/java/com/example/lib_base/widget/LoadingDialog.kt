package com.example.lib_base.widget

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.LibBase.R

/**
 * 通用加载对话框，支持 Activity、Fragment、DialogFragment 统一调用
 */
class LoadingDialog : DialogFragment() {
    companion object {
        fun show(manager: androidx.fragment.app.FragmentManager, message: String = "加载中...", tag: String = "loading"): LoadingDialog {
            // 防止重复弹窗
            manager.findFragmentByTag(tag)?.let {
                if (it is LoadingDialog) return it
            }
            val dialog = LoadingDialog()
            val args = Bundle()
            args.putString("message", message)
            dialog.arguments = args
            dialog.isCancelable = false
            dialog.show(manager, tag)
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_loading, container, false)
        val messageText = view.findViewById<TextView>(R.id.messageText)
        messageText.text = arguments?.getString("message") ?: "加载中..."
        return view
    }

    fun updateMessage(message: String) {
        view?.findViewById<TextView>(R.id.messageText)?.text = message
    }
}