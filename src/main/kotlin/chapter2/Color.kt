package chapter2

import chapter2.Color.*

enum class Color(
    // 声明枚举常量的属性
    val r:Int,val g:Int,val b:Int
){
    RED(255,0,0),ORANGE(255,165,0),
    YELLOW(255,255,0),GREEN(0,255,0),BLUE(0,0,255),
    INDIGO(75,0,130),VIOLET(238,130,238);

    // 枚举类中的方法
    fun rgb() = (r*256 +g) * 256 +b
}

fun getWarmth(color: Color) = when(color){
    RED,ORANGE,YELLOW -> "warm"
    GREEN-> "neutral"
    BLUE,INDIGO,VIOLET -> "cold"
}


fun getMnemonic(color: Color)=
    when(color){
        RED -> "Richard"
        ORANGE -> "Of"
        YELLOW -> "York"
        GREEN -> "Gave"
        BLUE -> "Battle"
        INDIGO -> "In"
        VIOLET -> "Vain"
}





fun mix(c1: Color, c2: Color) =
    when (setOf(c1, c2)) {//when表达式的参数可以是任何实例，用来被分支条件检查
        setOf(RED, YELLOW) -> ORANGE//枚举可以混合的颜色对
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLET) -> INDIGO
        else -> throw Exception("Dirty color")//执行这个，如果没有分支可以匹配
    }

fun mixOptimized(c1:Color,c2: Color) =
    when{
        (c1 == RED && c2 == YELLOW) ||
                (c1 == YELLOW && c2 == RED) -> ORANGE

        (c1 == YELLOW && c2 == BLUE) ||
                (c1 == BLUE && c2 == YELLOW) -> GREEN

        (c1 == BLUE && c2 == VIOLET) ||
                (c1 == VIOLET && c2 == BLUE) -> INDIGO
        else -> throw Exception("Dirty color")
    }

fun main(args: Array<String>){
    println(getMnemonic(BLUE))
    println(getWarmth(ORANGE))
    println(mix(RED,YELLOW))
    println(mixOptimized(BLUE,YELLOW))
}