package com.example.craftiza.repository

import com.example.craftiza.data.Product
import com.example.craftiza.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val apiService: ApiService
){

    suspend fun getProducts(offset : Int, limit : Int) : Response<List<Product>>{
        return apiService.products(offset,limit);
    }
}