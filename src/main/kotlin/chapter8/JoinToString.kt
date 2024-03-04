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