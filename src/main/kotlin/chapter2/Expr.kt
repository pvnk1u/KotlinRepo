package chapter2

import java.lang.IllegalArgumentException

interface Expr

//  简单的值对象类，只有一个属性value，实现了Expr接口
class Num(val value:Int) : Expr

// Sum运算的实参可以是任何Expr:Num或者另一个Sum
class Sum(val left:Expr,val right:Expr) : Expr


fun eval(e:Expr):Int{
    if (e is Num){
        // 显式地转换成类型Num是多余的
        val n = e as Num
        return n.value
    }
    if (e is Sum){
        // 变量e被智能地转换了类型
        return eval(e.right) + eval(e.left)
    }
    throw IllegalArgumentException("Unknown expression")
}

fun main(args: Array<String>){
    println(eval(Sum(Sum(Num(1),Num(2)),Num(4))))
}