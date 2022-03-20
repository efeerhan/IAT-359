package com.group7.momio

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "2022")
data class MoodMonth(
    @PrimaryKey val month: Int,
    @ColumnInfo(name = "d1") var d1: Int = -1,
    @ColumnInfo(name = "d2") var d2: Int = -1,
    @ColumnInfo(name = "d3") var d3: Int = -1,
    @ColumnInfo(name = "d4") var d4: Int = -1,
    @ColumnInfo(name = "d5") var d5: Int = -1,
    @ColumnInfo(name = "d6") var d6: Int = -1,
    @ColumnInfo(name = "d7") var d7: Int = -1,
    @ColumnInfo(name = "d8") var d8: Int = -1,
    @ColumnInfo(name = "d9") var d9: Int = -1,
    @ColumnInfo(name = "d10") var d10: Int = -1,
    @ColumnInfo(name = "d11") var d11: Int = -1,
    @ColumnInfo(name = "d12") var d12: Int = -1,
    @ColumnInfo(name = "d13") var d13: Int = -1,
    @ColumnInfo(name = "d14") var d14: Int = -1,
    @ColumnInfo(name = "d15") var d15: Int = -1,
    @ColumnInfo(name = "d16") var d16: Int = -1,
    @ColumnInfo(name = "d17") var d17: Int = -1,
    @ColumnInfo(name = "d18") var d18: Int = -1,
    @ColumnInfo(name = "d19") var d19: Int = -1,
    @ColumnInfo(name = "d20") var d20: Int = -1,
    @ColumnInfo(name = "d21") var d21: Int = -1,
    @ColumnInfo(name = "d22") var d22: Int = -1,
    @ColumnInfo(name = "d23") var d23: Int = -1,
    @ColumnInfo(name = "d24") var d24: Int = -1,
    @ColumnInfo(name = "d25") var d25: Int = -1,
    @ColumnInfo(name = "d26") var d26: Int = -1,
    @ColumnInfo(name = "d27") var d27: Int = -1,
    @ColumnInfo(name = "d28") var d28: Int = -1,
    @ColumnInfo(name = "d29") var d29: Int = -1,
    @ColumnInfo(name = "d30") var d30: Int = -1,
    @ColumnInfo(name = "d31") var d31: Int = -1
)
