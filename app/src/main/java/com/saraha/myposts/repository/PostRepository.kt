package com.saraha.myposts.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.saraha.myposts.model.Post

class PostRepository {

    var dbFirestore: FirebaseFirestore? = null
    var dbFBStorage: FirebaseStorage? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore!!.firestoreSettings = settings
    }
    fun createDBStorage(){dbFBStorage = Firebase.storage}

    fun getAllPosts(): LiveData<Pair<List<Post>?, Exception?>> {
        createDBFirestore()

        val liveDataResponse = MutableLiveData<Pair<List<Post>?, Exception?>>()

        dbFirestore?.collection("Post")?.get()?.addOnCompleteListener { snapshot ->
                if (snapshot.isSuccessful && snapshot.result != null) {
                    val listOfPosts = mutableListOf<Post>()
                    for (post in snapshot.result!!) {
                        if (post.data.isNotEmpty()){
                            val user_id = post.getString("user_id")!!
                            val content = post.getString("content")
                            val photo = post.getString("photo")
                            val like = post.get("like") as Int
                            val timeStamp = post.get("timeStamp") as Long
                            val dbPost = Post(post.id, user_id, content, photo, like, timeStamp)
                            listOfPosts.add(dbPost)
                        }
                    }
                    liveDataResponse.postValue(Pair(listOfPosts, null))
                }
            }?.addOnFailureListener {
                liveDataResponse.postValue(Pair(null, it))
            }

        return liveDataResponse
    }
}