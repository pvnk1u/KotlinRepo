package chapter7

class PersonCompare (
    val firstName:String,val lastName:String
) : Comparable<PersonCompare>{

    override fun compareTo(other: PersonCompare): Int {
        // 按顺序调用给定的方法，并比较它们的值
        return compareValuesBy(this,other,
            PersonCompare::lastName,PersonCompare::firstName)
    }
}

fun main() {
    val p1 = PersonCompare("Alice","Smith")
    val p2 = PersonCompare("Bob","Johnson")
    println(p1 < p2)
    // false
}