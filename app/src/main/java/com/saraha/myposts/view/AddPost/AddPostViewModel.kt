package com.saraha.myposts.view.AddPost

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.saraha.myposts.helper.ValidationHelper
import com.saraha.myposts.model.Post
import com.saraha.myposts.repository.PostRepository
import java.lang.Exception

class AddPostViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    val postedPhotoLiveData = MutableLiveData<String>()
    val firebaseResponseLiveData = MutableLiveData<Pair<Boolean, Exception?>>()


    //Function to handle firebase repository for uploading photo
    fun setPhotoInFireStorage(photo: String){
        PostRepository().setPhotoInStorage(Uri.parse(photo)).observeForever {
            if (it.isNotEmpty()) postedPhotoLiveData.postValue(it)
        }
    }

    //Function to handle firebase repository for inserting post
    fun insertPostIntoFireStore(newPost: HashMap<String, Any?>){
        PostRepository().insertPost(newPost).observeForever {
            if (it.first) firebaseResponseLiveData.postValue(it)
        }
    }

    //boolean variable to verify if validation is true or not
    var isEditTextValid = true
    //user object to hold signup values
    var post = Post(null, "", "", "", null,
        null, null, 0, 0)

    //Check name textField and handle use cases
    fun validateText(name: TextInputEditText) {
        val (result, isValid) = ValidationHelper().fieldVerification(name.text.toString())
        if (!isValid){
            name.error = result.string
            isEditTextValid = false
        } else {
            name.error = null
            isEditTextValid = true
            post.content = name.text.toString()
        }
    }
}