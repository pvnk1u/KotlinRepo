package chapter7

data class MutablePoint(var x:Int,var y : Int)

// 定义一个名为set的运算符函数
operator fun MutablePoint.set(index:Int,value:Int){
    when(index){
        // 根据给出的index修改对应的坐标
        0-> x = value
        1-> y = value
        else ->
            throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

fun main() {
    val p = MutablePoint(10,20)
    p[1] = 42
    println(p)
    // MutablePoint(x=10, y=42)
}
