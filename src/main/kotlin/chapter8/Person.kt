package chapter8

data class Person(val firstName:String,
                val lastName: String,
                val phoneNumber: String?)

class ContactListFilters{
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person) -> Boolean{
        val startsWithPrefix = {p: Person ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber){
            // 返回一个函数类型的变量
            return startsWithPrefix
        }
        // 从这个函数返回一个lambda
        return {startsWithPrefix(it)
                && it.phoneNumber != null}
    }
}

fun main() {
    val contacts = listOf(Person("Dmitry","Jemerov","123-4567"),
                        Person("Svetlana","Isakova",null)
    )
    val contactListFilters = ContactListFilters()
    with(contactListFilters){
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    // 将getPredicate返回的函数作为参数传递给filter函数
    println(contacts.filter(contactListFilters.getPredicate()))
    // [Person(firstName=Dmitry, lastName=Jemerov, phoneNumber=123-4567)]
}
