package com.saraha.myposts.model

data class Post (
    var post_id: String? = null,
    var user_id: String,
    var user_name: String,
    var user_username: String,
    var user_photo: String? = null,
    var content: String? = null,
    var photo: String? = null,
    var like: Int,
    var timeStamp: Long
){
    fun isPostEmpty() = user_id.isNotEmpty() && (!content.isNullOrEmpty() || !photo.isNullOrEmpty())

    fun postHash(): HashMap<String, Any?> = hashMapOf(
        "user_id" to user_id,
        "user_name" to user_name,
        "user_username" to user_username,
        "user_photo" to user_photo,
        "content" to content,
        "photo" to photo,
        "like" to like,
        "timeStamp" to timeStamp
    )
}