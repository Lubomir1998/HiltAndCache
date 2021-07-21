package com.example.training

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.training.databinding.RecyclerViewHeaderBinding
import com.example.training.databinding.RecyclerViewItemBinding
import com.example.training.room.Video
import com.squareup.picasso.Picasso
import javax.inject.Inject


class MyAdapter(var list: List<Video>, private val context: Context, var channelImgUrl: String, var channelName: String, var channelSubscribers: Int)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER : Int = 0
    private val TYPE_LIST : Int = 1

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            TYPE_HEADER
        } else{
            TYPE_LIST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TYPE_LIST) {
            val view =
                RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyViewHolder(view)
        } else {
            val view =
                RecyclerViewHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CustomViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        if(holder is CustomViewHolder){
            // the error is because we must not pass video instance

            holder.channelName.text = channelName

            val subscribersInString = getViews(channelSubscribers)
            holder.channelSubscribers.text = "$subscribersInString subscribers"

            if(channelImgUrl == ""){
                holder.channelImg.setImageResource(R.drawable.ic_launcher_background)
            }else {
                Picasso.with(context).load(channelImgUrl).into(holder.channelImg)
            }

        }

        else if(holder is MyViewHolder){
            val currentVideo = list[position - 1]

            holder.ownerOfTheVideoTextView.text = currentVideo.channelName
            holder.videoTitleTextView.text = currentVideo.title
            Picasso.with(context).load(currentVideo.imageUrl).into(holder.videoImg)

            val viewsInString = getViews(currentVideo.views)

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