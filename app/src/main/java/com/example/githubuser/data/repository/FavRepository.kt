package com.example.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.data.local.entity.FavUser
import com.example.githubuser.data.local.room.FavDao
import com.example.githubuser.data.local.room.FavRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application) {
    private val mFavDao: FavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavRoomDatabase.getDatabase(application)
        mFavDao = db.favDao()
    }

    fun insert(favUser: FavUser){
        executorService.execute { mFavDao.insert(favUser) }
    }

    fun delete(favUser: FavUser){
        executorService.execute { mFavDao.delete(favUser) }
    }

    fun update(favUser: FavUser){
        executorService.execute { mFavDao.update(favUser) }
    }

    fun getFavUserByUsername(username: String){
        executorService.execute { mFavDao.getFavUserByUsername(username) }
    }

    fun getFavUsers(): LiveData<List<FavUser>> = mFavDao.getFavUsers()
}