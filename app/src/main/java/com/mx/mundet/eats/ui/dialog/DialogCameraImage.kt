package com.mx.mundet.eats.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import coil.load
import com.google.android.material.appbar.MaterialToolbar
import com.mx.mundet.eats.R
import java.io.File

/**
 * Created by Alexander Juárez with Date 20/03/2021
 * @author Alexander Juárez
 */

class DialogCameraImage() : DialogFragment() {

    //TODO for load image using coil dependency

    private var imagePath : String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.activity_show_image, null)
        val image = view?.findViewById<ImageView>(R.id.img_image_account)
        val toolbar = view?.findViewById<MaterialToolbar>(R.id.toolbar_show_image)
        val alert = androidx.appcompat.app.AlertDialog.Builder(requireContext(), R.style.AppThemeDialogCustom)
        alert.setView(view)
        toolbar?.setNavigationIcon(R.drawable.ic_round_close_24)
        toolbar?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        toolbar?.setNavigationOnClickListener {
            dialog?.dismiss()
        }
        imagePath?.let {
            image?.load(File(it))
        }
        return alert.create()
    }

    companion object {
        @JvmStatic
        fun newInstance(path : String) = DialogCameraImage().apply {
            this.imagePath = path
        }

        @JvmStatic
        val TAG = DialogCameraImage::class.simpleName
    }
}