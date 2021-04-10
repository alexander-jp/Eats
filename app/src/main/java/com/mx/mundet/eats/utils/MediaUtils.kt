package com.mx.mundet.eats.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import androidx.exifinterface.media.ExifInterface
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun bitMaptoBase64(context : Context, filePath : Uri?) : String {
        Log.e("TAG", "bitMaptoBase64: URI $filePath")
        val bm = rotationBitmap(MediaStore.Images.Media.getBitmap(context.contentResolver, filePath), exifRotationImages(filePath))
//        val bm = rotationBitmap(MediaStore.Images.Media.getBitmap(context.contentResolver, filePath), 90F)

        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 30, stream)
        val byteFormat = stream.toByteArray()
        return java.util.Base64.getEncoder().encodeToString(byteFormat)
    }

    fun rotationBitmap(bitmap : Bitmap, degree : Float) : Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)
    }

    fun exifRotationImages(newFile : Uri?) : Float {
        val exif = newFile?.toFile()?.let { ExifInterface(it) }
        return when(exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)){
            ExifInterface.ORIENTATION_NORMAL-> 0F
            ExifInterface.ORIENTATION_ROTATE_90-> 90F
            ExifInterface.ORIENTATION_ROTATE_180-> 180F
            ExifInterface.ORIENTATION_ROTATE_270-> 270F
            else -> 0F
        }
    }

}