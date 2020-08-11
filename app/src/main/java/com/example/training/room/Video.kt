package com.example.training.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val title: String,
    val views: Int,
    val channelName: String
//    val channelSubscribers: Int,
//    val channelImgUrl: String
)