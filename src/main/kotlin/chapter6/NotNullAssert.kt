package chapter6

fun ignoreNulls(s: String?){
    // 异常指向这一行
    val sNotNull:String = s!!
    println(sNotNull.length)
}

fun main(){
    ignoreNulls(null)
    // java.lang.NullPointerException
}