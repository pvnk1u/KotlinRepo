package chapter4.constrcutor

class User3(val nickname:String,
                                val isSubscribed: Boolean = true) { // 为构造方法参数提供一个默认值
}

fun main(args:Array<String>){
    val alice = User3("Alice")
    println(alice.isSubscribed)
    // true
    val bob = User3("Bob",false)
    println(bob.isSubscribed)
    // false
    val carol = User3("Carol",false)
    println(carol.isSubscribed)
    // false
}