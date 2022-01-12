package com.saraha.myposts.repository

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

}