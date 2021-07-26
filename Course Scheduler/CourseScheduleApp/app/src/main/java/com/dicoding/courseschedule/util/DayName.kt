package com.dicoding.courseschedule.util

import java.util.*

enum class DayName(val value: String) {

    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    companion object {
        fun getByNumber(dayNumber: Int) = when (dayNumber) {
            Calendar.MONDAY -> MONDAY.value
            Calendar.TUESDAY -> TUESDAY.value
            Calendar.WEDNESDAY -> WEDNESDAY.value
            Calendar.THURSDAY -> THURSDAY.value
            Calendar.FRIDAY -> FRIDAY.value
            Calendar.SATURDAY -> SATURDAY.value
            Calendar.SUNDAY -> SUNDAY.value
            else -> MONDAY.value
        }

        fun getByName(dayName: String) = when(dayName){
            "Monday" -> Calendar.MONDAY
            "Tuesday" -> Calendar.TUESDAY
            "Wednesday" -> Calendar.WEDNESDAY
            "Thursday" -> Calendar.THURSDAY
            "Friday" -> Calendar.FRIDAY
            "Saturday" -> Calendar.SATURDAY
             else -> Calendar.SUNDAY
        }
    }
}