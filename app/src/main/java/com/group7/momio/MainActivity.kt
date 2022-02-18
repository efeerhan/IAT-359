package com.group7.momio

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var client: FusedLocationProviderClient

    private var apiID = "30e348a42ca66f4bff3e848a3fb85e6e"
    private var weatherURL: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide() //hide the title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = LocationServices.getFusedLocationProviderClient(this)
        Log.i("lat", "BAAAALSSSSSSS")
        obtainLocation()
    }

    @SuppressLint("MissingPermission")
    private fun obtainLocation() {
        Log.i("lat", "obtainLocation")
        // get the last location
        client.lastLocation
            .addOnSuccessListener { location: Location? ->
                weatherURL = "api.openweathermap.org/data/2.5/weather?lat=" + location?.latitude + "&lon=" + location?.longitude + "&appid=" + apiID
                Log.i("lat", weatherURL)
                getTemp()
            }
    }

    fun getTemp() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = weatherURL
        Log.i("lat", url)

        // Request a string response
        // from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.i("lat", response.toString())

                // get the JSON object
                val obj = JSONObject(response)

                // get the Array from obj of name - "data"
                val arr = obj.getJSONArray("data")
                Log.i("lat obj1", arr.toString())

                // get the JSON object from the
                // array at index position 0
                val obj2 = arr.getJSONObject(0)
                Log.i("lat obj2", obj2.toString())

                // set the temperature and the city
                // name using getString() function
                Log.i("lat", obj2.getString("temp") + " deg Celsius in " + obj2.getString("city_name"))
            },
            // In case of any error
            { Log.i("oops", "That didn't work!") })
        queue.add(stringReq)
    }
}