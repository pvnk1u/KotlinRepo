package chapter6

class Employee(val name:String,val manager: Employee?)

fun managerName(employee: Employee):String? = employee.manager?.name

fun main() {
    val ceo = Employee("Da Boss",null)
    val  developer = Employee("Bob Smith",ceo)
    println(managerName(developer))
    // Da Boss
    println(managerName(ceo))
    // null
}