package com.saraha.myposts.View.Signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.saraha.myposts.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)

        binding.buttonSignup.setOnClickListener {

        }

        //TODO: redirect to login

        //TODO: set toolbar with title and back button

        setContentView(binding.root)
    }
}