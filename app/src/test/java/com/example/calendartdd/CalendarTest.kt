package com.example.calendartdd

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class CalendarTest {

    lateinit var calendarSourceImpl: CalendarSourceImpl

    @Before
    fun init() {
        calendarSourceImpl = CalendarSourceImpl()
        calendarSourceImpl.initCalendar()
    }

    @Test
    fun initCalendarInstance() {
        Assert.assertNotNull(calendarSourceImpl.calendar)
    }

    @Test
    fun `1_initCalendar()를 호출하면 calendar는 초기화되고 null에 대해 안전하다`() {
        calendarSourceImpl.initCalendar()
        Assert.assertNotNull(calendarSourceImpl.calendar)
    }

    @Test
    fun `2_setYear()는 입력받은 년을 설정한다`() {
        calendarSourceImpl.setYear(2020)

        Assert.assertEquals(2020, calendarSourceImpl.getYear())
    }

    @Test
    fun `3_setMonth()는 입력받은 월을 설정한다`() {
        calendarSourceImpl.setMonth(12)

        Assert.assertEquals(12, calendarSourceImpl.getMonth())
    }

    @Test
    fun `4_setDay()는 입력받은 일을 설정한다`() {
        calendarSourceImpl.setDay(30)

        Assert.assertEquals(30, calendarSourceImpl.getDay())
    }

    // 테스트 케이스의 SRP 위반
//    @Test
//    fun `5_setYear(), setMonth(), setDay()는 Input값에 정확한 Output을 반환한다`() {
//        calendarSourceImpl.run {
//            setYear(2020)
//            setMonth(12)
//            setDay(30)
//        }
//
//        Assert.assertEquals("2020.12.30", calendarSourceImpl.getCurrentDate())
//    }

    @Test
    fun `5_getMonth()는 1 ~ 12 사이의 값을 반환한다`() {
        val month = calendarSourceImpl.getMonth()

        Assert.assertTrue(month in 1..12)
    }

    @Test
    fun `6_getDay()는 1 ~ 31 사이의 값을 반환한다`() {
        val day = calendarSourceImpl.getDay()

        calendarSourceImpl.getForwardMonth()

        Assert.assertTrue(day in 1..31)
    }

    @Test
    fun `7_getPreviousMonth()는 1 ~ 12 사이의 값을 반환한다`() {
        val month = calendarSourceImpl.getPreviousMonth()

        Assert.assertTrue(month in 1..12)
    }

    @Test
    fun `8_getPreviousMonth()의 값이 1월(0)일 때 0월(-1)이 아닌 12월로 변경된다`() {
        calendarSourceImpl.calendar.set(Calendar.MONTH, 0)

        val month = calendarSourceImpl.getPreviousMonth()

        Assert.assertEquals(month, 12)
    }

    @Test
    fun `9_getPreviousMonth()는 현재 달 -1값과 동일하다`() {
        val currentMonth = calendarSourceImpl.calendar[Calendar.MONTH]
        val previousMonth = calendarSourceImpl.run { getPreviousMonth(); calendar[Calendar.MONTH] }

        Assert.assertEquals(currentMonth - 1, previousMonth)
    }

    @Test
    fun `10_getForwardMonth()는 1 ~ 12 사이의 값을 반환한다`() {
        val month = calendarSourceImpl.getForwardMonth()

        Assert.assertTrue(month in 1..12)
    }

    @Test
    fun `11_getForwardMonth()는 12월 이후 13월이 아닌 1월로 변경된다`() {
        calendarSourceImpl.calendar.set(Calendar.MONTH, 12)

        val month = calendarSourceImpl.getForwardMonth()

        Assert.assertTrue(month == 1)
    }

    @Test
    fun `12_getForwardMonth()는 현재 달 +1값과 동일하다`() {
        val currentMonth = calendarSourceImpl.calendar[Calendar.MONTH]
        val forwardMonth = calendarSourceImpl.run { getForwardMonth(); calendar[Calendar.MONTH] }

        Assert.assertEquals(currentMonth + 1, forwardMonth)
    }

    @Test
    fun `13_getCurrentDate()는 현재 년월일을 정확하게 반환한다`() {
        val currentYear = calendarSourceImpl.getYear().toString()
        val currentMonth = calendarSourceImpl.getMonth().toString()
        val currentDay = calendarSourceImpl.getDay().toString()

        Assert.assertEquals(
            calendarSourceImpl.getCurrentDate(),
            "${currentYear}.${currentMonth}.${currentDay}"
        )
    }

}