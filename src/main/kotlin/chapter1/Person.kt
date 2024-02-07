package chapter1

// 数据类，data class声明，第二个参数val age:Int? = null表示参数是Int类型的，在调用时可不传（默认为null）
data class Person (val name:String,
    val age:Int? = null)

fun main(args:Array<String>){
    val persons = listOf(Person("Alice"),
                        Person("Bob", age = 29)
    )

    // 根据Person对象列表中的age属性进行排序，如果age为null，则默认当作0处理
    val oldest = persons.maxBy { it.age ?:0 }
    // 字符串模板
    println("The oldest is: $oldest")
}