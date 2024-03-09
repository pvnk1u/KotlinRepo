package chapter9

import java.util.Random

fun main() {
    val list: MutableList<Any?> = mutableListOf('a',1,"qwe")
    val chars = mutableListOf('a','b','c')
    // MutableList<*>和MutableList<Any?>不一样
    val unknownElements: MutableList<*> = if (Random().nextBoolean()) list else chars

    // 编译器禁止调用这个方法
    // unknownElements.add(42)
    // Error: Out-projected type 'MutableList<*>' prohibits the use of 'fun add(element: E): Boolean'
    // 读取元素是安全的：first()返回一个类型为Any?的元素
    println(unknownElements.first())
    // a
}