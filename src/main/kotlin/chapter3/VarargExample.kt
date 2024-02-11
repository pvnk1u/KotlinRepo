package chapter3

fun main(args: Array<String>){
    val newArgs: Array<String> = arrayOf("one","two","three")
    val list = listOf("args: ",*newArgs)
    println(list)
}