package chapter5

fun main(args:Array<String>){
    fun salute() = println("Salute!")
    // 引用顶层函数
    run(::salute)
    // Salute!
}