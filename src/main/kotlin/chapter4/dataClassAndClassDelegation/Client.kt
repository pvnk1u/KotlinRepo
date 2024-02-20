package chapter4.dataClassAndClassDelegation

class Client(val name:String,val postalCode: Int) {

    override fun toString() =  "Client(name=$name,postalCode=$postalCode)"

    // Any是java.lang.Object的模拟：Kotlin中所有类的父类。可空类型Any? 意味着other是可以为空的
    override fun equals(other: Any?): Boolean {
        // 检查other是不是一个Client
        if (other == null || other !is Client)
            return false
        return name == other.name && postalCode == other.postalCode
    }

    override fun hashCode(): Int = name.hashCode() * 31 + postalCode

}

fun main(args:Array<String>){
    val client1 = Client("Alice",342562)
    println(client1)
    val client2 = Client("Alice",342562)
    println(client1 == client2)
    // 重写equals方法前是false
    val processed = hashSetOf(Client("Alice",342562))
    println(processed.contains(Client("Alice",342562)))
    // 重写hashCode方法前是false，重写后是true
}