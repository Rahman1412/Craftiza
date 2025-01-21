package com.example.craftiza.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.craftiza.repository.LoginRepository
import com.example.craftiza.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val storageRepo: StorageRepository,
    private val loginRepo: LoginRepository
) : ViewModel() {

    val token = storageRepo.token.asLiveData()
    val user = storageRepo.user.asLiveData()
    
    fun updateUser(token:String = ""){
        viewModelScope.launch { 
            val response = loginRepo.profile(token);
            if(response.isSuccessful){
                val data = response.body();
                Log.d("UpdateUserResponse","${data}");
                data?.let {
                    storageRepo.updateUser(data);
                }
            }else{
                storageRepo.clearToken();
            }
        }
    }

}