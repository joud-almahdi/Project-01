package com.saraha.myposts.helper

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

//Show toast
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

//Download Image into ImageView
fun ImageView.loadImage(imageUrl: String) {
    Picasso.get().load(imageUrl).into(this)
}