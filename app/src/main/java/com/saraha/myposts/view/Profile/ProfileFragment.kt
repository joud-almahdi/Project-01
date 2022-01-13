package com.saraha.myposts.view.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.myposts.R
import com.saraha.myposts.databinding.FragmentProfileBinding
import com.saraha.myposts.helper.loadImage
import com.saraha.myposts.view.Home.shared
import java.util.*


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    val arrayOfMonthName = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProfileBinding.inflate(layoutInflater,container,false)

        setupToolbar()

        setValues()

        return binding.root
    }

    fun setValues(){
        val photo = shared.getString("profile", null)
        if (photo == null) binding.imageViewProfilePhoto.setImageResource(R.drawable.ic_person_24)
        else {binding.imageViewProfilePhoto.loadImage(photo)}

        val info = shared.getString("personalInfo", "No bio")
        binding.textViewUserDescription.setText(info)

        val name = shared.getString("name", Firebase.auth.uid!!)
        binding.textViewUserName.setText(name)

        val username = shared.getString("username", Firebase.auth.uid!!)
        val usernameText = getString(R.string.at)+username
        binding.textViewUserUsername.setText(usernameText)

        val join_date = shared.getLong("join_date", 0)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = join_date
        val joinDateText = "Joined "+arrayOfMonthName[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR)
        binding.textViewUserJoinDate.setText(joinDateText)
    }

    private fun setupToolbar() {
        val mainToolbar = binding.toolbarInProfile
        mainToolbar.title = "Profile"
        (activity as AppCompatActivity?)!!.setSupportActionBar(mainToolbar)
    }


}