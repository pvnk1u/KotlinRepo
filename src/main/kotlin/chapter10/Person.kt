package chapter10

data class Person(val name:String,val age:Int)

fun main() {
    val person = Person("Alice",29)
    // println(serialize(person))
    // {"age":29,"name":"Alice"}

    val json = """{"name:"Alice","age":29}"""
    // println(deserialize<Person>(json))
    // Person(name=Alice,age=29)
}
