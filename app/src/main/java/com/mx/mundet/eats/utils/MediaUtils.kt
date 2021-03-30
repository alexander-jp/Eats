package com.mx.mundet.eats.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import io.reactivex.Single
import java.io.ByteArrayOutputStream


object MediaUtils {

    fun bitmapTob64(bitmap: Bitmap, sizeDesired: Long): Single<String> {
        return Single.just(bitmap)
            .map { bitmap ->
                var outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
                var quality = 100
                do {
                    outputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    Log.d("Base64", "Size -> ${outputStream.size()}")

                    quality -= 5
                } while (outputStream.size() > sizeDesired && quality > 6)
                Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
            }
    }

    fun bitmapTob64(bitmap: Bitmap, quality: Int): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)

    }

    fun base64ToImg(base64:String): Bitmap {
        val decodedString = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun bitmapfromPath(path: String): Bitmap {
        return BitmapFactory.decodeFile(Uri.parse(path).path)
    }

}