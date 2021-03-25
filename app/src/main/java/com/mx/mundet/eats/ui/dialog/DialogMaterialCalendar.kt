package com.mx.mundet.eats.ui.dialog

import com.google.android.material.datepicker.MaterialDatePicker
import com.mx.mundet.eats.R

/**
 * Created by Alexander Juárez with Date 22/03/2021
 * @author Alexander Juárez
 */

class DialogMaterialCalendar {

    fun onCreateDialog() : MaterialDatePicker<Long>{
        return  MaterialDatePicker.Builder.datePicker().apply {
//            setTheme(R.style.ThemeOverlay_Catalog_MaterialCalendar_Custom)
            setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        }.build()
    }

     companion object{
          @JvmStatic
          fun newInstance() = DialogMaterialCalendar().apply{

          }

          @JvmStatic
          val TAG = DialogMaterialCalendar::class.simpleName
     }
}