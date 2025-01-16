package com.example.craftiza.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.example.craftiza.data.Token
import com.example.craftiza.utils.TokenPreferences
import com.example.craftiza.utils.UserPrefernces
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepository @Inject constructor(
    private val tokenDataStore : DataStore<TokenPreferences>,
    private val userDataStore: DataStore<UserPrefernces>
){
    val token : Flow<TokenPreferences> = tokenDataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(TokenPreferences.getDefaultInstance())
            }else{
                throw exception
            }
        }

    val user : Flow<UserPrefernces> = userDataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(UserPrefernces.getDefaultInstance())
            }else{
                throw exception
            }
        }

    fun updateUser(){

    }

    suspend fun updateToken(token:Token){
        tokenDataStore.updateData { prefernces->
            prefernces.toBuilder()
                .setAccessToken(token.access_token)
                .setRefreshToken(token.refresh_token)
                .build()
        }
    }

    suspend fun clearToken(){
        tokenDataStore.updateData {
            TokenPreferences.getDefaultInstance()
        }
    }
}