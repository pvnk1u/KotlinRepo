package chapter9

fun printContents(list:List<Any>){
    println(list.joinToString())
}

fun main(){
    printContents(listOf("abc","bac"))
    // abc,bac
}