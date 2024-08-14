package com.example.firebasechatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasechatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDataRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth=FirebaseAuth.getInstance()

        initviews()
    }

    private fun initviews() {

        binding.signup.setOnClickListener {
            var name=binding.name.text.toString()
            val email=binding.email.text.toString()
            val password=binding.password.text.toString()
            signUp(name,email,password)
        }

    }

    private fun signUp(name:String,email: String, password: String) {
        //logic for signup

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDataBase(name,email,password,mAuth.currentUser?.uid!!)
                //code for jumping to home
                    startActivity(Intent(this,MainActivity::class.java))
                    Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "some error occurred", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDataBase(name: String, email: String,password: String, uid: String) {

            mDataRef=FirebaseDatabase.getInstance().getReference()
        mDataRef.child("user").child(uid).setValue(User(name,email,password,uid))
    }

}