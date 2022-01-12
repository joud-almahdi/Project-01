package com.saraha.myposts.view.Posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saraha.myposts.databinding.ListItemPostBinding
import com.saraha.myposts.model.Post

class ViewPostsAdapter(var context: Context, var data: List<Post>) :
    RecyclerView.Adapter<ViewPostsHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPostsHolder {
        val binding = ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPostsHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPostsHolder, position: Int) {
        //holder.binding.textViewPostedBy.setText(data[position].)
        //holder.binding.testViewUsername.setText(data[position].)
        if (data[position].content != null){
            holder.binding.contentLayout.visibility = View.VISIBLE
            holder.binding.textViewPostContent.setText(data[position].content)
        }
        if (data[position].photo != null){
            holder.binding.photoLayout.visibility = View.VISIBLE
            //holder.binding.imageViewPostPhoto
        }

        holder.binding.imageViewLikePost.setOnClickListener {
            //TODO: increase post likes
        }
        holder.binding.imageViewSharePost.setOnClickListener {
            //TODO: share post
        }
    }
}

class ViewPostsHolder(val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root)