package com.example.craftiza.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.craftiza.data.Category
import com.example.craftiza.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryVM @Inject constructor(
    private val categoryRepo: CategoryRepository
): ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading : StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow("")
    val error : StateFlow<String> = _error

    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories : LiveData<List<Category>> = _categories

    init {
        getCategory()
    }

    fun getCategory(){
        viewModelScope.launch {
            _loading.value = true
            try{
                val response = categoryRepo.getCategory();
                if(response.isSuccessful){
                    _loading.value = false
                    _error.value = "";
                    val body = response.body();
                    body?.run {
                        _categories.value = body ?: emptyList()
                    }
                }else{
                    throw Exception("Something went wrong, Please try again!")
                }
            }catch (e: Exception){
                _loading.value = false
                _error.value = e.message.toString()
            }
        }
    }
}