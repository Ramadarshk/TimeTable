package com.example.timetable.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "Slot")
@TypeConverters(Converter::class)
data class DataOfSlot(
    @PrimaryKey()
    val courseId: String ,
    @ColumnInfo(name = "courseName")
    val courseName: String ,
    @ColumnInfo(name = "theory")
    val theory: List<String> ,
    @ColumnInfo(name = "theoryLocation")
    val theoryLocation: String ,
    @ColumnInfo(name = "lab")
    val lab: List<String?> ,
    @ColumnInfo(name = "labLocation")
    val labLocation: String? ,
    @ColumnInfo(name = "isLab")
    val isLab: Boolean ,
)
class Converter{
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}