package com.group7.momio

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "2022")
data class MoodMonth(
    @PrimaryKey val month: Int,
    @ColumnInfo(name = "array") var moodDayArray: Array<Int> = arrayOf(-1,-1,-1,-1,-1,-1,-1
        ,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MoodMonth

        if (month != other.month) return false
        if (!moodDayArray.contentEquals(other.moodDayArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = month
        result = 31 * result + moodDayArray.contentHashCode()
        return result
    }
}
