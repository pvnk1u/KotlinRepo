package chapter3

fun main(args:Array<String>){
    // 创建set
    val set = hashSetOf(1,7,53)
    // 创建list
    val list = arrayListOf(1,7,53)
    // 创建map
    val map = hashMapOf(1 to "one",7 to "seven",53 to "fifty-three")

    // 打印结果
    println(set.javaClass)
    println(list.javaClass)
    println(map.javaClass)
    // 结果
    // class java.util.HashSet
    // class java.util.ArrayList
    // class java.util.HashMap

}

