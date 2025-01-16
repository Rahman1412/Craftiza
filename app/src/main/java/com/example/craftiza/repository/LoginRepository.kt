package com.example.craftiza.repository

import android.util.Patterns
import com.example.craftiza.data.Login
import com.example.craftiza.data.Token
import com.example.craftiza.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Response

@Singleton
class LoginRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(login:Login):Response<Token>{
        return apiService.login(login);
    }


    fun validatePassword(value:String) : MutableMap<String,Any>{
        val error = mutableMapOf<String,Any>(
            "isError" to false,
            "error" to "",
            "isEnable" to true
        )
        if(value.isBlank()){
            error["isError"] = true
            error["error"] = "Password is required"
            error["isEnable"] = false
        }
        return error;
    }

    fun validateEmail(email: String): MutableMap<String,Any> {
        val error = mutableMapOf<String,Any>(
            "isError" to false,
            "error" to "",
            "isEnable" to true
        )
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            error["error"] = "Invalid email"
            error["isError"] = true
            error["isEnable"] = false
        }
        return error;
    }

}