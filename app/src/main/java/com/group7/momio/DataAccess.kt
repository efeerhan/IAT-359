package com.group7.momio

import androidx.room.*

@Dao
interface DataAccess {

    @Query("SELECT * FROM '2022' WHERE month = :month")
    fun getMonth(month: Int) : MoodMonth

    @Query("SELECT * FROM '2022'")
    fun getAllMonths(): List<MoodMonth>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(month: MoodMonth)
}