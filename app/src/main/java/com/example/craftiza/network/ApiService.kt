package com.example.craftiza.network

import androidx.compose.ui.geometry.Offset
import com.example.craftiza.data.Login
import com.example.craftiza.data.Product
import com.example.craftiza.data.SignUp
import com.example.craftiza.data.Token
import com.example.craftiza.data.UploadedFile
import com.example.craftiza.data.User
import com.example.craftiza.vm.SignupVM
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body data:Login) : Response<Token>

    @Multipart
    @POST("files/upload")
    suspend fun uploadFiles(
        @Part file: MultipartBody.Part
    ): Response<UploadedFile>

    @POST("users")
    suspend fun signup(@Body data: SignUp):Response<User>

    @GET("products")
    suspend fun products(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<List<Product>>

    @GET("auth/profile")
    suspend fun profile(
        @Header("Authorization") token: String
    ): Response<User>


}