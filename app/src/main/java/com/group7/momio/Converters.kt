package com.group7.momio

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromArray(value: Array<Int>): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toArray(value: String): Array<Int> {
        return Gson().fromJson(value, Array<Int>::class.java)
    }
}