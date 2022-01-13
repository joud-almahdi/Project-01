package com.saraha.myposts.view.Signup

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseNetworkException
import com.saraha.myposts.R
import com.saraha.myposts.view.Login.loginActivity
import com.saraha.myposts.databinding.ActivitySignupBinding
import com.saraha.myposts.view.Home.HomeActivity
import java.util.*

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)

        setupToolbar()

        onTextChangeValidation()

        binding.buttonSignup.setOnClickListener {
            verifyRegistrationFormFields()
        }

        binding.buttonRedirectToLogin.setOnClickListener {
            startActivity(Intent(this, loginActivity::class.java))
        }

        setContentView(binding.root)
    }

    //Check all data is entered then send to firebase authentication
    private fun verifyRegistrationFormFields() {
        val password = binding.editTextSignupPassword
        val email = binding.editTextSignupEmail
        if (viewModel.isEditTextValid && viewModel.user.isSignUpNotEmpty() && password.text?.isNotEmpty() == true){
            viewModel.user.join_date = Calendar.getInstance().timeInMillis

            createUserFirebaseAuth(email, password)
        }
    }

    private fun createUserFirebaseAuth(email: TextInputEditText, password: TextInputEditText) {
        viewModel.signUpUserInFirebase(email.text.toString(), password.text.toString())
        viewModel.signInResponseLiveData.observe(this) { result ->
            if (result.first) createUserAccount()
            else handleException(result)
        }
    }

    private fun handleException(result: Pair<Boolean, Exception?>) {
        try {
            throw result.second!!
        } catch (e: FirebaseNetworkException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun createUserAccount() {
        viewModel.createAnAccountInFirebase(viewModel.user.signUpHash())
        viewModel.createAccountResponseLiveData.observe(this) { result ->
            if (result.first) startActivity(Intent(this, HomeActivity::class.java))
            else handleException(result)
        }
    }

    private fun onTextChangeValidation(){
        binding.editTextSignupName.addTextChangedListener {
            viewModel.validateText(binding.editTextSignupName, 1)
        }
        binding.editTextSignupUsername.addTextChangedListener {
            viewModel.validateText(binding.editTextSignupUsername, 2)
        }
        binding.editTextSignupEmail.addTextChangedListener {
            viewModel.validateEmail(binding.editTextSignupEmail, 3)
        }
        binding.editTextSignupPassword.addTextChangedListener {
            viewModel.validatePassword(binding.editTextSignupPassword, 0)
        }
    }

    //Function to setup activity toolbar with title and back button
    private fun setupToolbar() {
        val mainToolbar = binding.toolbarSignUp
        mainToolbar.title = getString(R.string.signup_toolbar_title)
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}