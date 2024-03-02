package chapter7

data class PointTimes(val x:Int,val y:Int){


}

operator fun PointTimes.times(scale: Double): PointTimes{
    return PointTimes((x * scale).toInt(),(y * scale).toInt())
}

fun main() {
    val p = PointTimes(10,20)
    println(p * 1.5)
    // PointTimes(x,15,y=30)
}


