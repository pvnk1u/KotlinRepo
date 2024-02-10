package chapter3

fun main(args:Array<String>){
    // 创建set
    val strings = listOf("first","second","fourteenth")
    println(strings.last())
    // fourteenth
    // 创建set
    val numbers = setOf(1,14,2)
    println(numbers.max())
    // 14
}