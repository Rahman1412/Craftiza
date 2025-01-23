package com.example.craftiza.vm

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.craftiza.data.User
import com.example.craftiza.repository.StorageRepository
import com.example.craftiza.utils.ToastUtils
import com.example.craftiza.utils.UserPrefernces
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val storageRepo: StorageRepository,
    @ApplicationContext val context:Context
): ViewModel() {
    private val _user = storageRepo.user.asLiveData()
    val user : LiveData<UserPrefernces> = _user
 }