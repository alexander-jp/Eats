package com.mx.mundet.eats.ui.ext

import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Created by Alexander Juárez with Date 13/03/2021
 * @author Alexander Juárez
 */

fun Fragment.colorAttr(@AttrRes attrColor : Int) : Int{
    val typedValue = TypedValue()
    requireContext().theme.resolveAttribute(attrColor, typedValue, true)
    return typedValue.data
}

fun AppCompatActivity.colorAttr(@AttrRes attrColor : Int) : Int{
    val typedValue = TypedValue()
    theme.resolveAttribute(attrColor, typedValue, true)
    return typedValue.data
}

fun AppCompatActivity.showToast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
