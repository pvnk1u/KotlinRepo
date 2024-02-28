package chapter6

fun strLenSafe(s: String?): Int =
    if (s != null) s.length else 0

fun main(args:Array<String>){
    val  x:String? = null
    println(strLenSafe(x))
    // 0
    println(strLenSafe("abd"))
    // 3
}