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

    private fun whiteBackground(view: LinearLayout) {
        view.setBackgroundResource(R.drawable.calendar_white_bg)
    }

    private fun calendarBackground(){
        val daysBackgroundList = listOf<LinearLayout>(
            findViewById(R.id.day01),   //monday
            findViewById(R.id.day02),   //tuesday
            findViewById(R.id.day03),   //wednesday
            findViewById(R.id.day04),   //thursday
            findViewById(R.id.day05),   //friday
            findViewById(R.id.day06),   //saturday
            findViewById(R.id.day07)    //sunday
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

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_diary)

        calendarBackground()

        val cardsList = listOf<CardView>(
            findViewById(R.id.happyCard),
            findViewById(R.id.excitedCard),
            findViewById(R.id.energeticCard),
            findViewById(R.id.peacefulCard),
            findViewById(R.id.exhaustedCard),
            findViewById(R.id.angryCard),
            findViewById(R.id.cryingCard)
        )

        var emotionalValue = -1

        for ( card in cardsList ) {
            card.setOnClickListener{
                for ( x in cardsList )
                    x.cardElevation = 10F
                card.cardElevation = 40F
                when ( card ) {
                    cardsList[0] -> emotionalValue = EValue.HAPPY.emotion
                    cardsList[1] -> emotionalValue = EValue.EXCITED.emotion
                    cardsList[2] -> emotionalValue = EValue.ENERGETIC.emotion
                    cardsList[3] -> emotionalValue = EValue.PEACEFUL.emotion
                    cardsList[4] -> emotionalValue = EValue.EXHAUSTED.emotion
                    cardsList[5] -> emotionalValue = EValue.ANGRY.emotion
                    cardsList[6] -> emotionalValue = EValue.CRYING.emotion
                }
            }
        }

        val okayButton = findViewById<AppCompatButton>(R.id.okButtonDiary)
        val backButton = findViewById<ImageButton>(R.id.backButtonDiary1)

        backButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        //THIS IS OKAY BUTTON. MAKE THIS PUT THE VARIABLE "emotionalValue" INTO TODAY'S SLOT
        //IN THE DATABASE WITH AN INSERT FUNCTION CALL.
        okayButton.setOnClickListener{
            startActivity(Intent(this, MoodDiaryResultActivity::class.java))
        }
    }
}