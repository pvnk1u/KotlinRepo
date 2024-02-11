package chapter3

import java.lang.StringBuilder

fun <T> joinToStringWithDefaultPara(
    collection: Collection<T>,
    separator: String= ",",
    prefix: String = "",
    postfix: String = ""
):String{
    val result = StringBuilder(prefix)
    for ((index,element) in collection.withIndex()){
        if (index > 0 ) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun main(args: Array<String>){
    val list = listOf(1,2,3)
    // 然后手动在调用时指定全部参数
    println(joinToStringWithDefaultPara(list,",","(",")"))
    // 在调用时只指定需要的参数，剩下的参数使用默认值
    println(joinToStringWithDefaultPara(list))
    println(joinToStringWithDefaultPara(list,";"))
}