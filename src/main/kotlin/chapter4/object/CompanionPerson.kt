package chapter4.`object`

class CompanionPerson(val name:String) {

    companion object Loader{
        fun fromJson(jsonText:String):CompanionPerson {
            return CompanionPerson("")
        }
    }
}

fun main(args: Array<String>){
    val	person = CompanionPerson.Loader.fromJson("{name:'Dmitry'}")
    println(person.name)
    // Dmitry
    // 可以通过不加Loader的方式来调用fromJSON
    val	person2 = CompanionPerson.fromJson("{name:'Dmitry'}")
    println(person.name)
    // Dmitry
}