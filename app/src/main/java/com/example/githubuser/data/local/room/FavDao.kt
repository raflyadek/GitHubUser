package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubuser.data.local.entity.FavUser

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavUser)

    @Update
    fun update(favUser: FavUser)

    @Delete
    fun delete(favUser: FavUser)

    @Query("SELECT * FROM FavUser WHERE username = :username")
    fun getFavUserByUsername(username: String): LiveData<FavUser>

    @Query("SELECT * FROM FavUser")
    fun getFavUsers(): LiveData<List<FavUser>>

}