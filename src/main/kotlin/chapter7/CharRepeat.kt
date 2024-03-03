package chapter7

// 多次重复单个字符来创建字符串
operator fun Char.times(count: Int):String{
    return toString().repeat(count)
}

fun main() {
    println('a' * 3)
    // aaa
}