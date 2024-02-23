package chapter4.`object`

interface JSONFactory<T> {

    fun fromJSON(jsonText:String): T
}


class Person(val name:String){
    companion object : JSONFactory<Person>{

        /**
         * 实现接口的伴生对象
         */
        override fun fromJSON(jsonText: String): Person {
            return Person("")
        }
    }
}

fun <T> loadFromJSON(factory:JSONFactory<T>):T{
    return factory.fromJSON("")
}

fun main(args:Array<String>){
    loadFromJSON(Person)
}