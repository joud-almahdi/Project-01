package com.saraha.myposts.view.Signup

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.saraha.myposts.helper.ValidationHelper
import com.saraha.myposts.model.User
import com.saraha.myposts.repository.UserRepository

class SignupViewModel: ViewModel() {

    //boolean variable to verify if validation is true or not
    var isEditTextValid = false
    //user object to hold signup values
    var user = User(null, "", "", "",null, null, 0)
    //Variable to get liveData response from Firebase
    var signInResponseLiveData = MutableLiveData<Pair<Boolean, Exception?>>()
    var createAccountResponseLiveData = MutableLiveData<Pair<Boolean, Exception?>>()

    //Function to handle firebase repository for signing up a user
    fun signUpUserInFirebase(email: String, password: String){
        Log.d(TAG,"SignupViewModel: - signUpUserInFirebase: - : ${email} - $password")
        UserRepository().signupUser(email, password).observeForever {
            Log.d(TAG,"SignupViewModel: - signUpUserInFirebase: - : ${it.first} - ${it.second}")
            signInResponseLiveData.postValue(it)
        }
    }

    //Function to handle firebase repository for creating an account for a user
    fun createAnAccountInFirebase(newUser: HashMap<String, Any?>){
        Log.d(TAG,"SignupViewModel: - createAnAccountInFirebase: - : ${newUser.values}")
        UserRepository().createUserAccount(newUser).observeForever {
            Log.d(TAG,"SignupViewModel: - createAnAccountInFirebase: - : ${it.first} - ${it.second}")
            createAccountResponseLiveData.postValue(it)
        }
    }

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
            Log.d(TAG,"SignupViewModel: - handleTextFields: - : ${v.text.toString()}")
            when (index){
                1 -> user.name = v.text.toString()
                2 -> user.username = v.text.toString()
                3 -> user.email = v.text.toString()
                else -> return
            }
        }
    }
}