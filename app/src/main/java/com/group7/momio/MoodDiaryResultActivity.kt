package com.group7.momio

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.text.isDigitsOnly
import java.lang.IndexOutOfBoundsException
import java.time.LocalDateTime
import kotlin.math.log

class MoodDiaryResultActivity : AppCompatActivity() {

    private val current: LocalDateTime = LocalDateTime.now()

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

    private fun getMonthMood(month: Int) {

        val bigImageView = findViewById<ImageView>(R.id.img_1)
        val bigTextView = findViewById<TextView>(R.id.mood_1)
        val bigCardView = findViewById<CardView>(R.id.first)

        val midImageView = findViewById<ImageView>(R.id.img_2)
        val midTextView = findViewById<TextView>(R.id.mood_2)
        val lowImageView = findViewById<ImageView>(R.id.img_3)
        val lowTextView = findViewById<TextView>(R.id.mood_3)

        val db = MoodDatabase.getDatabase(this)
        val dao = db.getDao()

        val list = arrayListOf(0,0,0,0,0,0,0)

        val monthObject = dao.getMonth(month)
        for ( num in monthObject.moodDayArray ) {
            when ( num ) {
                0 -> list[0]++
                1 -> list[1]++
                2 -> list[2]++
                3 -> list[3]++
                4 -> list[4]++
                5 -> list[5]++
                6 -> list[6]++
            }
        }

        println(list)

        val big = list.indexOf(list.maxOrNull())
        setCardMood(bigImageView, bigTextView, big)
        list.removeAt(big)

        val midVal = list.maxOrNull()
        if ( midVal != 0 ) {

            bigCardView.

            val mid = list.indexOf(list.maxOrNull())
            setCardMood(midImageView, midTextView, mid)
            list.removeAt(mid)

            val low = list.indexOf(list.maxOrNull())
            setCardMood(lowImageView, lowTextView, low)
            list.removeAt(low)
        }
        else {
            findViewById<CardView>(R.id.second).visibility = View.GONE
            findViewById<CardView>(R.id.third).visibility = View.GONE
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setCardMood(img: ImageView, text: TextView, mood: Int){
        when ( mood ){
            0 -> {
                img.setImageResource(R.drawable.mood_crying)
                text.text = "Crying"
            }
            1 -> {
                img.setImageResource(R.drawable.mood_angry)
                text.text = "Angry"
            }
            2 -> {
                img.setImageResource(R.drawable.mood_exhausted)
                text.text = "Exhausted"
            }
            3 -> {
                img.setImageResource(R.drawable.mood_peaceful)
                text.text = "Peaceful"
            }
            4 -> {
                img.setImageResource(R.drawable.mood_energetic)
                text.text = "Energetic"
            }
            5 -> {
                img.setImageResource(R.drawable.mood_excited)
                text.text = "Excited"
            }
            6 -> {
                img.setImageResource(R.drawable.mood_happy)
                text.text = "Happy"
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

        getMonthMood(current.monthValue)

        val backButton = findViewById<ImageButton>(R.id.backButtonDiary2)

        backButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}