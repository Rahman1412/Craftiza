package com.example.craftiza.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.craftiza.data.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(): ViewModel() {
    private val _login = mutableStateOf(Login())
    val login = _login


    fun setEmail(value:String){
        _login.value = _login.value.copy(
            email = value
        )
    }

    fun setPassword(value: String){
        _login.value = _login.value.copy(
            password = value
        )
    }
}