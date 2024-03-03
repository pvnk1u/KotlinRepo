package chapter7

fun printEntries(map:Map<String,String>){
    // 在in循环中用解构声明
    for((key,value) in map){
        println("$key -> $value")
    }
}

fun main() {
    val map = mapOf("Oracle" to "Java","Jetbrains" to "kotlin")
    printEntries(map)
    // Oracle -> Java
    // Jetbrains -> kotlin
}