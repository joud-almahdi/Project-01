package com.saraha.myposts.view.Signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.saraha.myposts.R
import com.saraha.myposts.view.Login.loginActivity
import com.saraha.myposts.databinding.ActivitySignupBinding
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
        if (viewModel.isEditTextValid && viewModel.user.isSignUpEmpty()){
            viewModel.user.join_date = Calendar.getInstance().timeInMillis

        }
    }

    private fun onTextChangeValidation(){
        binding.editTextSignupName.addTextChangedListener {
            viewModel.validateText(binding.editTextSignupUsername, 1)
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