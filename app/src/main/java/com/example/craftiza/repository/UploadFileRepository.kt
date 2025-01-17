package com.example.craftiza.repository

import android.content.Context
import android.graphics.Bitmap
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

@Singleton
class UploadFileRepository @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun uploadFile(file:File) : Response<UploadedFile>{
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return apiService.uploadFiles(body)
    }

     fun bitmapToFile(bitmap: Bitmap,context:Context): File {
        val file = File(context.cacheDir, "captured_image.jpg")
        val outputStream: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }
}