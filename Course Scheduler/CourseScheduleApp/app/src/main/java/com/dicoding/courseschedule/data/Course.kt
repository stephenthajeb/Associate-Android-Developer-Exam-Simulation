package com.dicoding.courseschedule.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO 1 : Define a local database table using the schema in app/schema/course.json

@Entity(tableName="course")
data class Course(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    @NonNull
    val id: Int = 0,

    @ColumnInfo(name="courseName")
    @NonNull
    val courseName: String,

    @ColumnInfo(name="day")
    @NonNull
    val day: Int,

    @ColumnInfo(name="startTime")
    @NonNull
    val startTime: String,

    @ColumnInfo(name="endTime")
    @NonNull
    val endTime: String,

    @ColumnInfo(name="lecturer")
    @NonNull
    val lecturer: String,

    @ColumnInfo(name="note")
    @NonNull
    val note: String
)
