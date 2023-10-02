package com.example.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.local.entity.FavUser
import com.example.githubuser.data.remote.response.GithubDetail
import com.example.githubuser.data.repository.FavRepository
import com.example.githubuser.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (private val repository: FavRepository): ViewModel() {
    fun getUserDetail(username: String) = repository.getUserDetail(username)

    fun insertFavorite(favUser: FavUser){
        viewModelScope.launch {
            repository.insert(favUser)
        }
    }

    fun deleteFavorite(favUser: FavUser){
        viewModelScope.launch {
            repository.delete(favUser)
        }
    }
    val favUser = repository.getFavUsers()
}