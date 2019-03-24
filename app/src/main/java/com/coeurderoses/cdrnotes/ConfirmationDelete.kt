package com.coeurderoses.cdrnotes

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

@SuppressLint("ValidFragment")
class ConfirmationDelete @SuppressLint("ValidFragment") constructor(val noteTitle: String) : DialogFragment() {

    // creation of interface question
    interface  confirmDeleteDailogListener{
        fun onDialogPositive()
        fun onDialogNegative()
    }

    var listener: confirmDeleteDailogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Etes vous sur de supprimer la note \"$noteTitle\" ?")
            .setPositiveButton("Supprimer",DialogInterface.OnClickListener{
                dialog, id -> listener?.onDialogPositive()
            }).setNegativeButton("Annuler", DialogInterface.OnClickListener{
                dialog, id -> listener?.onDialogNegative()
            })

        return builder.create()
    }
}