package chapter3

fun main(args: Array<String>){
    // 显式地创建一个正则表达式
    println("12.345-6.A".split("\\.|-".toRegex()))
}