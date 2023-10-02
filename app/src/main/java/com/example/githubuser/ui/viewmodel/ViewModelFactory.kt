package com.example.githubuser.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.repository.FavRepository
import com.example.githubuser.di.Injection
import java.lang.IllegalArgumentException

class ViewModelFactory constructor(private val favRepository: FavRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(favRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(favRepository) as T
        }
        else if (modelClass.isAssignableFrom(FavViewModel::class.java)){
            return FavViewModel(favRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class" + modelClass.name)
    }
    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInsntance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }. also { instance = it}
    }
    }
