package com.group7.momio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.text.isDigitsOnly
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException
import java.time.LocalDateTime

class MoodDiaryActivity : AppCompatActivity() {

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
        val currentMonth = current.month.value

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


        val db = MoodDatabase.getDatabase(this)
        val dao = db.getDao()

        for ((num, day) in daysBackgroundList.withIndex()) {
            val childCount = day.childCount
            for ( i in 0..childCount ) {
                val child = day.getChildAt(i)
                if ( child is TextView && child.text.isDigitsOnly()) {
                    child.text = dayRange[num].toString()
                }
                if ( child is ImageView ) {
                    if ( dao.getMonth(currentMonth).moodDayArray[dayRange[num]-1] == -1 )
                        child.setImageDrawable(null)
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

        val okayButton: AppCompatButton = findViewById(R.id.okButtonDiary)
        val backButton: ImageButton = findViewById(R.id.backButtonDiary1)

        calendarBackground()
        okayButton.isEnabled = false
        okayButton.setBackgroundResource(R.drawable.button_disabled)

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
                okayButton.setBackgroundResource(R.drawable.button_login_2)
                okayButton.isEnabled = true
                for ( x in cardsList )
                    x.cardElevation = 10F
                card.cardElevation = 40F
                when ( card ) {
                    cardsList[0] -> emotionalValue = HAPPY
                    cardsList[1] -> emotionalValue = EXCITED
                    cardsList[2] -> emotionalValue = ENERGETIC
                    cardsList[3] -> emotionalValue = PEACEFUL
                    cardsList[4] -> emotionalValue = EXHAUSTED
                    cardsList[5] -> emotionalValue = ANGRY
                    cardsList[6] -> emotionalValue = CRYING
                }
            }
        }

        backButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        okayButton.setOnClickListener{
            val dateData = LocalDateTime.now()
            val db = MoodDatabase.getDatabase(this)
            val dao = db.getDao()
            var temp = dao.getMonth(dateData.monthValue)
            try {
                temp.moodDayArray[dateData.dayOfMonth-1] = emotionalValue
            } catch (e: NullPointerException ) {
                temp = MoodMonth(dateData.monthValue).apply {
                    moodDayArray[dateData.dayOfMonth-1] = emotionalValue
                }
            }

            dao.insert(temp)
            startActivity(Intent(this, MoodDiaryResultActivity::class.java))
        }
    }

    companion object {
        const val HAPPY = 6
        const val EXCITED = 5
        const val ENERGETIC = 4
        const val PEACEFUL = 3
        const val EXHAUSTED = 2
        const val ANGRY = 1
        const val CRYING = 0
    }
}