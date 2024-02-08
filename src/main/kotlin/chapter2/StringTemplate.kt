package chapter2

/*fun main(args:Array<String>){
    val name = if(args.isNotEmpty()) args[0] else "Kotlin"
    println("Hello,$name!")
}*/

fun main(args: Array<String>){
    println("Hello,${if(args.isNotEmpty()) args[0] else "someone"}")
}
