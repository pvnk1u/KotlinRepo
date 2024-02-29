package chapter6

fun printAllCaps(s:String?){
    val allCaps : String? = s?.uppercase()
    // allCaps可能是null
    println(allCaps)
}

fun main() {
    printAllCaps("abc")
    // ABC
    printAllCaps(null)
    // null
}