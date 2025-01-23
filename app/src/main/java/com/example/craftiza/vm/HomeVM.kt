package com.example.craftiza.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.craftiza.data.Product
import com.example.craftiza.paging.ProductPaging
import com.example.craftiza.repository.HomeRepository
import com.example.craftiza.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val storageRepo: StorageRepository,
    private val homeRepo: HomeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class)
    val products: Flow<PagingData<Product>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        ProductPaging(homeRepo)
    }.flow.cachedIn(viewModelScope)
        .flatMapLatest{ pagingData ->
            searchQuery.map { query ->
                pagingData.filter { product ->
                    product.title.contains(query, ignoreCase = true)
                }
            }
        }

    val token = storageRepo.token.asLiveData()
    val user = storageRepo.user.asLiveData()

    fun doLogOut() {
        viewModelScope.launch {
            storageRepo.clearToken();
        }
    }

    fun search(value:String){
        _searchQuery.value = value
    }
}