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
        val rotatedBitmap = rotateBitmapIfNeeded(bitmap, file)
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }

    fun rotateBitmapIfNeeded(bitmap: Bitmap, file: File): Bitmap {
        val exifInterface: ExifInterface
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            exifInterface = ExifInterface(file)
        } else {
            val inputStream = file.inputStream()
            exifInterface = ExifInterface(inputStream)
        }
        val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270f)
            else -> bitmap
        }
    }

    fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = android.graphics.Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
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