package chapter6

fun printShippingLabel(person: Person){
    val address = person.company?.address
        ?: throw IllegalArgumentException("No Address") // 如果缺少 address就抛出异常
    // 此时address肯定不为空
    with(address){
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}

fun main() {
    val address = Address("Elsestr. 47",80687,"Munich","Germany")
    val jetbrains = Company("Jetbrains",address)
    val person = Person("Dmitry",jetbrains)

    printShippingLabel(person)
    // Elsestr. 47
    // 80687 Munich, Germany

    printShippingLabel(Person("Alexey",null))
    // java.lang.IllegalArgumentException("No Address")
}