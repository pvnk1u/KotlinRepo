package chapter8

fun twoAndThree(operation: (Int,Int) -> Int){
    // 调用函数类型的参数
    val result = operation(2,3)
    println("The Result is $result")
}

fun main() {
    twoAndThree{a,b -> a + b }
    // The result is 5
    twoAndThree{a,b -> a * b}
    // The Result is 6
}