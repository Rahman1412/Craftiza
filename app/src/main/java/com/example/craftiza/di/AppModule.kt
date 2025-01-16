package com.example.craftiza.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.craftiza.network.ApiService
import com.example.craftiza.repository.LoginRepository
import com.example.craftiza.utils.Constatnts
import com.example.craftiza.utils.NetworkHelper
import com.example.craftiza.utils.TokenPreferences
import com.example.craftiza.utils.TokenSerializer
import com.example.craftiza.utils.UserPrefernces
import com.example.craftiza.utils.UserSerializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper{
        return NetworkHelper(context)
    }

    @Provides
    @Singleton
    fun provideTokenPreferences(@ApplicationContext context: Context): DataStore<TokenPreferences>{
        return DataStoreFactory.create(
            serializer = TokenSerializer,
            produceFile = {
                context.dataStoreFile("token_prefs.pb")
            }
        )
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): DataStore<UserPrefernces>{
        return DataStoreFactory.create(
            serializer = UserSerializer,
            produceFile = {
                context.dataStoreFile("user_prefs.pb")
            }
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constatnts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService{
        return retrofit.create(ApiService::class.java);
    }
}