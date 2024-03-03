package chapter7

import java.time.LocalDate

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
    // 这个对象实现了遍历LocalDate元素的Iterator
    object : Iterator<LocalDate>{
        var current = start

        override fun hasNext() =
            // 这里日期用到了compareTo约定
            current <= endInclusive

        // 在修改前返回当前日期作为结果
        override fun next() = current.apply {
            // 把当前日期增加一天
            current = plusDays(1)
        }

    }

fun main() {
    val newYear = LocalDate.ofYearDay(2017,1)
    val daysOff = newYear.minusDays(1)..newYear
    // 对应的iterator函数实现后，遍历daysOff
    for (dayOff in daysOff){
        println(dayOff)
    }
    //    2016-12-31
    //    2017-01-01
}