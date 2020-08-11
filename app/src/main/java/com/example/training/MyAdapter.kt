package com.example.training

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.training.databinding.RecyclerViewHeaderBinding
import com.example.training.databinding.RecyclerViewItemBinding
import com.example.training.room.Video
import com.squareup.picasso.Picasso


class MyAdapter(var list: List<Video>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER : Int = 0
    private val TYPE_LIST : Int = 1

    override fun getItemViewType(position: Int): Int {
        if(position == 0){
            return TYPE_HEADER
        }
        else{
            return TYPE_LIST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_LIST) {
            val view =
                RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(view)
        }
        else {
            val view =
                RecyclerViewHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CustomViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val current = list[position]

        if(holder is CustomViewHolder){
            val current = list[position]
            holder.channelName.text = current.channelName

            val subscribersInString = getViews(current.channelSubscribers)
            holder.channelSubscribers.text = "$subscribersInString subscribers"

            Picasso.with(context).load(current.channelImgUrl).into(holder.channelImg)

        }

        else if(holder is MyViewHolder){
            val current = list[position - 1]
        holder.ownerOfTheVideoTextView.text = current.channelName
        holder.videoTitleTextView.text = current.title
        Picasso.with(context).load(current.imageUrl).into(holder.videoImg)

        val viewsInString = getViews(current.views)

        holder.videoViewsTextView.text = "$viewsInString views"
    }



    }

    fun getViews(views: Int): String {
        if (views / 1000 != 0) {
            return "${views / 1000}K"
        } else {
            return "$views"
        }
    }

    override fun getItemCount(): Int = list.size + 1


    class MyViewHolder(itemBinding: RecyclerViewItemBinding): RecyclerView.ViewHolder(itemBinding.root){

        val videoImg = itemBinding.videoImage
        val videoTitleTextView = itemBinding.videoTitle
        val videoViewsTextView = itemBinding.videoViews
        val ownerOfTheVideoTextView = itemBinding.channelOfTheVideoName

    }

    class CustomViewHolder(recyclerViewHeaderBinding: RecyclerViewHeaderBinding): RecyclerView.ViewHolder(recyclerViewHeaderBinding.root){
        val channelImg = recyclerViewHeaderBinding.channelImage
        val channelName = recyclerViewHeaderBinding.channelName
        val channelSubscribers = recyclerViewHeaderBinding.channelSubscribers
    }
}