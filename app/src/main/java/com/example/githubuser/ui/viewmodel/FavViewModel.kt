package com.example.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.local.entity.FavUser
import com.example.githubuser.data.repository.FavRepository

class FavViewModel(application: Application): ViewModel() {
    private val mFavRepository: FavRepository = FavRepository(application)
    private var _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    fun insert(favUser: FavUser){
        mFavRepository.insert(favUser)
    }

    fun update(favUser: FavUser){
        mFavRepository.update(favUser)
    }

    fun delete(favUser: FavUser){
        mFavRepository.delete(favUser)
    }

    fun getFavUserByUsername(username: String) = mFavRepository.getFavUserByUsername(username)

    fun getFavUsers(): LiveData<List<FavUser>> = mFavRepository.getFavUsers()
}