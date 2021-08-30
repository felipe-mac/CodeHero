package com.app.codehero.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.app.codehero.R
import com.app.codehero.databinding.ProgressAlertViewBinding

/**
FELIPE
 */
object DialogTools {

    private var dialog: AlertDialog? = null

    fun showMessageDialog(context: Context, title: String, message: String) {
        val builder = createDialogDefault(context, title, message)
        builder.setPositiveButton("OK"){ dialog, _ ->
            dialog.dismiss()
        }
        try{
            builder.create().show()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    fun showErrorDialog(context: Context, title: String, message: String) {
        showMessageDialog(context, title, message)
    }

    fun showProgressDialog(context: Context, message: String) {
        val binding: ProgressAlertViewBinding = ProgressAlertViewBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context).setCancelable(false)
        builder.setView(binding.root)
        binding.textviewProgressMessage.text = message
        dialog = builder.create()
        dialog?.show()

        dialog?.window?.let {
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(it.attributes)
            lp.width = WRAP_CONTENT
            lp.height = WRAP_CONTENT
            it.attributes = lp
        }
    }

    private fun createDialogDefault(context: Context, title: String, message: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        return builder
    }

    fun dismissProgressDialog() {
        dialog?.dismiss()
    }
}