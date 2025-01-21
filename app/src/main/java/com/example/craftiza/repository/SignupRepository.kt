package com.example.craftiza.repository

import android.util.Patterns
import com.example.craftiza.data.SignUp
import com.example.craftiza.data.User
import com.example.craftiza.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignupRepository @Inject constructor(
    private val apiService: ApiService
) {


    fun validateName(name:String):MutableMap<String,Any>{
        val error = mutableMapOf<String,Any>(
            "isError" to true,
            "error" to "Name is required",
            "isEnable" to false
        )
        if(name.isNotBlank()){
            error["isError"]  = false
            error["error"]    = ""
            error["isEnable"] = true
        }
        return error
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

    fun validatePassword(password:String): MutableMap<String,Any>{
        val error = mutableMapOf<String,Any>(
            "isError" to true,
            "error" to "Password is required",
            "isEnable" to false
        )
        if(password.isNotBlank()){
            error["isError"]  = false
            error["error"]    = ""
            error["isEnable"] = true
        }
        if(password.length < 4 && password.isNotBlank()){
            error["isError"]  = true
            error["error"] = "Password must be longer than or equal to 4 characters"
            error["isEnable"] = false
        }
        return error
    }

    suspend fun signup(data:SignUp) : Response<User> {
        return apiService.signup(data)
    }

    suspend fun updateProfile(id:Int,user:SignUp):Response<User>{
        return apiService.updateUser(id,user)
    }
}