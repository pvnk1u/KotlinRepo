package chapter7

data class Point(val x:Int,val y : Int)

data class Rectangle(val upperLeft:Point,val lowerRight:Point)

operator fun Rectangle.contains(p:Point):Boolean{
    // 构建一个区间，检查坐标x是否属于这个区间
    // 使用until函数来构建一个开区间
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until  lowerRight.y
}


fun main() {
    val rect = Rectangle(Point(10,20),Point(50,50))
    println(Point(20,30) in rect)
    // true
    println(Point(5,5) in rect)
    // false
}