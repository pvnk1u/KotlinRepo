package chapter9

// 使用了<reified T>之后，可以在运行时检查泛型实参的类型了
inline fun <reified T> isA(value: Any) = value is T

fun main() {
    println(isA<String>("abc"))
    // true
    println(isA<String>(123))
    // false
}