package com.saraha.myposts.view.Posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saraha.myposts.R
import com.saraha.myposts.databinding.ListItemPostBinding
import com.saraha.myposts.helper.loadImage
import com.saraha.myposts.model.Post

class ViewPostsAdapter(var context: Context, var data: List<Post>) :
    RecyclerView.Adapter<ViewPostsHolder>() {

    var liked = false

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPostsHolder {
        val binding = ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPostsHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPostsHolder, position: Int) {
        holder.binding.textViewPostedBy.setText(data[position].user_name)
        holder.binding.testViewUsername.setText(data[position].user_username)
        holder.binding.imageViewPhoto.loadImage(data[position].user_photo ?: context.getString(R.string.default_image))

        var number= Integer.parseInt(holder.binding.LikeNumber.text.toString())
        if( data[position].like > 0) { holder.binding.LikeNumber.visibility=View.VISIBLE
        } else { holder.binding.LikeNumber.visibility=View.GONE }

        if (data[position].content != null){
            holder.binding.contentLayout.visibility = View.VISIBLE
            holder.binding.textViewPostContent.setText(data[position].content)
        }
        if (data[position].photo != null){
            holder.binding.photoLayout.visibility = View.VISIBLE
            holder.binding.imageViewPostPhoto.loadImage(data[position].photo!!)
        }

        holder.binding.imageViewLikePost.setOnClickListener {
            if (!liked){
                number += 1
                holder.binding.imageViewLikePost.setImageResource(R.drawable.ic_favorite_24)
                liked = true
            } else {
                number -= 1
                holder.binding.imageViewLikePost.setImageResource(R.drawable.ic_unfavorite_24)
                liked = false
            }
            holder.binding.LikeNumber.visibility=View.VISIBLE
            data[position].like = number.toLong()
            holder.binding.LikeNumber.text = number.toString()
        }

        holder.binding.imageViewSharePost.setOnClickListener {
            //TODO: share post
        }
    }
}

class ViewPostsHolder(val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root)