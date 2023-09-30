package com.example.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.data.local.entity.FavUser

@Database(entities = [FavUser::class], version = 2, exportSchema = false)
abstract class FavRoomDatabase : RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object{
        @Volatile
        private var instance: FavRoomDatabase? = null
        fun getInstance(context: Context): FavRoomDatabase =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavRoomDatabase::class.java, "Fav.db"
                ).build()
            }
    }
}