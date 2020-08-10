package com.example.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.training.databinding.RecyclerViewItemBinding
import com.example.training.room.Video
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyAdapter(var list: List<Video>, private val context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = list[position]

        holder.ownerOfTheVideoTextView.text = current.channelName
        holder.videoTitleTextView.text = current.title
        Picasso.with(context).load(current.imageUrl).into(holder.videoImg)

        val viewsInString = getViews(current.views)

        holder.videoViewsTextView.text = "$viewsInString views"

    }

    fun getViews(views: Int): String{
        if(views/1000 != 0){
            return "${views/1000}K"
        }

        else{
            return "$views"
        }
    }


    override fun getItemCount(): Int = list.size


    class MyViewHolder(itemBinding: RecyclerViewItemBinding): RecyclerView.ViewHolder(itemBinding.root){

        val videoImg = itemBinding.videoImage
        val videoTitleTextView = itemBinding.videoTitle
        val videoViewsTextView = itemBinding.videoViews
        val ownerOfTheVideoTextView = itemBinding.channelOfTheVideoName


    }
}