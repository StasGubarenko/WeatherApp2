package com.bignerdranch.android.weatherapp.presentation.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ErrorDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let{
            val builder = AlertDialog.Builder(it)
            //тут очень странно. Написано что данная строчка должна была предотвратить
            //закрытие окна, если тапнул за пределы диалогового окна, но по факту окно закрывается как при false, так и при true
            builder.setMessage("Что-то пошло не так")
                .setCancelable(true)
                .setPositiveButton("OK"){buttonAction,id ->
                    buttonAction.cancel()
                }
            builder.create()
        }
    }
}