package com.example.calendartdd

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CalendarTest {

    lateinit var calendarSourceImpl: CalendarSourceImpl

    @Before
    fun init() {
        calendarSourceImpl = CalendarSourceImpl()
        calendarSourceImpl.initCalendar()
    }

}