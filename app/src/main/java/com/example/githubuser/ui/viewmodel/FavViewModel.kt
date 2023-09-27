package com.example.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.local.entity.Fav
import com.example.githubuser.data.repository.FavRepository

class FavViewModel(application: Application): ViewModel() {
    private val mFavRepository: FavRepository = FavRepository(application)

    fun insert(fav: Fav){
        mFavRepository.insert(fav)
    }

    fun update(fav: Fav){
        mFavRepository.update(fav)
    }

    fun delete(fav: Fav){
        mFavRepository.delete(fav)
    }

    fun getFavUserByUsername(username: String){
        mFavRepository.getFavUserByUsername(username)
    }
}