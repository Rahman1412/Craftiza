package com.example.craftiza.repository

import com.example.craftiza.data.Category
import com.example.craftiza.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getCategory():Response<List<Category>>{
        return apiService.getCategory()
    }
}