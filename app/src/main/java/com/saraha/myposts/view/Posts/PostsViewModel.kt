package com.saraha.myposts.view.Posts

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.myposts.model.Post
import com.saraha.myposts.repository.PostRepository

class PostsViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var listOfPostsLiveData = MutableLiveData<List<Post>>()

    //Function to handle firebase repository for retrieving all data for charities
    fun getAllPostsFromFirebase(){
        PostRepository().getAllPosts().observeForever {
            if (it.first?.isNotEmpty() == true){ listOfPostsLiveData.postValue(it.first!!) }
        }
    }

}