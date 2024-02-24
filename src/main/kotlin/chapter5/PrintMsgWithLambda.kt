package chapter5

fun printMessageWithPrefix(messages: Collection<String>,prefix: String){
    // 接收lambda作为实参，指定对每个元素的操作
    messages.forEach{
        // 在lambda中访问prefix参数
        println("$prefix $it")
    }
}



fun main(args:Array<String>){
    val erros = listOf("403 Forbidden","404 Not Found")
    printMessageWithPrefix(erros,"Error:")
    // Error: 403 Forbidden
    // Error: 404 Not Found
}