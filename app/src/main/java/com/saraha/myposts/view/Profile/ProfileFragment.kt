package com.saraha.myposts.view.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.saraha.myposts.View.Profile.ProfileViewModel
import com.saraha.myposts.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
private lateinit var binding: FragmentProfileBinding
private val viewmodel:ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observer()

    }


   fun  observer()
    {
       viewmodel.currentUserLiveData.observe(viewLifecycleOwner,{
           binding.textViewUserName.text=it.name
           binding.textViewUserUsername.text=it.username
       })
    }


    private fun setupToolbar() {
        val mainToolbar = binding.toolbarInProfile
        mainToolbar.title = "Profile"
        (activity as AppCompatActivity?)!!.setSupportActionBar(mainToolbar)
    }


}