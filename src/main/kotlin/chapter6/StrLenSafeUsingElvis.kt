package chapter6

fun strLenSafeUsingElvis(s:String?): Int =  s?.length ?:0

fun main(){
    println(strLenSafeUsingElvis("abc"))
    // 3
    println(strLenSafeUsingElvis(null))
    // 0
}