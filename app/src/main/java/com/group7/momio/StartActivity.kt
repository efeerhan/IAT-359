package com.group7.momio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        supportActionBar?.hide(); //hide the title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<AppCompatButton>(R.id.loginButton1)?.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        findViewById<AppCompatButton>(R.id.signupButton1)?.setOnClickListener() {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}