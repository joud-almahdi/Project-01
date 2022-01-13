package com.saraha.myposts.view.AddPost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.myposts.R
import com.saraha.myposts.databinding.FragmentAddPostBinding
import com.saraha.myposts.model.Post
import com.saraha.myposts.view.Posts.PostsFragment
import java.util.*

class AddPostFragment : Fragment() {

    //View model and binding lateinit property
    val viewModel: AddPostViewModel by viewModels()
    lateinit var binding: FragmentAddPostBinding

    var imageData: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)

        binding.imageViewAddPostCancel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.homeFrameLayout, PostsFragment()).commit()
        }

        onTextChangeValidation()

        startImageIntent()

        binding.PostButtonInAddPost.setOnClickListener {
            val contentText = binding.editTextPostContent.text
            if (viewModel.isEditTextValid &&
                (contentText?.isNotEmpty() == true || imageData != null)){
                    val post = Post(
                        null,
                        Firebase.auth.uid.toString(),
                        "name",
                        "username",
                        "profile" ?: null,
                        if (contentText.toString().isNotEmpty()) contentText.toString() else null,
                        if (imageData.toString().isNotEmpty()) imageData.toString() else null,
                        0,
                        Calendar.getInstance().timeInMillis

                    )
            }
        }

        return binding.root
    }

    private fun startImageIntent() {
        val resultImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) { handleImage(result.data) }
        }

        binding.CameraFABInAddPost.setOnClickListener {
            resultImageLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        binding.GalleryFABInAddPost.setOnClickListener {
            resultImageLauncher
                .launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
        }
    }

    private fun handleImage(data: Intent?) {
        imageData = data?.data!!
        binding.imageViewAddPostPhoto.setImageURI(imageData)
    }

    private fun onTextChangeValidation(){
        binding.editTextPostContent.addTextChangedListener {
            viewModel.validateText(binding.editTextPostContent)
        }
    }

}