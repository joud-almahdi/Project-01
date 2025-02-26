package com.saraha.myposts.view.Splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.myposts.R
import com.saraha.myposts.databinding.ActivityMainBinding
import com.saraha.myposts.view.Home.HomeActivity
import com.saraha.myposts.view.Login.loginActivity
import com.saraha.myposts.view.Signup.SignupActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val anim = binding.animationView
        anim.playAnimation()


        Handler(Looper.getMainLooper()).postDelayed({
            //Verify if user has logged in before then redirect to home or login activity
            if (Firebase.auth.currentUser?.uid?.isNotEmpty() == true){
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, loginActivity::class.java))
            }
            anim.cancelAnimation()
            finish()
        }, 3000)

        setContentView(binding.root)
    }
}