package com.example.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubuser.data.Result
import com.example.githubuser.data.local.entity.FavUser
import com.example.githubuser.data.local.room.FavDao
import com.example.githubuser.data.local.room.FavRoomDatabase
import com.example.githubuser.data.remote.response.GithubResponse
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.data.retrofit.ApiService
import com.example.githubuser.utils.AppExecutors
import com.loopj.android.http.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository (application: Application){
    private val mFavDao: FavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavRoomDatabase.getInstance(application)
        mFavDao = db.favDao()
    }
    fun getFavUsers(): LiveData<List<FavUser>> = mFavDao.getFavUsers()

    fun insert(favUser: FavUser){
        executorService.execute { mFavDao.insert(favUser) }
    }

    fun update(favUser: FavUser){
        executorService.execute { mFavDao.update(favUser) }
    }

    fun delete(favUser: FavUser){
        executorService.execute { mFavDao.delete(favUser) }
    }
    fun getFavUserByUsername(username: String) = mFavDao.getFavUserByUsername(username)
    }

