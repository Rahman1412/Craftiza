package com.example.craftiza.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.craftiza.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val storageRepo: StorageRepository
) : ViewModel() {

    val token = storageRepo.token.asLiveData()
    val user = storageRepo.user.asLiveData()

}