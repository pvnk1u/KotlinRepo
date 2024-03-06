package chapter8

data class PersonObj(val name:String,val age: Int)

val people = listOf(PersonObj("Alice",29), PersonObj("Bob",31))

fun lookForAlice(people: List<PersonObj>){
    for (person in people){
        if (person.name == "Alice"){
            println("Found!")
            return
        }
    }
    println("Alice is not found!")
}

fun lookForAliceWithForEach(people: List<PersonObj>){
    people.forEach {
        if (it.name == "Alice"){
            println("Found!")
            return
        }
    }
    println("Alice is not found!")
}

/**
 * 局部返回
 */
fun lookForAliceLocalReturn(people: List<PersonObj>){
    people.forEach label@{
        if (it.name == "Alice") return@label
    }
    println("Alice might be somewhere")
}

/**
 * 使用forEach作为标签局部返回
 */
fun lookForAliceLocalReturn2(people: List<PersonObj>){
    people.forEach {
        if (it.name == "Alice") return@forEach
    }
    println("Alice might be somewhere")
}

/**
 * 在匿名函数中使用return
 */
fun lookForAliceWithAnonymousFunction(people: List<PersonObj>){
    people.forEach(fun (person){
        if (person.name == "Alice") return
        println("${person.name} is not Alice")
    })
}

fun main() {
    lookForAliceWithForEach(people)
    // Found!

    lookForAliceLocalReturn(people)
    // Alice might be somewhere
}
