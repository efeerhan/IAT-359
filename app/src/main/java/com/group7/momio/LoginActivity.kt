package com.group7.momio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
        this.value = false

        addSource(emailLiveData) { email ->
            val password = passwordLiveData.value
            this.value = validateForm(email, password)
        }

        addSource(passwordLiveData) { password ->
            val email = emailLiveData.value
            this.value = validateForm(email, password)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailBox = findViewById<TextInputLayout>(R.id.inputEmailLayout)
        val passBox = findViewById<TextInputLayout>(R.id.inputPassLayout)
        val login = findViewById<AppCompatButton>(R.id.loginButton2)
        val forgot = findViewById<AppCompatButton>(R.id.forgotPassword)
        val signup = findViewById<AppCompatButton>(R.id.signupButton2)

        val sharedPref = this.getSharedPreferences("data", Context.MODE_PRIVATE)

        emailBox.editText?.doOnTextChanged{text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }

        passBox.editText?.doOnTextChanged{text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }

        login.setOnClickListener{
            if ( validateForm(emailLiveData.value, passwordLiveData.value) ){
                val savedPass: String? = sharedPref.getString(emailLiveData.value, "not found")
                Toast.makeText(applicationContext,savedPass + " " + passwordLiveData.value,Toast.LENGTH_SHORT).show()
                if ( passwordLiveData.value == savedPass ){
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            else
                Toast.makeText(applicationContext,"Incorrect Info :(",Toast.LENGTH_SHORT).show()
        }
        signup.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }

    private fun validateForm(email: String?, password: String?) : Boolean {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword = password != null && password.isNotBlank() && password.length >= 6
        return isValidEmail && isValidPassword
    }


}