package com.saraha.myposts.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.saraha.myposts.model.Post
import java.util.*

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
                            val user_name = post.getString("user_name")!!
                            val user_username = post.getString("user_username")!!
                            val user_photo = post.getString("user_photo")
                            val content = post.getString("content")
                            val photo = post.getString("photo")
                            val like = post.getLong("like")!!
                            val timeStamp = post.get("timeStamp") as Long
                            val dbPost = Post(post.id, user_id, user_name, user_username, user_photo, content, photo, like, timeStamp)
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

    fun setPhotoInStorage(fileUri: Uri): LiveData<String> {
        createDBStorage()

        val fileName = UUID.randomUUID().toString() +".jpg"

        val liveDataImage = MutableLiveData<String>()

        val ref = dbFBStorage?.reference?.child(Firebase.auth.uid.toString())?.child(fileName)

        val uploadTask = ref?.putFile(fileUri)
        uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                Log.d(ContentValues.TAG,"could not upload image: ${task.result?.error}")
            }
            ref.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d(ContentValues.TAG, downloadUri.toString())
                liveDataImage.postValue(downloadUri.toString())
            }
        }?.addOnFailureListener{
            Log.d(ContentValues.TAG,"could not upload image: ${it.message}")
        }
        return liveDataImage
    }
}