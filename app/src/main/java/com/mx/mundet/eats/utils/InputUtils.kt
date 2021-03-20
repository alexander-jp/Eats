package com.mx.mundet.eats.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mx.mundet.eats.R
import java.util.regex.Pattern

object InputUtils {


    fun TextInputEditText.validateReactive(textInputLayout: TextInputLayout) : Boolean  {
        this.doOnTextChanged { text, _, _, count ->
            textInputLayout.isErrorEnabled = text?.isEmpty()!!
            textInputLayout.error = if (text.isEmpty()) context.getString(R.string.field_required) else null
            if (tag is String) {
                Log.e("TAG", "validateReactive: $tag")
                when (tag) {
                    "email" -> textEmail(text.toString(), textInputLayout, context)
                    "textArea" -> textMultiline(text.length, textInputLayout, context)
                    "text" -> textLine(text.toString(), textInputLayout, context)
                    "password" -> textPassword(text.toString(), textInputLayout, context)
                    else -> false
                }
            }
        }
        return false
    }

    fun TextInputLayout.validateReactive(text: String): Boolean {
        error = if (text.isEmpty()) context.getString(R.string.field_required) else null
        isErrorEnabled = text.isEmpty()
        if (text.isNotEmpty()) {
            if (tag is String) {
                return when (tag) {
                    "email" -> textEmail(text, this, context)
                    "textArea" -> textMultiline(text.length, this, context)
                    "text" -> textLine(text, this, context)
                    "password" -> textPassword(text, this, context)
                    else -> false
                }
            }
        }
        return false
    }

    fun textMultiline(count: Int, textInputLayout: TextInputLayout, ctx: Context): Boolean {
        return when (count) {
            in 1..30 -> {
                textInputLayout.error = ctx.getString(R.string.field_length_30)
                false
            }
            0 -> {
                textInputLayout.error = ctx.getString(R.string.field_required)
                false
            }
            else -> {
                textInputLayout.error = null
                true
            }
        }
    }

    fun textLine(text: String, textInputLayout: TextInputLayout, ctx: Context): Boolean {
        return when (text.isNotEmpty()) {
            true -> {
                textInputLayout.error = null
                true
            }
            false -> {
                textInputLayout.error = ctx.getString(R.string.field_required)
                false
            }
        }
    }

    fun textEmail(text: String, textInputLayout: TextInputLayout, ctx: Context): Boolean {
        return when (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            true -> {
                textInputLayout.error = null
                true
            }
            false -> {
                textInputLayout.error = ctx.getString(R.string.email_not_formed)
                false
            }
        }

    }

    fun textPassword(text: String, textInputLayout: TextInputLayout, ctx: Context): Boolean {
        return when (text.length >= 6) {
            true -> {
                textInputLayout.error = null
                true
            }
            false -> {
                textInputLayout.error = ctx.getString(R.string.field_length_6)
                false
            }
        }

    }


    fun isValidEmail(text: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    fun isValidPassword(text: String): Boolean = text.length >= 6

    fun isValidLetters(text: String): Boolean {
        return text.isNotEmpty()
    }

    fun isValidLettersTextArea(text: String): Boolean {
        return when (text.length) {
            in 1..30 -> false
            0 -> false
            else -> true
        }
    }

}