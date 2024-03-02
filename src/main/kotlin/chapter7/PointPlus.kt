package chapter7

data class PointPlus(val x:Int,val y:Int){

    // 定义一个名为plus的方法
    operator fun plus(other:PointPlus):PointPlus{
        // 坐标分别相加，然后返回一个新的点
        return PointPlus(x + other.x,y + other.y)
    }
}

fun main() {
    val p1 = PointPlus(10,20)
    val p2 = PointPlus(30,40)
    // 通过使用+号来调用plus方法
    println(p1 + p2)
    // PointPlus(x=40,y=60)
}