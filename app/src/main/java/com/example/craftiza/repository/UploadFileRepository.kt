package com.example.craftiza.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.view.Surface
import android.view.WindowManager
import com.example.craftiza.data.UploadedFile
import com.example.craftiza.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException

@Singleton
class UploadFileRepository @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun uploadFile(file: File): Response<UploadedFile> {
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return apiService.uploadFiles(body)
    }

    fun bitmapToFile(bitmap: Bitmap, context: Context): File {
        val file = File(context.cacheDir, "captured_image.jpg")
        val outputStream: OutputStream = FileOutputStream(file)
        //Not Working RotateBitmapIfNeeded Function
        val rotatedBitmap = rotateBitmapIfNeeded(bitmap, context)
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }

    fun rotateBitmapIfNeeded(bitmap: Bitmap, context: Context): Bitmap {
        val orientation = getDeviceOrientation(context)
        val rotationAngle = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }

        return if (rotationAngle != 0) {
            val matrix = Matrix()
            matrix.postRotate(rotationAngle.toFloat())
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else {
            bitmap
        }
    }

    fun getDeviceOrientation(context: Context): Int {
        val file = File(context.cacheDir, "temp.jpg")
        file.createNewFile()
        val exif = ExifInterface(file.absolutePath)
        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    }

    fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val tempFile = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
        tempFile.outputStream().use { outputStream ->
            contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return tempFile
    }
}