package com.example.craftiza.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.craftiza.data.Product
import com.example.craftiza.paging.ProductPaging
import com.example.craftiza.repository.HomeRepository
import com.example.craftiza.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val storageRepo: StorageRepository,
    private val homeRepo: HomeRepository
): ViewModel() {

    val products: Flow<PagingData<Product>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        ProductPaging(homeRepo)
    }.flow.cachedIn(viewModelScope)

    val token = storageRepo.token.asLiveData()
    val user = storageRepo.user.asLiveData()

    fun doLogOut(){
        viewModelScope.launch {
            storageRepo.clearToken();
        }
    }
}