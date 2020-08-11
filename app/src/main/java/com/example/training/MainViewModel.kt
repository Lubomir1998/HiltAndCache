package com.example.training

import android.view.View
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.training.room.Video
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainViewModel
@ViewModelInject constructor(private val repository: Repository): ViewModel() {

    val listOfVideosLiveData: LiveData<List<Video>> = repository.getAllFromDB()

    fun insert(video: Video){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(video)
        }
    }



}