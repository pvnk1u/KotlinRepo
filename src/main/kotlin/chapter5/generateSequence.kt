package chapter5

fun main(args:Array<String>){
    val natureNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = natureNumbers.takeWhile { it <= 100 }
    println(numbersTo100.sum())
}