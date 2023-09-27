package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubuser.data.local.entity.Fav

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: Fav)

    @Update
    fun update(fav: Fav)

    @Delete
    fun delete(fav: Fav)

    @Query("SELECT * FROM Fav WHERE username = :username")
    fun getFavUserByUsername(username: String): LiveData<Fav>

}