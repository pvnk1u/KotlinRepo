package chapter9

// 来源的元素类型应该是目标元素类型的子类型
fun <T:R,R> copyData(source:MutableList<T>,
                     destination: MutableList<R>){
    for (item in source){
        destination.add(item)
    }
}

/**
 * 使用out投影更优雅的实现相应功能
 */
// 这里给source的泛型添加了out关键字，表示只能调用source上那些T用在out位置的方法，不能使用那些source上T用在in位置的方法
fun <T> copyDataWithOut(source: MutableList<out T>,
                 destination: MutableList<T>){
    for (item in source){
        destination.add(item)
    }
}

/**
 * 使用in投影更优雅的实现相应功能
 */
fun <T> copyDataWithIn(source: MutableList<T>,
                       destination: MutableList<in T>){ // 允许目标元素的类型是来源元素类型的超类型
    for (item in source){
        destination.add(item)
    }
}

fun main() {
    val ints = mutableListOf(1,2,3)
    val anyItems = mutableListOf<Any>()
    // 可以调用这个函数，因为Int是Any的子类型
    copyData(ints,anyItems)
    println(anyItems)
    // [1,2,3]
}