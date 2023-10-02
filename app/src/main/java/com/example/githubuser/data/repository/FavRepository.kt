package com.example.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubuser.data.Result
import com.example.githubuser.data.local.entity.FavUser
import com.example.githubuser.data.local.room.FavDao
import com.example.githubuser.data.local.room.FavRoomDatabase
import com.example.githubuser.data.remote.response.GithubDetail
import com.example.githubuser.data.remote.response.GithubResponse
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.data.retrofit.ApiService
import com.example.githubuser.utils.AppExecutors
import com.loopj.android.http.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository private constructor(
    private val apiService: ApiService,
    private val favDao: FavDao,
    private val appExecutors: AppExecutors
) {

    private val resultUsers = MediatorLiveData<Result<List<User>>>()
    fun getUsers(username: String): LiveData<Result<List<User>>> {
        resultUsers.value = Result.Loading
        val client = apiService.getListUsers(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    users?.let {
                        resultUsers.value = Result.Success(it)
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                resultUsers.value = Result.Error(t.message.toString())
            }
        })

        return resultUsers
    }

    private val resultUserDetail = MediatorLiveData<Result<GithubDetail>>()
    fun getUserDetail(username: String): LiveData<Result<GithubDetail>> {
        resultUserDetail.value = Result.Loading
        val client = apiService.getDetailUser(username)
        client.enqueue(object : Callback<GithubDetail> {
            override fun onResponse(
                call: Call<GithubDetail>,
                response: Response<GithubDetail>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        resultUserDetail.value = Result.Success(it)
                    }
                }
            }

            override fun onFailure(call: Call<GithubDetail>, t: Throwable) {
                resultUserDetail.value = Result.Error(t.message.toString())
            }
        })

        return resultUserDetail
    }

    fun getFavUsers(): LiveData<List<FavUser>> = favDao.getFavUsers()

    fun insert(favUser: FavUser) {
        appExecutors.diskIO.execute { favDao.insert(favUser) }
    }

    fun update(favUser: FavUser) {
        appExecutors.diskIO.execute { favDao.update(favUser) }
    }

    fun delete(favUser: FavUser) {
        appExecutors.diskIO.execute { favDao.delete(favUser) }
    }

    fun getFavUserByUsername(username: String) {
        return appExecutors.diskIO.execute { favDao.getFavUserByUsername(username) }
    }

    companion object {
        @Volatile
        private var instance: FavRepository? = null
        fun getInstance(
            apiService: ApiService,
            favDao: FavDao,
            appExecutors: AppExecutors
        ): FavRepository =
            instance ?: synchronized(this) {
                instance ?: FavRepository(apiService, favDao, appExecutors)
            }.also { instance = it }
    }
}
