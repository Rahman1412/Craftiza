package com.example.craftiza.network

import com.example.craftiza.data.Login
import com.example.craftiza.data.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body data:Login) : Response<Token>


}