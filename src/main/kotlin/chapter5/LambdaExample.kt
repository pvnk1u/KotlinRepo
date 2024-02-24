package chapter5

fun main(args:Array<String>){
    val sum = {x:Int,y:Int ->
        println("Computing the sum of $x and $y...")
        x + y
    }

    println(sum(1,2))
    // Computing the sum of 1 and 2...
    // 3
}