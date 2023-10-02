package com.example.githubuser.ui.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.response.GithubResponse
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.data.repository.FavRepository
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: FavRepository) : ViewModel() {

    fun getUsers(username: String) = repository.getUsers(username)
}