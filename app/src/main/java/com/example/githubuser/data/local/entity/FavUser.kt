package com.example.githubuser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavUser(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null
)