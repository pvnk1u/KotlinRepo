package chapter2

fun recognize(c: Char) = when(c){
    in '0'..'9' -> "It`s a digit！"
    in 'a'..'z', in 'A'..'Z' -> "It`s a letter!"
    else -> "I dont know..."
}

fun main(args: Array<String>){
    println(recognize('8'))
}