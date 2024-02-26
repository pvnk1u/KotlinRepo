package chapter5

fun main(args: Array<String>){
    val people = listOf(Person("Alice",31),
        Person("Bob",29),Person("Carol",31))
    println(people.groupBy{it.age})
    // {31=[Person(name=Alice, age=31), Person(name=Carol, age=31)], 29=[Person(name=Bob, age=29)]}

    val list = listOf("a","ab","b")
    println(list.groupBy(String::first))
    // {a={a,ab},b={b}}
}