package chapter6

fun verifyUserInput(input :String?){
    // 这里不需要安全调用
    if (input.isNullOrBlank()){
        println("Please fill in the required fields")
    }
}

fun main() {
    verifyUserInput(" ")
    // Please fill in the required fields
    verifyUserInput(null)  // 这里传递null进函数里，函数里执行null对象的isNullOrBlank并不会导致任何异常
    // Please fill in the required fields
}