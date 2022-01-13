package com.saraha.myposts.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.saraha.myposts.model.User

class UserRepository {
    var dbFirestore: FirebaseFirestore? = null
    var dbFBStorage: FirebaseStorage? = null
    var dbFBAuth: FirebaseAuth? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore!!.firestoreSettings = settings
    }
    fun createDBStorage(){dbFBStorage = Firebase.storage}
    fun createDBAuth(){dbFBAuth = Firebase.auth}

    fun signinUser(email: String, password: String): LiveData<Pair<Boolean, Exception?>> {
        if (dbFBAuth == null) createDBAuth()

        val liveDataUser = MutableLiveData<Pair<Boolean, Exception?>>()

        dbFBAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { result ->
                if (result.isSuccessful) { liveDataUser.postValue(Pair(true, null)) }
            }?.addOnFailureListener {
                liveDataUser.postValue(Pair(false, it))
            }
        return liveDataUser
    }

    fun signupUser(email: String, password: String): LiveData<Pair<Boolean, Exception?>> {
        if (dbFBAuth == null) createDBAuth()

        val liveDataUser = MutableLiveData<Pair<Boolean, Exception?>>()


        dbFBAuth?.createUserWithEmailAndPassword(email,password)
            ?.addOnSuccessListener {
                liveDataUser.postValue(Pair(true, null))
            }
            ?.addOnFailureListener { liveDataUser.postValue(Pair(false, it)) }

        return liveDataUser
    }

    fun createUserAccount(newUser: HashMap<String, Any?>): LiveData<Pair<Boolean, Exception?>> {
        if (dbFirestore == null) createDBFirestore()

        val liveDataUser = MutableLiveData<Pair<Boolean, Exception?>>()

        val currentUser = Firebase.auth.currentUser?.uid

        dbFirestore?.collection("Users")?.document(currentUser.toString())
            ?.set(newUser)?.addOnCompleteListener {
                if (it.isSuccessful){ liveDataUser.postValue(Pair(true, null)) }
            }?.addOnFailureListener { liveDataUser.postValue(Pair(false, it)) }

        return liveDataUser
    }

    fun getUserAccount(): LiveData<Pair<User?, Exception?>>{
        if (dbFirestore == null) createDBFirestore()

        val liveDataUser = MutableLiveData<Pair<User?, Exception?>>()

        dbFirestore?.collection("Users")?.document(Firebase.auth.uid!!)?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful && it.result?.data?.isNotEmpty() == true){
                    val userData = it.result!!
                    val name = userData.getString("name")!!
                    val username = userData.getString("username")!!
                    val email = userData.getString("email")!!
                    val photo = userData.getString("photo")
                    val personalInfo = userData.getString("personalInfo")
                    val join_date = userData.getLong("join_date")!!

                    val user = User(Firebase.auth.uid!!, name, username, email, photo, personalInfo, join_date)
                    liveDataUser.postValue(Pair(user, null))
                }
            }?.addOnFailureListener { liveDataUser.postValue(Pair(null, it)) }

        return liveDataUser
    }



}