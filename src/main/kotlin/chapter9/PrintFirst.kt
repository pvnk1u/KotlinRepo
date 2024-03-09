package chapter9

// 每一种列表都是可能的实参
fun printFirst(list:List<*>){
    // isNotEmpty()没有使用泛型类型参数
    if (list.isNotEmpty()){
        // first()现在返回的是Any?，但是这里足够了
        println(list.first())
    }
}

fun main() {
    printFirst(listOf("Svetlana","Dmitry"))
    // Svetlana
}