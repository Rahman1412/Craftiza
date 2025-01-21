package com.example.craftiza.vm

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.craftiza.data.FormField
import com.example.craftiza.repository.SignupRepository
import com.example.craftiza.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileVM @Inject constructor(
    private val signupRepo : SignupRepository,
    private val storageRepo: StorageRepository
): ViewModel() {
    private val _name = MutableStateFlow(FormField())
    val name : StateFlow<FormField> = _name

    private val _email = MutableStateFlow(FormField())
    val email : StateFlow<FormField> = _email

    private val _password = MutableStateFlow(FormField())
    val password : StateFlow<FormField> = _password

    private val _image = MutableStateFlow("")
    val image : StateFlow<String> = _image

    private val _userId = MutableStateFlow<Int?>(null)

    init {
        viewModelScope.launch {
            getUser()
        }
    }

    suspend fun getUser(){
        storageRepo.user.collectLatest { user ->
            setName(user.name)
            setEmail(user.email)
            setPassword(user.password)
            _image.value = user.avatar
            _userId.value = user.id
        }
    }

    fun setName(value:String){
        val result = signupRepo.validateName(value);
        _name.value = _name.value.copy(
            value = value,
            error = result["error"] as String,
            isError = result["isError"] as Boolean,
            isEnable = result["isEnable"] as Boolean
        )
    }

    fun setEmail(value:String){
        val result = signupRepo.validateEmail(value)
        _email.value = _email.value.copy(
            value = value,
            error = result["error"] as String,
            isError = result["isError"] as Boolean,
            isEnable = result["isEnable"] as Boolean
        )
    }

    fun setPassword(value: String){
        val result = signupRepo.validatePassword(value)
        _password.value = _password.value.copy(
            value = value,
            error = result["error"] as String,
            isError = result["isError"] as Boolean,
            isEnable = result["isEnable"] as Boolean
        )
    }
}