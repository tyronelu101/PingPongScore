package com.simplu.pingpongscore.gamescore

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException

class SaveConfirmDialogFragment : DialogFragment() {

    private lateinit var listener: SaveConfirmDialogListener

    interface SaveConfirmDialogListener {
        fun onDialogPositiveClick(dialogFragment: DialogFragment)
        fun onDialogNegativeClick(dialogFragment: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Saving match will end the match and take you back to the main screen.")
                .setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogPositiveClick(this)
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogNegativeClick(this)
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = getTargetFragment() as SaveConfirmDialogListener
        }catch (e: ClassCastException ) {
            throw ClassCastException("Calling fragment must implement SaveConfirmDialogListener")
        }
    }
}