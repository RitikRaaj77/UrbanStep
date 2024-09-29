package com.example.urbanstep.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.urbanstep.R
import com.example.urbanstep.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySigninBinding
    private lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.btnUp.setOnClickListener {
            val email = binding.emailTxt.text.toString()
            val password = binding.passtxt.text.toString()

            logIn(email, password)
            hideKeyboard(this,binding.btnUp)
        }

        binding.authSignUpTxt.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            finish()
            startActivity(i)
        }

    }

    private fun logIn(email: String, password: String) {
        binding.pb1.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val i = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(i)
                    Toast.makeText(
                        baseContext,
                        "Authentication Successful.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
                binding.pb1.visibility = View.GONE
            }
    }

    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(
            view.windowToken,
            0
        )
    }
}