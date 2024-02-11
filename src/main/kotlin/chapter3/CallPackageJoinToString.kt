package chapter3

import chapter3.strings.joinToStringWithKt

fun main(args: Array<String>){
    val list = listOf(1,2,3)
    // 然后手动在调用时指定全部参数
    println(joinToStringWithKt(list,",","(",")"))
}