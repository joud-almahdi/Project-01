package com.saraha.myposts.view.Posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.saraha.myposts.R
import com.saraha.myposts.databinding.FragmentPostsBinding
import com.saraha.myposts.model.Post

class PostsFragment : Fragment() {
    //View model and binding lateinit property
    val viewModel: PostsViewModel by viewModels()
    lateinit var binding: FragmentPostsBinding
    //list of animals and recycler view adapter lateinit property
    lateinit var postsList: List<Post>
    lateinit var adapter: ViewPostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(inflater, container, false)

        getPosts()

        return binding.root
    }

    fun getPosts(){
        viewModel.getAllPostsFromFirebase()
        viewModel.listOfPostsLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) setRecyclerViewWithData(it!!)
        }
    }

    //Function to set data into recyclerview
    private fun setRecyclerViewWithData(posts: List<Post>) {
        postsList = posts.sortedBy { it.timeStamp }
        val recyclerView = binding.recyclerViewPosts
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = ViewPostsAdapter(this.requireContext() ,postsList)
        recyclerView.adapter = adapter
    }

}