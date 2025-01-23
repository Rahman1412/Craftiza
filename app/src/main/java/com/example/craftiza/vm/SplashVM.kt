package com.example.craftiza.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.craftiza.repository.LoginRepository
import com.example.craftiza.repository.StorageRepository
import com.example.craftiza.utils.NetworkHelper
import com.example.craftiza.utils.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val storageRepo: StorageRepository,
    private val loginRepo: LoginRepository,
    private val networkHelper: NetworkHelper,
    @ApplicationContext private val context : Context
) : ViewModel() {

    val token = storageRepo.token.asLiveData()
    val user = storageRepo.user.asLiveData()
    
    fun updateUser(token:String = ""){
        if(!networkHelper.isNetworkConnected()){
            ToastUtils.displayToast(context,"Internet Unavailable!")
            return
        }
        viewModelScope.launch {
            try{
                val response = loginRepo.profile(token);
                if(response.isSuccessful){
                    val data = response.body();
                    data?.let {
                        storageRepo.updateUser(data);
                    }
                }else{
                    throw Exception("Something went wrong!")
                }
            }catch (e:Exception){
                storageRepo.clearToken();
            }
        }
    }

}