package com.mx.mundet.eats.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mx.mundet.eats.R
import java.util.regex.Pattern

object InputUtils {

    fun TextInputEditText.validate(textInputLayout : TextInputLayout, validate : (String) -> Unit){
        this.doOnTextChanged { text, _, _, count ->
            textInputLayout.isErrorEnabled = text?.isEmpty()!!
            textInputLayout.error = if (text.isEmpty()) context.getString(R.string.field_required) else null
            if (tag is String) {
                when (tag) {
                    "email" -> validate.invoke(textEmail(text.toString(), textInputLayout, context).toString())
                    "textArea"-> validate.invoke(textMultiline(text.toString(), count, textInputLayout, context).toString())
                    "text" -> validate.invoke(textLine(text.toString(), textInputLayout, context).toString())
                    "password"-> validate.invoke(textPassword(text.toString(), textInputLayout, context).toString())
                }
            }
        }
    }


    fun textMultiline(text: String, count: Int, textInputLayout: TextInputLayout, ctx: Context): String? {
        return when (text.length) {
            in 1..30 -> {
                textInputLayout.error = ctx.getString(R.string.field_length_30)
                null
            }
            0 -> {
                textInputLayout.error = ctx.getString(R.string.field_required)
                null
            }
            else -> {
                textInputLayout.error = null
                text
            }
        }
    }

    fun textLine(text: String, textInputLayout: TextInputLayout, ctx: Context): String? {
        return when (text.isNotEmpty()) {
            true -> {
                textInputLayout.error = null
                text
            }
            false -> {
                textInputLayout.error = ctx.getString(R.string.field_required)
                null
            }
        }
    }

    fun textEmail(text: String, textInputLayout: TextInputLayout, ctx: Context): String? {
        return when (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            true -> {
                textInputLayout.error = null
                text
            }
            false -> {
                textInputLayout.error = ctx.getString(R.string.email_not_formed)
                null
            }
        }

    }

    fun textPassword(text: String, textInputLayout: TextInputLayout, ctx: Context): String? {
        return when (text.length >= 6) {
            true -> {
                textInputLayout.error = null
                null
            }
            false -> {
                textInputLayout.error = ctx.getString(R.string.field_length_6)
                text
            }
        }

    }


    fun isValidEmail(text: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    fun isValidPassword(text: String): Boolean = text.length >= 6

    fun isValidLetters(text: String): Boolean {
        return when(text.isNotEmpty()){
            true -> true
            false -> false
        }
    }

    fun isValidLettersTextArea(text: String): Boolean {
        return when (text.length) {
            in 1..30 -> false
            0 -> false
            else -> true
        }
    }

}