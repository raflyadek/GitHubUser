package com.example.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.local.entity.FavUser
import com.example.githubuser.data.repository.FavRepository

class FavViewModel(private val repository: FavRepository): ViewModel() {
    val favUser = repository.getFavUsers()
}