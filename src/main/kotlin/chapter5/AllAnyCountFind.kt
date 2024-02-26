package chapter5


data class Person(val name:String,val age:Int) {
}

fun main(args: Array<String>){
    val canBeInClub27 = {p:Person -> p.age <= 27}
    val people = listOf(Person("Alice",27),Person("Bob",31))
    println(people.all(canBeInClub27))
    // false
    println(people.any(canBeInClub27))
    // true
    println(people.count(canBeInClub27))
    // 1
    // 如果有多个匹配的元素就返回其中第一个元素，或者返回null，还有一个同义方法firstOrNull
    println(people.find(canBeInClub27))
    // Person(name=Alice,age=27)

    val list = listOf(1,2,3)
    // !否定不明显，这种情况下最好使用any
    println(!list.all { it == 3 })
    // true
    // lambda参数中的条件要取反
    println(list.any{it != 3})
    // true
}