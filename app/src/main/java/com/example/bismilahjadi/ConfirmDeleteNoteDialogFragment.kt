package com.pandamy.notepad

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmDeleteNoteDialogFragment(val noteTitle : String? = "") : DialogFragment() {

    interface ConfirmDeleteNoteDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener : ConfirmDeleteNoteDialogListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)
        builder.setMessage("Yakin akan menghapus catatan ini?  : ${noteTitle}")
            .setPositiveButton("Ya",DialogInterface.OnClickListener{ dialogInterface, id -> listener?.onDialogPositiveClick() })
            .setNegativeButton("Tidak",DialogInterface.OnClickListener{ dialogInterface, id -> listener?.onDialogNegativeClick() })
        return builder.create()
    }
}