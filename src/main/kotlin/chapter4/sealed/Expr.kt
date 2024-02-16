package chapter4.sealed


sealed class Expr{
    class Num(val value:Int):Expr()
    class Sum(val left:Expr,val right:Expr): Expr()
}

fun eval(e: Expr): Int =
    // when表达式涵盖了所有可能的情况，所以不再需要else分支
    when(e){
        is Expr.Num -> e.value
        is Expr.Sum -> eval(e.right) + eval(e.left)
    }

fun main(args: Array<String>){
    println(eval(Expr.Sum(Expr.Sum(Expr.Num(1), Expr.Num(2)), Expr.Num(4))))
}