package com.example.craftiza.vm

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.craftiza.data.FormField
import com.example.craftiza.data.Login
import com.example.craftiza.data.Token
import com.example.craftiza.repository.LoginRepository
import com.example.craftiza.repository.StorageRepository
import com.example.craftiza.utils.NetworkHelper
import com.example.craftiza.utils.TokenPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val loginRepo: LoginRepository,
    private val networkInfo: NetworkHelper,
    @ApplicationContext private val context: Context,
    private val storageRepo: StorageRepository
) : ViewModel() {
    private val _email = MutableStateFlow(FormField())
    val email: StateFlow<FormField> = _email

    private val _password = MutableStateFlow(FormField())
    val password: StateFlow<FormField> = _password

    private var toast: Toast? = null
    val token = storageRepo.token.asLiveData()
    val user = storageRepo.user.asLiveData();

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading;

    init {
        observeInternet()
    }

    private fun observeInternet() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                networkInfo.isNetworkAvailable.collectLatest { available ->
                    if (!available) {
                        displayToast("Internet Unavailable!");
                    }
                }
            }
        }
    }

    fun setEmail(value: String) {
        val result = loginRepo.validateEmail(value);
        _email.value = _email.value.copy(
            value = value,
            isError = result["isError"] as Boolean,
            error = result["error"] as String,
            isEnable = result["isEnable"] as Boolean
        )
    }

    fun setPassword(value: String) {
        val result = loginRepo.validatePassword(value)
        _password.value = _password.value.copy(
            value = value,
            isError = result["isError"] as Boolean,
            error = result["error"] as String,
            isEnable = result["isEnable"] as Boolean
        )
    }

    fun doLogin() {
        if (networkInfo.isNetworkConnected()) {
            viewModelScope.launch {
                _isLoading.value = true
                val data = Login(
                    email = _email.value.value,
                    password = _password.value.value
                )
                try {
                    val response = loginRepo.login(data);
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        val data = response.body();
                        if (data != null) {
                            val user = loginRepo.profile(data.access_token);
                            user.body()?.let { storageRepo.updateUser(it) }
                            storageRepo.updateToken(data)
                        }
                    } else {
                        _isLoading.value = false
                        displayToast("Something went wrong, Please try again!")
                    }
                } catch (e: Exception) {
                    _isLoading.value = false
                    displayToast("Something went wrong, Please try again!")
                }
            }
        } else {
            displayToast("Please check your internet connection!")
        }
    }


    private fun displayToast(message: String = "") {
        Handler(Looper.getMainLooper()).post {
            toast?.cancel()
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    fun clear(){
        viewModelScope.launch {
            storageRepo.clearToken();
        }
    }
}