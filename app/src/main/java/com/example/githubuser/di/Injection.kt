package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.local.room.FavRoomDatabase
import com.example.githubuser.data.repository.FavRepository
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavRepository{
        val apiService = ApiConfig.getApiService()
        val database = FavRoomDatabase.getInstance(context)
        val dao = database.favDao()
        val appExecutors = AppExecutors()
        return FavRepository.getInstance(apiService, dao, appExecutors)
    }
}