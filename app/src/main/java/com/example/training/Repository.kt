package com.example.training

import androidx.lifecycle.LiveData
import com.example.training.room.Video
import com.example.training.room.VideoDao
import javax.inject.Inject

class Repository @Inject constructor(private val dao: VideoDao) {

    suspend fun insert(video: Video){
        dao.insert(video)
    }

    fun getAllFromDB(): LiveData<List<Video>> = dao.getAllFromDB()

}