package com.example.craftiza.vm

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.craftiza.data.FormField
import com.example.craftiza.data.SignUp
import com.example.craftiza.data.UploadedFile
import com.example.craftiza.data.User
import com.example.craftiza.repository.SignupRepository
import com.example.craftiza.repository.StorageRepository
import com.example.craftiza.repository.UploadFileRepository
import com.example.craftiza.utils.NetworkHelper
import com.example.craftiza.utils.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class UpdateProfileVM @Inject constructor(
    private val signupRepo : SignupRepository,
    private val storageRepo: StorageRepository,
    @ApplicationContext private val context: Context,
    private val networkHelper: NetworkHelper,
    private val uploadFileRepo: UploadFileRepository
): ViewModel() {
    private val _name = MutableStateFlow(FormField())
    val name : StateFlow<FormField> = _name

    private val _email = MutableStateFlow(FormField())
    val email : StateFlow<FormField> = _email

    private val _password = MutableStateFlow(FormField())
    val password : StateFlow<FormField> = _password

    private val _image = MutableStateFlow("")
    val image : StateFlow<String> = _image

    private val _userId = MutableStateFlow<Int>(0)

    private val _isLoading = MutableStateFlow(false)
    val isLoading :StateFlow<Boolean> = _isLoading

    private val _isBottomSheet = MutableStateFlow<Boolean>(false)
    val isBottomSheet : StateFlow<Boolean> = _isBottomSheet

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

    fun updateProfile(){
        _isLoading.value = true
        viewModelScope.launch {
            val data = SignUp(
                name = _name.value.value,
                email = _email.value.value,
                password = _password.value.value,
                avatar = _image.value
            )
            try{
                val response = signupRepo.updateProfile(_userId.value,data)
                if(response.isSuccessful){
                    _isLoading.value = false
                    val user = response.body();
                    user?.let {
                        storageRepo.updateUser(it)
                        ToastUtils.displayToast(context,"Profile updated successfully!")
                    }
                }else{
                    throw Exception("Unable to update profile, Please try after sometimes!")
                }
            }catch (e:Exception){
                _isLoading.value = false
                ToastUtils.displayToast(context,e.message.toString())
            }
        }
    }

    fun uploadFile(image:Any,type:String){
        if(!networkHelper.isNetworkConnected()){
            ToastUtils.displayToast(context,"Please check your internet connection!")
            return
        }
        viewModelScope.launch {
            try{
                _isLoading.value = true
                val response : Response<UploadedFile>
                if(type == "bitmap"){
                    val file = uploadFileRepo.bitmapToFile(image as Bitmap,context)
                    response = uploadFileRepo.uploadFile(file)
                }else{
                    val file = uploadFileRepo.uriToFile(image as Uri,context)
                    response = uploadFileRepo.uploadFile(file)
                }
                if(response.isSuccessful){
                    _isLoading.value = false
                    val data = response.body()
                    data?.run {
                        _image.value = data.location
                    }
                }else{
                    throw Exception("Unable to upload file, Please try again")
                }
            }catch (e:Exception){
                _isLoading.value = false
                ToastUtils.displayToast(context,e.message.toString())
            }
        }
    }

    fun toggleBottomSheet(value:Boolean = false){
        _isBottomSheet.value = value
    }
}