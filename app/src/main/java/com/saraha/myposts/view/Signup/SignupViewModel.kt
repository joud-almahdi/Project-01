package com.saraha.myposts.view.Signup

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.saraha.myposts.helper.ValidationHelper
import com.saraha.myposts.model.User

class SignupViewModel: ViewModel() {

    //
    var isEditTextValid = false
    var user = User(null, "", "", "",null, null, 0)



    //Check email textField and handle use cases
    fun validateEmail(email: TextInputEditText, index: Int) {
        val (result, isValid) = ValidationHelper().emailVerification(email.text.toString())
        handleTextFields(email, result.string, index, isValid)
    }

    //Check password textField and handle use cases
    fun validatePassword(password: TextInputEditText, index: Int) {
        val (result, isValid) = ValidationHelper().passwordValidation(password.text.toString())
        handleTextFields(password, result.string, index, isValid)
    }

    //Check name textField and handle use cases
    fun validateText(name: TextInputEditText, index: Int) {
        val (result, isValid) = ValidationHelper().fieldVerification(name.text.toString())
        handleTextFields(name, result.string, index, isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
            isEditTextValid = false
        } else {
            v.error = null
            isEditTextValid = true
            when (index){
                1 -> user.name = v.text.toString()
                2 -> user.username = v.text.toString()
                3 -> user.email = v.text.toString()
                else -> return
            }
        }
    }
}