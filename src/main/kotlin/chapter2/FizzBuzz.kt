package chapter2

fun fizzBuzz(i: Int) = when{
    i % 15 == 0 ->"FizzBuzz"
    i % 3 == 0 ->"Fizz"
    i % 5 == 0 ->"Buzz"
    else -> "$i"
}

fun main(args: Array<String>){
    // 从1数到100，包括1和100
    for(i in 1..100){
        println(fizzBuzz(i))
    }
    // 从1数到100，不包括100
    for(i in 1 until  100){
        println(fizzBuzz(i))
    }
    // 从1数到100，不包括100
    for(i in 1 ..< 100){
        println(fizzBuzz(i))
    }
    // 从100数到1，每次减2，包括100和1
    for(i in 100 downTo 1 step 2){
        println(fizzBuzz(i))
    }
}