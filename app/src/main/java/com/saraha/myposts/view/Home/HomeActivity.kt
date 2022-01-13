package com.saraha.myposts.view.Home

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.saraha.myposts.R
import com.saraha.myposts.databinding.ActivityHomeBinding
import com.saraha.myposts.view.AddPost.AddPostFragment
import com.saraha.myposts.view.Posts.PostsFragment
import com.saraha.myposts.view.Profile.ProfileFragment

lateinit var shared: SharedPreferences
lateinit var sharedEditor: SharedPreferences.Editor

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        shared = this.getSharedPreferences("Account", Context.MODE_PRIVATE)

        getUsersInfo()

        setOnBottomNavItemClick()

        setContentView(binding.root)
    }

    fun getUsersInfo(){
        viewModel.getUsersInfoFromFirebase()

        viewModel.currentUserLiveData.observe(this){user ->
            sharedEditor = shared.edit()
            sharedEditor.putString("name", user.name)
            sharedEditor.putString("username", user.username)
            sharedEditor.putString("email", user.email)
            sharedEditor.putString("photo", user.photo)
            sharedEditor.putString("personalInfo", user.personalInfo)
            sharedEditor.putLong("join_date", user.join_date)
            sharedEditor.commit()
        }
    }

    fun setOnBottomNavItemClick(){
        displayFragment(PostsFragment())

        binding.bottomNavigationHome.setOnItemSelectedListener {
            when (it.itemId){
                R.id.main_home -> {displayFragment(PostsFragment())}
                R.id.add_home -> {displayFragment(AddPostFragment())}
                R.id.profile_home -> {displayFragment(ProfileFragment())}
                else -> {displayFragment(PostsFragment())}
            }
            true
        }
    }

    //Function to replace a fragment view
    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.homeFrameLayout.id, fragment).commit()
    }
}