package com.group7.momio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import java.time.LocalDateTime

class MoodDiaryResultActivity : AppCompatActivity() {

    private fun whiteBackground(view: LinearLayout) {
        view.setBackgroundResource(R.drawable.calendar_white_bg)
    }

    private fun calendarBackground(){
        val daysBackgroundList = listOf<LinearLayout>(
            findViewById(R.id.rDay01),   //monday
            findViewById(R.id.rDay02),   //tuesday
            findViewById(R.id.rDay03),   //wednesday
            findViewById(R.id.rDay04),   //thursday
            findViewById(R.id.rDay05),   //friday
            findViewById(R.id.rDay06),   //saturday
            findViewById(R.id.rDay07)    //sunday
        )

        val current = LocalDateTime.now()

        val dayOfMonth = current.dayOfMonth
        val dayOfWeek = current.dayOfWeek.value

        val dayView = daysBackgroundList[dayOfWeek-1]
        val daysAfter = 7 - dayOfWeek
        val daysBefore = 6 - daysAfter
        val dayRange = mutableListOf<Int>()

        for ( i in daysBefore downTo 1 )
            dayRange.add(dayOfMonth-i)
        dayRange.add(dayOfMonth)
        for ( i in 1..daysAfter )
            dayRange.add(dayOfMonth+i)

        var num = 0

        for ( day in daysBackgroundList ) {
            val childCount = day.childCount
            for ( i in 0..childCount ) {
                val child = day.getChildAt(i)
                if ( child is TextView) {
                    if (child.text.isDigitsOnly())
                        child.text = dayRange[num++].toString()
                }
            }
        }

        whiteBackground(dayView)
        val childCount = dayView.childCount

        for ( i in 0..childCount ) {
            val child = dayView.getChildAt(i)
            if ( child is TextView) {
                child.setTextColor(resources.getColor(R.color.purple_100, theme))
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_diary_result)

        calendarBackground()

        val backButton = findViewById<ImageButton>(R.id.backButtonDiary2)

        backButton.setOnClickListener(){
            startActivity(Intent(this, MoodDiaryActivity::class.java))
        }
    }
}