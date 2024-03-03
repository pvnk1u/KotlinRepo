package chapter7

data class PointUnaryMinus(val x:Int,val y:Int){

}

// 一元运算符，无参数
operator fun PointUnaryMinus.unaryMinus(): PointUnaryMinus{
    // 坐标取反，然后返回
    return PointUnaryMinus(-x,-y)
}

fun main() {
    val p = PointUnaryMinus(10,20)
    println(-p)
    // PointUnaryMinus(x=-10, y=-20)
}
