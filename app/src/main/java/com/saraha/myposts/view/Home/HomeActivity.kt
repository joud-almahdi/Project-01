package com.saraha.myposts.view.Home

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.saraha.myposts.R
import com.saraha.myposts.databinding.ActivityHomeBinding
import com.saraha.myposts.view.AddPost.AddPostFragment
import com.saraha.myposts.view.Posts.PostsFragment
import com.saraha.myposts.view.Profile.ProfileFragment
lateinit var shared: SharedPreferences
lateinit var sharededitor: SharedPreferences.Editor
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setOnBottomNavItemClick()

        setContentView(binding.root)
        shared=this.getSharedPreferences("Login", Context.MODE_PRIVATE)
        sharededitor=shared.edit()

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