package com.example.training.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.training.MainViewModel
import com.example.training.MyAdapter
import com.example.training.R
import com.example.training.databinding.MainFragmentBinding
import com.example.training.room.Video
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.main_fragment) {

    private lateinit var binding: MainFragmentBinding
    private val model: MainViewModel by viewModels()

    private val client = OkHttpClient()
    private val request = Request.Builder()
        .url("https://api.letsbuildthatapp.com/youtube/home_feed")
        .build()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity?)?.supportActionBar?.title = "YouTube"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // set recycler view
        binding.recyclerView.apply {
            adapter = MyAdapter(listOf(), requireContext())
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        // observe the live data
        model.listOfVideosLiveData.observe(requireActivity(), Observer {
            displayData(it)
        })


        parseData()

         //pull refresh
        binding.swipeRefresh.setOnRefreshListener {
            parseData()
            binding.swipeRefresh.isRefreshing = false
        }

    }




    private fun displayData(list: List<Video>){
        val adapter = binding.recyclerView.adapter as MyAdapter
        adapter.list = list
        adapter.notifyDataSetChanged()

    }

    private fun parseData(){
        client.newCall(request).enqueue(object : Callback{
            override fun onResponse(call: Call, response: Response) {

                // find video title, views, and so on...
                // add the video in the database

                val responseData = response.body()?.string()
                try {
                    val json = JSONObject(responseData!!)

                    val JSONArrayOfVideos = json.getJSONArray("videos")

                    val list = mutableListOf<Video>()

                    for (i in 0 until JSONArrayOfVideos.length()){
                        val videoId = JSONArrayOfVideos.getJSONObject(i).getInt("id")
                        val videoImgUrl = JSONArrayOfVideos.getJSONObject(i).getString("imageUrl")
                        val videoTitle = JSONArrayOfVideos.getJSONObject(i).getString("name")
                        val videoViews = JSONArrayOfVideos.getJSONObject(i).getInt("numberOfViews")

                        val channel = JSONArrayOfVideos.getJSONObject(i).getJSONObject("channel")

                        val videoChannel = channel.getString("name")
                        val subscribers = channel.getInt("numberOfSubscribers")
                        val channelImgUrl = channel.getString("profileImageUrl")

                        val video = Video(videoId, videoImgUrl, videoTitle, videoViews, videoChannel, subscribers, channelImgUrl)
                        model.insert(video)

                        }

                    CoroutineScope(Dispatchers.Main).launch {
                        binding.progressBar.visibility = View.GONE
                        binding.swipeRefresh.visibility = View.VISIBLE
                    }

                } catch (e: IOException){
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "$e", Toast.LENGTH_LONG).show()
                }

            }


            override fun onFailure(call: Call, e: IOException) {
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "$e", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}