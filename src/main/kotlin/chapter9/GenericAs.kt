package chapter9

import java.lang.IllegalArgumentException

fun printSum(c: Collection<*>){
    // 这里会有警告：Unchecked cast: List<*> to List<Int>
    val intList = c as? List<Int>
        ?: throw IllegalArgumentException("List is expected")
    println(intList.sum())
}

fun main() {
    printSum(listOf(1,2,3))
    // 6
}