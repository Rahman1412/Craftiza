package com.example.craftiza.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.craftiza.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val storageRepo: StorageRepository
): ViewModel() {
    val token = storageRepo.token.asLiveData()

    fun doLogOut(){
        viewModelScope.launch {
            storageRepo.clearToken();
        }
    }
}