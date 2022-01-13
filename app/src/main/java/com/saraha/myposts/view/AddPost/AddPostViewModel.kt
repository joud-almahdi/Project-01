package com.saraha.myposts.view.AddPost

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.saraha.myposts.helper.ValidationHelper
import com.saraha.myposts.model.Post

class AddPostViewModel: ViewModel() {

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

    fun validatePhoto(photo: Uri){

    }
}