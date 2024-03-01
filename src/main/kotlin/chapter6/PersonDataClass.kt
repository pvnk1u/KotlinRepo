package chapter6

data class PersonDataClass(val name:String,
                            val age: Int? = null){
    fun isOlderThan(other: PersonDataClass):Boolean?{
        if (age == null || other.age == null)
            return null
        return age > other.age
    }
}


fun main() {
    println(PersonDataClass("Sam",35).isOlderThan(PersonDataClass("Amy",42)))
    // false
    println(PersonDataClass("Sam",35).isOlderThan(PersonDataClass("Jane")))
    // null
}