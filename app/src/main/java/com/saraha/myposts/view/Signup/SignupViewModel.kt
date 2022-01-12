package com.saraha.myposts.view.Signup

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.saraha.myposts.helper.ValidationHelper

class SignupViewModel: ViewModel() {

    //
    var isEditTextValid = false



    //Check email textField and handle use cases
    fun validateEmail(email: TextInputEditText) {
        val (result, isValid) = ValidationHelper().emailVerification(email.text.toString())
        handleTextFields(email, result.string, isValid)
    }

    //Check password textField and handle use cases
    fun validatePassword(password: TextInputEditText) {
        val (result, isValid) = ValidationHelper().passwordValidation(password.text.toString())
        handleTextFields(password, result.string, isValid)
    }

    //Check name textField and handle use cases
    fun validateText(name: TextInputEditText) {
        val (result, isValid) = ValidationHelper().fieldVerification(name.text.toString())
        handleTextFields(name, result.string, isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, isValid: Boolean){
        if (!isValid){
            v.error = msg
            isEditTextValid = false
        } else {
            v.error = null
            isEditTextValid = true
        }
    }
}