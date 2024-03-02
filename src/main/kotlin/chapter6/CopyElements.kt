package chapter6

fun <T> copyElements(source :Collection<T>,
                     target: MutableCollection<T>){
    // 在source集合中的所有元素中循环
    for (item in source){
        // 向可变的target集合中添加元素
        target.add(item)
    }
}

fun main() {
    val source: Collection<Int> = arrayListOf(3,5,7)
    val target: MutableCollection<Int> = arrayListOf(1)
    copyElements(source,target)
    println(target)
    // [1,3,5,7]
}