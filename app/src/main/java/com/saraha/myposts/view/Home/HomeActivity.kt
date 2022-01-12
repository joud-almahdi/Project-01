package com.saraha.myposts.view.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saraha.myposts.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)



        setContentView(binding.root)
    }
}