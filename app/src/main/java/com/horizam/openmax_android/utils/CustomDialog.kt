package com.horizam.openmax_android.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.horizam.openmax_android.R

class CustomDialog(private val activity :Activity) {
    private lateinit var isDialog : AlertDialog
    private val inflater = activity.layoutInflater

    @SuppressLint("InflateParams")
    private val writeNfcDialog = inflater.inflate(R.layout.write_nfc_dialog, null)

    fun startLoadingToWriteNfc(){
        val builder = AlertDialog.Builder(activity)
        builder.setView(writeNfcDialog)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
        isDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn : ImageView = isDialog.findViewById(R.id.ivCross)
        btn.setOnClickListener {
            isDialog.dismiss()
        }
    }

}