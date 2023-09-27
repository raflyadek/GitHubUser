package com.example.githubuser.data.repository

import android.app.Application
import com.example.githubuser.data.local.entity.Fav
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

    fun insert(fav: Fav){
        executorService.execute { mFavDao.insert(fav) }
    }

    fun delete(fav: Fav){
        executorService.execute { mFavDao.delete(fav) }
    }

    fun update(fav: Fav){
        executorService.execute { mFavDao.update(fav) }
    }

    fun getFavUserByUsername(username: String){
        executorService.execute { mFavDao.getFavUserByUsername(username) }
    }
}