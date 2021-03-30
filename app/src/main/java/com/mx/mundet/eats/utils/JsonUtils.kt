package com.mx.mundet.eats.utils

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

object JsonUtils {

    fun <T> readJson(json: String, clazz: Class<T>): T? {
        var gericObject: T? = null
        try {
            val gson = Gson()
            gericObject = gson.fromJson(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return gericObject
    }


    fun <T> readJson(input: InputStream?, clazz: Class<T>): T? {
        var gericObject: T? = null
        if (input != null) {
            try {
                val gson = Gson()
                val inReader = InputStreamReader(input)
                val reader = BufferedReader(inReader)
                gericObject = gson.fromJson(reader, clazz)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return gericObject
    }

    fun readJsonType(json: String, type: Type): Any? {
        var gericObject: Any? = null
        try {
            val gson = Gson()
            gericObject = gson.fromJson<Any>(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return gericObject
    }

    fun <T> createJson(objectClass: T?): String? {
        var jsonresult: String? = null
        if (objectClass != null) {
            try {
                val gson = Gson()
                jsonresult = gson.toJson(objectClass)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return jsonresult
    }

}