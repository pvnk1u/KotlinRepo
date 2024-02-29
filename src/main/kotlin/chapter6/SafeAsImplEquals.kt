package chapter6

class PersonObj(val firstName:String,val lastName:String){
    override fun equals(other: Any?): Boolean {
        // 尝试使用as?将other转换为PersonObj对象，如果能转换则将转换后的结果赋值给otherPerson
        // 如果不能转换则as?的结果为null，直接执行return false返回
        val otherPerson = other as? PersonObj ?: return false

        // 在安全转换之后，变量otherPerson被智能地转换为Person类型
        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int  =
        firstName.hashCode() * 37 + lastName.hashCode()

}

fun main() {
    val p1 = PersonObj("Dmitry","Jemerov")
    val p2 = PersonObj("Dmitry","Jemerov")
    println(p1 == p2)
    // true
    println(p1.equals(42))   // 运算符会调用equals方法
    // false
}