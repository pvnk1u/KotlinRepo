package chapter8

fun String.filter(predicate:(Char) -> Boolean):String{
    val sb = StringBuilder()
    for (index in 0 until length){
        val element = get(index)
        // 调用作为参数传递给predicate的函数
        if (predicate(element)) sb.append(element)
    }
    return sb.toString()
}

fun main() {
    // 传递一个lambda作为predicate参数
    println("ab1c".filter { it in 'a'..'z' })
    // abc
}