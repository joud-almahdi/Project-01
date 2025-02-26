package com.saraha.myposts.model

data class User (
    var user_id: String? = null,
    var name: String,
    var username: String,
    var email: String,
    var photo: String? = null,
    var personalInfo: String? = null,
    var join_date: Long
        ){

    fun isSignUpNotEmpty() = name.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty()

    fun isProfileEmpty() = name.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty()
            && photo?.isNotEmpty() == true && personalInfo?.isNotEmpty() == true

    fun signUpHash(): HashMap<String, Any?> = hashMapOf(
            "name" to name,
            "username" to username,
            "email" to email,
            "join_date" to join_date
        )

    fun profileHash(): HashMap<String, Any?> = hashMapOf(
            "name" to name,
            "username" to username,
            "email" to email,
            "photo" to photo,
            "personalInfo" to personalInfo,
            "join_date" to join_date
        )

}