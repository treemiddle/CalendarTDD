package com.example.calendartdd

interface CalendarSource {

    fun initCalendar()

    fun setYear(year: Int)
    fun setMonth(month: Int)
    fun setDay(day: Int)

    fun getYear(): Int
    fun getMonth(): Int
    fun getDay(): Int

    fun getPreviousMonth(): Int
    fun getForwardMonth(): Int

    fun getCurrentDate(): String
    fun getFirstDayOfWeek(): Int

    fun onToday(today: String): Boolean
    fun onSunday(today: String): Boolean
    fun onSaturday(today: String): Boolean

    fun getDayList(): List<CalendarEntity>
    fun createBlankDayList(): List<CalendarEntity>

}