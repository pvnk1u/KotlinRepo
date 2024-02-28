package chapter5

fun alphabet():String{
    val result = StringBuilder()
    for (letter in 'A'..'Z'){
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}

/**
 * 使用with重构
 */
fun alphabetUsingWith():String{
    val result = StringBuilder()
    // 指定接收者的值，代码块中会调用它的方法
    return with(result){
        for (letter in 'A'..'Z'){
            // 通过显式地this来调用接收者值的方法
            this.append(letter)
        }
        append("\nNow I know the alphabet!")
        // 从lambda返回值
        this.toString()
    }
}


/**
 * 再次重构
 */
fun alphabetUsingWith2() = with(StringBuilder()){
    for (letter in 'A'..'Z'){
        // 通过显式地this来调用接收者值的方法
        append(letter)
    }
    append("\nNow I know the alphabet!")
    // 从lambda返回值
    toString()
}

/**
 * 使用apply重构
 */
fun alphabetUsingApply() = StringBuilder().apply {
    for (letter in 'A'..'Z'){
        // 通过显式地this来调用接收者值的方法
        append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()


/**
 * 使用buildString重构
 */
fun alphabetUsingBuildString() = buildString {
    for (letter in 'A'..'Z') {
        // 通过显式地this来调用接收者值的方法
        append(letter)
    }
    append("\nNow I know the alphabet!")
}

fun main(args:Array<String>){
    println(alphabet())
    /*
    ABCDEFGHIJKLMNOPQRSTUVWXYZ
    Now I know the alphabet!
    * */
}