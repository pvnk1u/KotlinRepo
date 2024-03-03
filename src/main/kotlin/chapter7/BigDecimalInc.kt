package chapter7

import java.math.BigDecimal

operator fun BigDecimal.inc() = this + BigDecimal.ONE

fun main() {
    var bd = BigDecimal.ZERO
    println(bd++)  //在println语句执行后增加
    // 0
    println(++bd)  // 在println语句执行前增加
    // 2
}