package com.rorono.weatherappavito.utils

import android.app.AlertDialog
import android.content.Context
import com.rorono.weatherappavito.R

object DialogManager {
    fun getLocationDialog(context: Context, listener:GetLocationDialogListener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.get_permission))
        dialog.setMessage(context.getString(R.string.get_location))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,context.getString(R.string.ok)){ _, _->
            listener.onClickPositiveButton()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE,context.getString(R.string.cancel)){ _, _->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface GetLocationDialogListener{
        fun onClickPositiveButton()

    }
}