package com.saraha.myposts.view.Login

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.myposts.R
import com.saraha.myposts.databinding.ActivityLoginBinding
import com.saraha.myposts.helper.toast
import com.saraha.myposts.model.User
import com.saraha.myposts.view.Home.HomeActivity
import com.saraha.myposts.view.Home.shared
import com.saraha.myposts.view.Home.sharededitor
import com.saraha.myposts.view.Signup.SignupActivity

val auth: FirebaseAuth = Firebase.auth
var user=auth.currentUser
class loginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        shared=this.getSharedPreferences("Login", Context.MODE_PRIVATE)
        sharededitor= shared.edit()
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)

        setupToolbar()
        binding.ForgotMyPasswordInLogin.setOnClickListener { showdialog() }

        binding.GotoSignInLoginActivity.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

        binding.LoginButtonInLogin.setOnClickListener {
            val email = binding.EmailTextFieldInLogin.text.toString()
            val password = binding.PasswordTextFieldInLogin.text.toString()
            loginToAccount(email, password)
        }

        setContentView(binding.root)
    }

    private fun loginToAccount(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        this.toast(getString(R.string.login_success))
                        sharededitor.putString("email", user!!.email)
                        sharededitor.putString("id",user!!.uid)
                        sharededitor.putBoolean("status",true)
                        sharededitor.commit()


                        startActivity(Intent(this, HomeActivity::class.java))
                    } else {
                        this.toast(it.exception!!.message.toString(), Toast.LENGTH_LONG)
                    }
                }
        }
    }


    fun showdialog(){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Forgot Password")

        val input = EditText(this)
        input.setHint("Enter your Email")
        input.inputType = InputType.TYPE_CLASS_TEXT

        builder.setView(input)

        builder.setPositiveButton("Send", DialogInterface.OnClickListener { dialog, which ->
            if(input.text.isNotBlank())
            {
                FirebaseAuth.getInstance().sendPasswordResetEmail(input.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        this.toast(getString(R.string.reset_password_msg))
                    } else {
                        this.toast(it.exception.toString(), Toast.LENGTH_LONG)
                    }
                }
            } else {
                this.toast(getString(R.string.required_msq))
            }

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }


    fun setupToolbar() {
        val mainToolbar = binding.toolbarInLogin
        mainToolbar.title = getString(R.string.login)
        setSupportActionBar(mainToolbar)
    }
}