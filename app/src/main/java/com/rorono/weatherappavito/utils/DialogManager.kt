package com.rorono.weatherappavito.utils

import android.app.AlertDialog
import android.content.Context

object DialogManager {

    fun getLocationDialog(context: Context, listener:GetLocationDialogListener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Получения разрешения ")
        dialog.setMessage("Разрешить приложению получать доступ о Вашем местоположение?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Да"){ _, _->
            listener.onClickPositiveButton()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Отмена"){ _, _->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface GetLocationDialogListener{
        fun onClickPositiveButton()

    }
}