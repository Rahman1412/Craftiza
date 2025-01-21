package com.example.craftiza.vm

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.craftiza.data.FormField
import com.example.craftiza.data.SignUp
import com.example.craftiza.repository.SignupRepository
import com.example.craftiza.repository.UploadFileRepository
import com.example.craftiza.utils.NetworkHelper
import com.example.craftiza.utils.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupVM @Inject constructor(
    private val signupRepo: SignupRepository,
    private val uploadFileRepo: UploadFileRepository,
    @ApplicationContext private val context: Context,
    private val networkHelper: NetworkHelper
): ViewModel() {
    private val _isBottomSheet = MutableStateFlow(false)
    val isBottomSheet : StateFlow<Boolean> = _isBottomSheet

    private val _name = MutableStateFlow(FormField())
    val name : StateFlow<FormField> = _name

    private val _email = MutableStateFlow(FormField())
    val email : StateFlow<FormField> = _email

    private val _password = MutableStateFlow(FormField())
    val password : StateFlow<FormField> = _password

    private val _image = MutableStateFlow<String?>(null)
    val image : StateFlow<String?>  = _image

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading



    private val _nowLoggedIn = MutableStateFlow<Boolean>(false)
    val nowLoggedIn : StateFlow<Boolean> = _nowLoggedIn



    fun setName(value:String){
        val result = signupRepo.validateName(value)
        _name.value = _name.value.copy(
            value = value,
            isError = result["isError"] as Boolean,
            error = result["error"] as String,
            isEnable =  result["isEnable"] as Boolean
        )
    }
    fun setEmail(value:String){
        val result = signupRepo.validateEmail(value)
        _email.value = _email.value.copy(
            value = value,
            isError = result["isError"] as Boolean,
            error = result["error"] as String,
            isEnable =  result["isEnable"] as Boolean
        )
    }
    fun setPassword(value:String){
        val result = signupRepo.validatePassword(value)
        _password.value = _password.value.copy(
            value = value,
            isError = result["isError"] as Boolean,
            error = result["error"] as String,
            isEnable =  result["isEnable"] as Boolean
        )
    }

    fun openBottomSheet(){
        _isBottomSheet.value = true
    }

    fun dismissBottomSheet(){
        _isBottomSheet.value = false
    }

    fun uploadImage(bitmap:Bitmap){
        _isBottomSheet.value = false
        if(!networkHelper.isNetworkConnected()){
            ToastUtils.displayToast(context,"Please check your internet connection!")
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try{
                val file = uploadFileRepo.bitmapToFile(bitmap,context)
                val response = uploadFileRepo.uploadFile(file)
                if(response.isSuccessful){
                    val data = response.body()

                    if(data != null){
                        _image.value = data.location
                    }
                    _isLoading.value = false
                }
            }catch (e : Exception){
                _isLoading.value = false
                Log.d("Error","${e.message}")
            }
        }
    }

    fun uploadFile(uri:Uri){
        viewModelScope.launch {
            try{
                _isBottomSheet.value = false
                _isLoading.value = true;
                val file = uploadFileRepo.uriToFile(uri,context);
                val response = uploadFileRepo.uploadFile(file)
                if(response.isSuccessful){
                    val data = response.body()

                    if(data != null){
                        _image.value = data.location
                    }
                    _isLoading.value = false
                }
            }catch(e: Exception){
                _isLoading.value = false
                Log.d("Error","${e.message}")
            }
        }
    }

    fun doSignup(){
        if(!networkHelper.isNetworkConnected()){
            ToastUtils.displayToast(context,"Please check your internet connection!")
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val data = SignUp(
                name = _name.value.value,
                email = _email.value.value,
                password = _password.value.value,
                avatar = _image.value ?: ""
            )
            try{
                val response = signupRepo.signup(data)
                if(response.isSuccessful){
                    val data = response.body()
                    _isLoading.value = false
                    _nowLoggedIn.value = true
                    ToastUtils.displayToast(context,"User registered successfully!")
                }else{
                    throw Exception("Unable to Signup, Please try again")
                }
            }catch (e : Exception){
                _isLoading.value = false
                ToastUtils.displayToast(context,e.message.toString())
            }
        }
    }


}