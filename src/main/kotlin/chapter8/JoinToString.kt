package chapter8


fun <T> Collection<T>.joinToString(
    separator: String = ", ",
    prefix: String = "",
    postfix:String = ""
): String{
    val result = StringBuilder(prefix)

    for ((index,element) in this.withIndex()){
        if (index > 0) result.append(separator)
        // 使用默认的toString方法将对象转换为字符串
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}

/**
 * lambda作为函数形参
 */
fun <T> Collection<T>.joinToStringWithLambda(
    separator:String = ",",
    prefix:String = "",
    postfix:String = "",
    transform: (T) -> String = {it.toString()}
):String{
    val result = StringBuilder(prefix)

    for ((index,element) in this.withIndex()){
        if (index > 0) result.append(separator)
        result.append(transform(element))
    }

    result.append(postfix)
    return result.toString()
}

/**
 * 可空Lambda作为函数形参
 */
fun <T> Collection<T>.joinToStringWithNullableLambda(
    separator:String = ",",
    prefix:String = "",
    postfix:String = "",
    transform: ((T) -> String)? = null    // 声明一个函数类型的可空参数
):String{
    val result = StringBuilder(prefix)

    for ((index,element) in this.withIndex()){
        if (index > 0) result.append(separator)
        // 使用安全调用语法调用函数
        val str = transform?.invoke(element)
            ?: element.toString()       // 使用Elvis运算符处理回调没有被指定的情况
        result.append(str)
    }

    result.append(postfix)
    return result.toString()
}

fun main() {
    val letters = listOf("Alpha","Beta")
    println(letters.joinToString())
    // Alpha,Beta
    println(letters.joinToStringWithLambda { it.lowercase() })
    // alpha,beta
    println(letters.joinToString(separator = "! ", postfix = "! ", transform = {it.uppercase()}))
    // ALPHA! BETA!
}