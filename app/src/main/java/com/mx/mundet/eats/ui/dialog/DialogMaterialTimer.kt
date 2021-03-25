package com.mx.mundet.eats.ui.dialog

import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

/**
 * Created by Alexander Juárez with Date 22/03/2021
 * @author Alexander Juárez
 */

class DialogMaterialTimer {

    fun onCreateDialog() : MaterialTimePicker {
        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        return MaterialTimePicker.Builder().apply {
            setHour(hours)
            setMinute(minutes)
            setTimeFormat(TimeFormat.CLOCK_12H)
        }.build()
    }
     companion object{
          @JvmStatic
          fun newInstance() = DialogMaterialTimer().apply{

          }

          @JvmStatic
          val TAG = DialogMaterialTimer::class.simpleName
     }

}