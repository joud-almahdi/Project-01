package com.saraha.myposts.view.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.myposts.model.Post
import com.saraha.myposts.model.User
import com.saraha.myposts.repository.PostRepository
import com.saraha.myposts.repository.UserRepository

class HomeViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var currentUserLiveData = MutableLiveData<User>()

    //Function to handle firebase repository for retrieving all data for charities
    fun getUsersInfoFromFirebase(){
        UserRepository().getUserAccount().observeForever {
            if (it.first != null){ currentUserLiveData.postValue(it.first!!) }
        }
    }
}