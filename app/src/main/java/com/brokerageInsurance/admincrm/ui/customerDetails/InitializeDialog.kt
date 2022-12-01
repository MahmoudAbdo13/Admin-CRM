package com.brokerageInsurance.admincrm.ui.customerDetails

import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.Window

class InitializeDialog(var context: Context, var dialog: Dialog, var resource: Int) {
    fun initializeDialog(): View {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        @SuppressLint("InflateParams") val mView = LayoutInflater.from(context).inflate(
            resource, null
        )
        dialog.setContentView(mView)
        dialog.window!!.attributes = layoutParams
        return mView
    }
}