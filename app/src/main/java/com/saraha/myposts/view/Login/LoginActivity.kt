package com.saraha.myposts.view.Login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.saraha.myposts.R
import com.saraha.myposts.databinding.ActivityLoginBinding
import com.saraha.myposts.view.Signup.SignupActivity


class loginActivity : AppCompatActivity() {
private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ForgotMyPasswordInLogin.setOnClickListener {
            showdialog()

        }

        binding.GotoSignInLoginActivity.setOnClickListener {
            val intent=Intent(this,SignupActivity::class.java)
            startActivity(intent)

        }





        binding.LoginButtonInLogin.setOnClickListener {

            var emailastext=binding.EmailTextFieldInLogin.text.toString()
            var passwordastext=binding.PasswordTextFieldInLogin.text.toString()




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

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
}