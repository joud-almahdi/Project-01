package com.saraha.myposts.model

data class Post (
    var post_id: String? = null,
    var user_id: String,
    var content: String? = null,
    var photo: String? = null,
    var like: Int,
    var timeStamp: Long
){
    fun isPostEmpty() = user_id.isNotEmpty() && (!content.isNullOrEmpty() || !photo.isNullOrEmpty())

    fun postHash(): HashMap<String, Any?> = hashMapOf(
        "user_id" to user_id,
        "content" to content,
        "photo" to photo,
        "like" to like,
        "timeStamp" to timeStamp
    )
}