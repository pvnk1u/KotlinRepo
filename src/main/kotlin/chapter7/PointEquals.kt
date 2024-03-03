package chapter7

data class PointEquals(val x:Int,val y:Int){

    // 重写在Any中定义的方法
    override fun equals(other: Any?): Boolean {
        // 检查参数是否与this是同一个对象
        if (other === this) return true
        // 检查参数类型
        if (other !is PointEquals) return false
        // 智能转换为PointEquals来访问x、y属性
        return other.x == x && other.y == y
    }
}

fun main() {
    println(PointEquals(10,20) == PointEquals(10,20))
    // true
    println(PointEquals(10,20) != PointEquals(5,5))
    // true
    println(null == PointEquals(1,2))
    // false
}


