package com.group7.momio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import java.text.SimpleDateFormat
import java.util.*
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.text.isDigitsOnly
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue


class MoodDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_diary)

        val daysBackgroundList = listOf<LinearLayout>(
            findViewById(R.id.day01),   //monday
            findViewById(R.id.day02),   //tuesday
            findViewById(R.id.day03),   //wednesday
            findViewById(R.id.day04),   //thursday
            findViewById(R.id.day05),   //friday
            findViewById(R.id.day06),   //saturday
            findViewById(R.id.day07)    //sunday
        )

        val cardsList = listOf<CardView>(
            findViewById(R.id.happyCard),
            findViewById(R.id.excitedCard),
            findViewById(R.id.energeticCard),
            findViewById(R.id.peacefulCard),
            findViewById(R.id.exhaustedCard),
            findViewById(R.id.angryCard),
            findViewById(R.id.cryingCard)
        )

        //TODO: Make selecting an emotion choose a value

        for ( card in cardsList ) {
            card.setOnClickListener{
                for ( x in cardsList )
                    x.cardElevation = 10F
                card.cardElevation = 40F
            }
        }

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
                if ( child is TextView ) {
                    if (child.text.isDigitsOnly())
                        child.text = dayRange[num++].toString()
                }
            }
        }

        whiteBackground(dayView)
        val childCount = dayView.childCount

        for ( i in 0..childCount ) {
            val child = dayView.getChildAt(i)
            if ( child is TextView ) {
                child.setTextColor(resources.getColor(R.color.purple_100, theme))
            }
        }

        val okayButton = findViewById<AppCompatButton>(R.id.okButtonDiary)
        val backButton = findViewById<ImageButton>(R.id.backButtonDiary1)

        backButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        okayButton.setOnClickListener{
            startActivity(Intent(this, MoodDiaryResultActivity::class.java))
        }
    }

    private fun whiteBackground(view: LinearLayout) {
        view.setBackgroundResource(R.drawable.calendar_white_bg)
    }
}