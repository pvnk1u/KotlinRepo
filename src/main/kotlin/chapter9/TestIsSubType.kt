package chapter9

fun test(i: Int){
    // 编译通过，因为Int是Number的子类型
    val n:Number = i

    fun f(s:String){ /** **/}
    // 不能编译，因为Int不是String的子类型
    f(i)
}