package com.group7.momio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton

class MoodDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_diary)

        val okayButton = findViewById<AppCompatButton>(R.id.okButtonDiary)
        val backButton = findViewById<ImageButton>(R.id.backButtonDiary1)

        backButton.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        okayButton.setOnClickListener(){
            startActivity(Intent(this, MoodDiaryResultActivity::class.java))
        }
    }
}