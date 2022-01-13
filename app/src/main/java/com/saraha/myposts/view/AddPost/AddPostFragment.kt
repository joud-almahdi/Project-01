package com.saraha.myposts.view.AddPost

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.myposts.R
import com.saraha.myposts.databinding.FragmentAddPostBinding
import com.saraha.myposts.helper.hasPermissions
import com.saraha.myposts.helper.toast
import com.saraha.myposts.model.Post
import com.saraha.myposts.view.Posts.PostsFragment
import java.util.*

class AddPostFragment : Fragment() {

    //View model and binding lateinit property
    val viewModel: AddPostViewModel by viewModels()
    lateinit var binding: FragmentAddPostBinding

    var imageData: Uri? = null

    val appPermissionList = arrayOf(
        Manifest.permission.CAMERA)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)

        binding.imageViewAddPostCancel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.homeFrameLayout, PostsFragment()).commit()
        }

        checkPermission()

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
                this.requireContext().toast("success")
            }
        }

        return binding.root
    }

    private fun startImageIntent() {
        val resultGalleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) { handleGallery(result.data) }
        }
        val resultImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) { handleCamera(result.data) }
        }

        binding.CameraFABInAddPost.setOnClickListener {
            resultImageLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        binding.GalleryFABInAddPost.setOnClickListener {
            resultGalleryLauncher
                .launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
        }
    }

    private fun handleGallery(data: Intent?) {
        imageData = data?.data!!
        binding.imageViewAddPostPhoto.setImageURI(imageData)
    }

    private fun handleCamera(data: Intent?) {
        val imageBitmap = data?.data!! as Bitmap
        binding.imageViewAddPostPhoto.setImageBitmap(imageBitmap)
    }

    private fun onTextChangeValidation(){
        binding.editTextPostContent.addTextChangedListener {
            viewModel.validateText(binding.editTextPostContent)
        }
    }

    //Function to check app location permission
    private fun checkPermission(){
        val cameraPermission = this.requireContext().hasPermissions(appPermissionList[0])

        if ( !cameraPermission) {
            ActivityCompat.requestPermissions(this.requireActivity(), appPermissionList, 4)
            binding.CameraFABInAddPost.isEnabled = false
        } else { binding.CameraFABInAddPost.isEnabled = true }
    }

    //Function to handle permission response
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 4){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                this.requireContext().toast(getString(R.string.camera_permi_required))
                binding.CameraFABInAddPost.isEnabled = true
            }
        } else { binding.CameraFABInAddPost.isEnabled = false }
    }

}