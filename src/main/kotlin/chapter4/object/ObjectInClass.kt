package chapter4.`object`

data class ObjectInClass(val name:String){
    object NameComparator: Comparator<ObjectInClass>{
        override fun compare(p1: ObjectInClass, p2: ObjectInClass): Int =
            p1.name.compareTo(p2.name)
    }
}


fun main(args: Array<String>){
    val persons = listOf(ObjectInClass("Bob"),ObjectInClass("Alice"))
    println(persons.sortedWith(ObjectInClass.NameComparator))
}