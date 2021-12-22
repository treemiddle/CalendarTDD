package com.example.calendartdd

import java.util.*


class CalendarSourceImpl : CalendarSource {

    private lateinit var calendar: Calendar

    override fun initCalendar() {
        calendar = Calendar.getInstance()
    }

    override fun setYear(year: Int) {
        if (::calendar.isInitialized.not()) throw NullPointerException("calendar instance is null")

        calendar.set(Calendar.YEAR, year)
    }

    override fun setMonth(month: Int) {
        if (::calendar.isInitialized.not()) throw NullPointerException("calendar instance is null")

        calendar.set(Calendar.MONTH, month.minus(1))
    }

    override fun setDay(day: Int) {
        if (::calendar.isInitialized.not()) throw NullPointerException("calendar instance is null")

        calendar.set(Calendar.DAY_OF_MONTH, day)
    }

    override fun getYear(): Int {
        if (::calendar.isInitialized.not()) throw NullPointerException("calendar instance is null")

        return calendar[Calendar.YEAR]
    }

    override fun getMonth(): Int {
        if (::calendar.isInitialized.not()) throw NullPointerException("calendar instance is null")

        return calendar[Calendar.MONTH].plus(1)
    }

    override fun getDay(): Int {
        if (::calendar.isInitialized.not()) throw NullPointerException("calendar instance is null")

        return calendar[Calendar.DAY_OF_MONTH]
    }

    override fun getPreviousMonth(): Int {
        if (::calendar.isInitialized.not()) throw NullPointerException("calendar instance is null")

        val currentMonth = calendar[Calendar.MONTH]

        if (currentMonth == 0) {
            calendar.set(Calendar.MONTH, 11)
        } else {
            calendar.set(Calendar.MONTH, currentMonth - 1)
        }

        return getMonth()
    }

    override fun getForwardMonth(): Int {
        if (::calendar.isInitialized.not()) throw NullPointerException("calendar instance is null")

        val currentMotnh = calendar[Calendar.MONTH]

        calendar.set(Calendar.MONTH, currentMotnh + 1)

        return calendar[Calendar.MONTH]
    }

    override fun getCurrentDate(): String {
        return calendar.run { "${getYear()}.${getMonth()}.${getDay()}" }
    }

    override fun getFirstDayOfWeek(): Int {
        return calendar.run { this[Calendar.DAY_OF_WEEK] }
    }

    override fun onToday(today: String): Boolean {
        Calendar.getInstance().run {
            val currentYear = this[Calendar.YEAR]
            val currentMonth = this[Calendar.MONTH].plus(1)
            val currentDay = this[Calendar.DAY_OF_MONTH]

            try {
                today.split(".").also {
                    return currentYear == it[0].toInt() &&
                            currentMonth == it[1].toInt() &&
                            currentDay == it[2].toInt()
                }
            } catch (e: Exception) {
                throw Exception("date convert exception: ${e.localizedMessage}")
            }
        }
    }

    override fun onSunday(today: String): Boolean {
        Calendar.getInstance().run {
            try {
                today.split(".").also {
                    this.set(Calendar.YEAR, it[0].toInt())
                    this.set(Calendar.MONTH, it[1].toInt() - 1)
                    this.set(Calendar.DAY_OF_MONTH, it[2].toInt())

                    return this[Calendar.DAY_OF_WEEK] == 1
                }
            } catch (e: Exception) {
                throw Exception("date convert exception: ${e.localizedMessage}")
            }
        }
    }

    override fun onSaturday(today: String): Boolean {
        Calendar.getInstance().run {
            try {
                today.split(".").also {
                    this.set(Calendar.YEAR, it[0].toInt())
                    this.set(Calendar.MONTH, it[1].toInt() - 1)
                    this.set(Calendar.DAY_OF_MONTH, it[2].toInt())

                    return this[Calendar.DAY_OF_WEEK] == 7
                }
            } catch (e: Exception) {
                throw Exception("date convert exception: ${e.localizedMessage}")
            }
        }
    }

    override fun getDayList(): List<CalendarEntity> {
        val lastDayMonth = calendar.getActualMaximum(Calendar.DATE)
        val result = mutableListOf<CalendarEntity>()

        createBlankDayList().run { result.addAll(this) }

        return lastDayMonth.run {
            for (i in 1..this)  {
                CalendarEntity(
                    type = CalendarDayType.DAY,
                    value = i
                ).run {
                    result.add(this)
                }
            }

            result
        }
    }

    override fun createBlankDayList(): List<CalendarEntity> {
        val firstDay = getFirstDayOfWeek()
        val result = mutableListOf<CalendarEntity>()

        for (i in 1 until firstDay) {
            CalendarEntity(
                type = CalendarDayType.BLANK,
                value = 0
            ).run {
                result.add(this)
            }
        }

        return result
    }

}