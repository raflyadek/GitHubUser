package com.example.githubuser.ui.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.response.Follow
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private var _userFollow = MutableLiveData<List<Follow>>()
    val userFollow : LiveData<List<Follow>> get() = _userFollow
    private var _loadingState = MutableLiveData<Boolean>()
    val loadingState : LiveData<Boolean> get() = _loadingState

     fun getFollowerList(username: String) {
        _loadingState.value = true
        val client = ApiConfig.getApiService().getFollowersList(username)
        client.enqueue(object : Callback<List<Follow>> {
            override fun onResponse(
                call: Call<List<Follow>>,
                response: Response<List<Follow>>
            ) {
                _loadingState.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userFollow.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<Follow>>, t: Throwable) {
                _loadingState.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })

    }

     fun getFollowingList(username: String) {
        _loadingState.value = true
        val client = ApiConfig.getApiService().getFollowingList(username)
        client.enqueue(object : Callback<List<Follow>> {
            override fun onResponse(
                call: Call<List<Follow>>,
                response: Response<List<Follow>>
            ) {
                _loadingState.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userFollow.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<Follow>>, t: Throwable) {
                _loadingState.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}