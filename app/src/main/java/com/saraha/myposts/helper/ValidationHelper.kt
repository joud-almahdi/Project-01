package com.saraha.myposts.helper

import android.util.Patterns

class ValidationHelper {

    enum class inputHandler(val string: String){
        Required("Required"),
        IncorrectEmail("Email does not follow the correct format"),
        IncorrectPassword("should be at least 8 characters, 2 digits and no symbols"),
        IncorrectMobile("should be between 9 to 12 digits"),
        IncorrectLink("The link is not in the correct format"),
        Correct("nothing"),
        Mismatch("Does not match password")
    }

    fun fieldVerification(editText: String): Pair<inputHandler,Boolean>{
        return if (editText.isEmpty()){
            Pair(inputHandler.Required, false)
        } else {
            Pair(inputHandler.Correct, true)
        }
    }

    //Check email textField and handle use cases
    //verify email is not empty and the email is in a correct format
    fun emailVerification(email: String): Pair<inputHandler,Boolean> {
        return if (email.isEmpty()) {
            Pair(inputHandler.Required, false)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Pair(inputHandler.IncorrectEmail, false)
        } else {
            Pair(inputHandler.Correct, true)
        }
    }

    fun passwordValidation(password: String): Pair<inputHandler,Boolean>{
        //Password rules:
        //A password must have at least eight characters.
        //A password consists of only letters and digits.
        //A password must contain at least two digits.
        return if (password.isEmpty()) {
            Pair(inputHandler.Required, false)
        } else if (password.length < 8 || !password.matches("([A-Za-z0-9]*)(\\D*\\d){2,}".toRegex())){
            Pair(inputHandler.IncorrectPassword, false)
        } else {
            Pair(inputHandler.Correct, true)
        }

    }

}