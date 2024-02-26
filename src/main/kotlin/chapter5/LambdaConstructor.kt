package chapter5

data class LambdaConstructor(val name:String,val age:Int) {
}

fun main(args:Array<String>){
    val createObj = ::LambdaConstructor
    val p = createObj("Alice",29)
    println(p)
}