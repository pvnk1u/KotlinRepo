package chapter5


data class LambdaDataClassPerson(val name:String,val age:Int) {

}

fun findTheOldest(people: List<LambdaDataClassPerson>){
    var maxAge = 0
    var theOldest: LambdaDataClassPerson? = null;
    for (person in people){
        if (person.age > maxAge){
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

fun main(args: Array<String>){
    val people = listOf(LambdaDataClassPerson("Alice",29),LambdaDataClassPerson("Bob",31))
    findTheOldest(people)
    // LambdaDataClassPerson(name=Bob, age=31)
}