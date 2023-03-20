package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.example.myapplication.network.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var signInButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_sign_up)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        signInButton = binding.buttonSignIn

        binding.buttonSignIn.setOnClickListener{
            val intent = Intent(this, MarkTeacherViewActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSignUp.setOnClickListener{
            val firstName = binding.FirstNameEt.text.toString()
            val lastName = binding.LastnameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()){
                if (pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this){
                        if (it.isSuccessful){
                            //get the user id
                            val userId = it.result?.user?.uid
                            //create a user object with the additional fields
                            val user = User(email, pass, firstName, lastName)

                            //save the user object to the database
                            FirebaseDatabase.getInstance().getReference("users")
                                .child(userId ?: "")
                                .child("Informations")
                                .setValue(user)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful){
                                        Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, SignInActivity::class.java)
                                        startActivity(intent)
                                    }else{
                                        //Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }else{
                            //Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


    }
}