package ru.skillbranch.devintensive.extensions

import java.lang.UnsupportedOperationException
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60L
const val HOUR = MINUTE * 60L
const val DAY = HOUR * 24L


fun Date.humanizeDiff(date: Date = Date()): String {
    return when (val diff = date.time - this.time) {
        in Long.MIN_VALUE .. -360*DAY -> "более чем через год"
        in -360*DAY .. -26*HOUR -> "через " + (-diff/DAY).toString() + " " +
                TimeUnits.ending(-diff/DAY, TimeUnits.DAY)
        in -26*HOUR .. -22*HOUR -> "через день"
        in -22*HOUR .. -75*MINUTE -> "через " + (-diff/HOUR).toString() + " " +
                TimeUnits.ending(-diff/HOUR, TimeUnits.HOUR)
        in -75*MINUTE .. -45*MINUTE -> "через час"
        in  -45*MINUTE .. -75*SECOND-> "через " + (-diff/MINUTE).toString() + " " +
                TimeUnits.ending(-diff/MINUTE, TimeUnits.MINUTE)
        in -75*SECOND .. -45*SECOND -> "через минуту"
        in -45*SECOND .. -1*SECOND -> "через несколько секунд"
        in -1*SECOND .. 1*SECOND-> "только что"
        in 1*SECOND .. 45*SECOND -> "несколько секунд назад"
        in 45*SECOND .. 75*SECOND -> "минуту назад"
        in 75*SECOND .. 45*MINUTE -> (diff/MINUTE).toString() + " " +
                TimeUnits.ending(diff/MINUTE, TimeUnits.MINUTE) + " назад"
        in 45*MINUTE .. 75*MINUTE -> "час назад"
        in 75*MINUTE .. 22*HOUR -> (diff/HOUR).toString() + " " +
                TimeUnits.ending(diff/HOUR, TimeUnits.HOUR) + " назад"
        in 22*HOUR .. 26*HOUR -> "день назад"
        in 26*HOUR .. 360*DAY -> (diff/DAY).toString() + " " +
                TimeUnits.ending(diff/DAY, TimeUnits.DAY) + " назад"
        in 360*DAY .. Long.MAX_VALUE -> "более года назад"
        else -> throw UnsupportedOperationException("Something went wrong with difference $diff")
    }
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    return SimpleDateFormat(pattern, Locale("ru")).format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(amount: Long) = "$amount ${ending(amount, this)}"

    companion object TU{
        fun ending(amount:Long, tu: TimeUnits):String {
            return when(tu){
                SECOND -> when{
                    (amount % 10 == 1L) && (amount % 100 != 11L)  -> "секунду"
                    (amount % 10 in 2..4) && (amount % 100 !in 12..14) -> "секунды"
                    else -> "секунд"
                }
                MINUTE -> when{
                    (amount % 10 == 1L) && (amount % 100 != 11L)  -> "минуту"
                    (amount % 10 in 2..4) && (amount % 100 !in 12..14) -> "минуты"
                    else -> "минут"
                }
                HOUR -> when {
                    (amount % 10 == 1L) && (amount % 100 != 11L) -> "час"
                    (amount % 10 in 2..4) && (amount % 100 !in 12..14) -> "часа"
                    else -> "часов"
                }
                DAY -> when {
                    (amount % 10 == 1L) && (amount % 100 != 11L) -> "день"
                    (amount % 10 in 2..4) && (amount % 100 !in 12..14) -> "дня"
                    else -> "дней"
                }
            }
        }
    }
}