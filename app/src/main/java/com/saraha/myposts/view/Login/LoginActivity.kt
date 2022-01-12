package com.saraha.myposts.view.Login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.myposts.R
import com.saraha.myposts.databinding.ActivityLoginBinding
import com.saraha.myposts.view.Signup.SignupActivity


class loginActivity : AppCompatActivity() {
    val auth: FirebaseAuth = Firebase.auth
    var user=auth.currentUser
    lateinit var shared: SharedPreferences
    lateinit var sharededitor: SharedPreferences.Editor
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

                if(emailastext.isNotBlank() && passwordastext.isNotBlank())
                {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailastext,passwordastext).addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()  //startActivity(Intent(this, HomeActivity::class.java))
                        }
                        else
                        {
                            Toast.makeText(this, it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }


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
                    if (it.isSuccessful)
                    {
                        Toast.makeText(this, "Sent Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {

                Toast.makeText(this, "Email field was empty", Toast.LENGTH_SHORT).show()
            }

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
}