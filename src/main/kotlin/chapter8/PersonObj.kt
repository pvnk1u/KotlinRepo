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

fun main() {
    lookForAliceWithForEach(people)
    // Found!
}
