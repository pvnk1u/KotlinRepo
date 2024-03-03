package chapter7

data class PointGetSet(val x: Int,val y:Int)


// 定义一个名为get的运算符函数
operator fun PointGetSet.get(index:Int):Int{
    return when(index){
        // 根据给出的index返回对应的坐标
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}



fun main() {
    val p = PointGetSet(10,20)
    println(p[1])
    // 20
}