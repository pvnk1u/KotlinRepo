package chapter2

import java.util.Random

class Rectangle(val height:Int,val width:Int){
    val isSquare: Boolean
        get(){
            return height == width
        }

}

fun createRandomRectangle(): Rectangle{
    val random = Random()
    return Rectangle(random.nextInt(),random.nextInt())
}


fun main(args: Array<String>){
    val rectangle = Rectangle(41,43)
    println(rectangle.isSquare)
}


