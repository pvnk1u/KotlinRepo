package chapter4.interfaceAndProp

class UserWithSetter(val name:String) {
    var address: String = "unspecified"
        set(value: String){
            println("""
                Address was changed for $name:
                "$field"－>"$value".""".trimIndent()) // 使用field关键字读取值
            // 使用field关键字更新值
            field = value
        }
        get(){
            return field;
        }
}

fun main(args:Array<String>){
    val user = UserWithSetter("Alice")
    user.address = "Elsenheimerstrasse 47, 8068 7 Muenchen"
    /*
    Address was changed for Alice:
    "unspecified"－>"Elsenheimerstrasse 47, 8068 7 Muenchen".
    * */
    println("new value: ${user.address}")
}