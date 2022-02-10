package com.group7.momio

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {

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
        supportActionBar?.hide()//hide the title bar
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val emailBox = findViewById<TextInputLayout>(R.id.inputEmailCreateLayout)
        val passBox = findViewById<TextInputLayout>(R.id.inputPassCreateLayout)
        val create = findViewById<AppCompatButton>(R.id.createButton)
        val login = findViewById<AppCompatButton>(R.id.loginButton3)

        val sharedPref = this.getSharedPreferences("data", Context.MODE_PRIVATE)

        emailBox.editText?.doOnTextChanged{text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }

        passBox.editText?.doOnTextChanged{text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }

        login.setOnClickListener{
                startActivity(Intent(this, LoginActivity::class.java))
        }

        create.setOnClickListener{
            if ( validateForm(emailLiveData.value, passwordLiveData.value) ) {
                val editor: SharedPreferences.Editor = sharedPref.edit()
                if ( !sharedPref.contains(emailLiveData.value) ) {
                    editor.putString(
                        emailBox.editText?.text.toString(),
                        passBox.editText?.text.toString()
                    )
                    editor.apply()
                    Toast.makeText(applicationContext, "Account created!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                else
                    Toast.makeText(applicationContext, "Account with this email already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(email: String?, password: String?) : Boolean {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword = password != null && password.isNotBlank() && password.length >= 6
        return isValidEmail && isValidPassword
    }
}