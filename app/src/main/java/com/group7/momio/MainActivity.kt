package com.group7.momio

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import org.json.JSONObject
import java.net.URL
import com.squareup.picasso.Picasso
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.AnyChart
import com.anychart.palettes.RangeColors
import java.lang.NullPointerException
import java.lang.reflect.Field
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private val apiID = "30e348a42ca66f4bff3e848a3fb85e6e"
    private val iconURLPrefix = "http://openweathermap.org/img/wn/"
    private val iconURLSuffix = "@2x.png"
    private val city = "surrey,ca"

    private fun getWeatherAPI(){
        val thread = Thread {
            val result = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$apiID").readText(Charsets.UTF_8)
            val jsonObj = JSONObject(result)
            val main = jsonObj.getJSONObject("main")
            val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
            val temp = main.getString("temp").take(1)+"°C"

            val weatherCondition = weather.getString("id")
            val weatherIcon = findViewById<ImageView>(R.id.weatherFloatIcon)

            runOnUiThread {
                when ( weatherCondition.take(1) ) {
                    "2" -> Picasso.get().load("${iconURLPrefix}11d$iconURLSuffix").
                    into(weatherIcon)
                    "3" -> Picasso.get().load("${iconURLPrefix}09d$iconURLSuffix").
                    into(weatherIcon)
                    "5" -> Picasso.get().load("${iconURLPrefix}10d$iconURLSuffix").
                    into(weatherIcon)
                    "6" -> Picasso.get().load("${iconURLPrefix}13d$iconURLSuffix").
                    into(weatherIcon)
                    "7" -> Picasso.get().load("${iconURLPrefix}50d$iconURLSuffix").
                    into(weatherIcon)
                    "8" -> {
                        if ( weatherCondition.last() == '0' ) {
                            Picasso.get().load("${iconURLPrefix}01d$iconURLSuffix")
                                .into(weatherIcon)
                        }
                        else Picasso.get().load("${iconURLPrefix}02d$iconURLSuffix")
                            .into(weatherIcon)
                    }
                }
                findViewById<TextView>(R.id.weatherFloatTemp).text = temp
            }

        }
        thread.start()
    }

    private fun getMonthGraph(dao: DataAccess){
        val currentMonth = LocalDateTime.now().month.value
        val chart = AnyChart.pie()
        val data = mutableListOf<DataEntry>()

        var neutral = 0
        var positive = 0
        var negative = 0

        try {
            val month: MoodMonth = dao.getMonth(currentMonth)
            val monthArray = month.moodDayArray
            for ( num in monthArray ) {
                if ( num == 0 )
                    neutral++
                if (num in 1..3)
                    negative++
                if ( num in 4..6 )
                    positive++
            }
        } catch (e: NullPointerException){
            neutral++
        }


        println("Neutral: $neutral")
        println("Positive: $positive")
        println("Negative: $negative")

        data.add(ValueDataEntry("Positive", positive))
        data.add(ValueDataEntry("Negative", negative))
        data.add(ValueDataEntry("Neutral", neutral))

        chart.data(data)

        val anyChartView = findViewById<AnyChartView>(R.id.any_chart_view)
        val palette = RangeColors.instantiate()
        palette.items("#B5A4E6", "#F1D0EA")
        palette.count(3)
        chart.palette(palette)
        chart.credits().text("Company")
        anyChartView.setChart(chart)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db by lazy { MoodDatabase.getDatabase(this) }
        val dao = db.getDao()

        val moodDiary = findViewById<ImageButton>(R.id.diaryFloatButton)

        getWeatherAPI()

        getMonthGraph(dao)

        moodDiary.setOnClickListener{
            startActivity(Intent(this, MoodDiaryActivity::class.java))
        }

        val settingsButton = findViewById<ImageButton>(R.id.settings)
        val dialog = BottomSheetFragmentActivity()

        settingsButton.setOnClickListener() {
            dialog.show(supportFragmentManager, dialog.tag)
        }

    }
}