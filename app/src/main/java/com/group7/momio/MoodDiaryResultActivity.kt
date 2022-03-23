package com.group7.momio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.text.isDigitsOnly
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException
import java.time.LocalDateTime

class MoodDiaryResultActivity : AppCompatActivity() {

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

        var numBefore = 30
        var numBeforeExtra = 31
        var numBeforeFeb = 28
        var numBeforeFebLeap = 29
        var numAfter = 1

        for ( i in daysBefore downTo 1 ) {
            if ( arrayOf(1,3,5,7,8,10,12).contains(current.monthValue-1) ) {
                if (dayOfMonth - i < 1)
                    try {
                        dayRange.add(dayRange.size-1, numBeforeExtra)
                        numBeforeExtra--
                    } catch (e: IndexOutOfBoundsException) {
                        dayRange.add(numBeforeExtra--)
                    }
                else
                    dayRange.add(dayOfMonth - i)
            }
            if ( !arrayOf(1,3,5,7,8,10,12).contains(current.monthValue-1) && current.monthValue-1 != 2 ) {
                if (dayOfMonth - i < 1)
                    try {
                        dayRange.add(dayRange.size-1, numBefore)
                        numBefore--
                    } catch (e: IndexOutOfBoundsException) {
                        dayRange.add(numBefore--)
                    }
                else
                    dayRange.add(dayOfMonth - i)
            }
            if ( current.monthValue-1 == 2 && current.year % 4 != 0 ) {
                if (dayOfMonth - i < 1)
                    try {
                        dayRange.add(dayRange.size-1, numBeforeFeb)
                        numBeforeFeb--
                    } catch (e: IndexOutOfBoundsException) {
                        dayRange.add(numBeforeFeb--)
                    }
                else
                    dayRange.add(dayOfMonth - i)
            }
            if ( current.monthValue-1 == 2 && current.year % 4 == 0 ) {
                if (dayOfMonth - i < 1)
                    try {
                        dayRange.add(dayRange.size-1, numBeforeFebLeap)
                        numBeforeFebLeap--
                    } catch (e: IndexOutOfBoundsException) {
                        dayRange.add(numBeforeFebLeap--)
                    }
                else
                    dayRange.add(dayOfMonth - i)
            }
        }


        dayRange.add(dayOfMonth)

        for ( i in 1..daysAfter ){
            if ( arrayOf(1,3,5,7,8,10,12).contains(current.monthValue) ) {
                if (dayOfMonth + i > 31)
                    dayRange.add(numAfter++)
                else
                    dayRange.add(dayOfMonth + i)
            }
            if ( !arrayOf(1,3,5,7,8,10,12).contains(current.monthValue) && current.monthValue != 2 ) {
                if (dayOfMonth + i > 30)
                    dayRange.add(numAfter++)
                else
                    dayRange.add(dayOfMonth + i)
            }
            if ( current.monthValue == 2 && current.year % 4 != 0 ) {
                if (dayOfMonth + i > 28)
                    dayRange.add(numAfter++)
                else
                    dayRange.add(dayOfMonth + i)
            }
            if ( current.monthValue == 2 && current.year % 4 == 0 ) {
                if (dayOfMonth + i > 29)
                    dayRange.add(numAfter++)
                else
                    dayRange.add(dayOfMonth + i)
            }


        }


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

    private fun getMonthMood() {

        val currentMonth = LocalDateTime.now().month.value
        val db = MoodDatabase.getDatabase(this)
        val dao = db.getDao()

        var crying = 0
        var angry = 0
        var exhausted = 0
        var peaceful = 0
        var happy = 0
        var excited = 0
        var energetic = 0
        val list = arrayListOf(
            crying,
            angry,
            exhausted,
            peaceful,
            happy,
            excited,
            energetic)

        try {
            val month: MoodMonth = dao.getMonth(currentMonth)
            val monthArray = month.moodDayArray
            for ( num in monthArray ) {
                when ( num ) {
                    0 -> crying++
                    1 -> angry++
                    2 -> exhausted++
                    3 -> peaceful++
                    4 -> happy++
                    5 -> excited++
                    6 -> energetic++
                }
            }
        } catch (e: NullPointerException){
            peaceful++
        }

        val big = list.maxOrNull()
        list.remove(big)
        val mid = list.maxOrNull()
        list.remove(mid)
        val low = list.maxOrNull()
        list.remove(low)

        val bigImageView = findViewById<ImageView>(R.id.img_1)
        val bigTextView = findViewById<TextView>(R.id.mood_1)

        val midImageView = findViewById<ImageView>(R.id.img_2)
        val midTextView = findViewById<TextView>(R.id.mood_2)

        val lowImageView = findViewById<ImageView>(R.id.img_3)
        val lowTextView = findViewById<TextView>(R.id.mood_3)
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
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}