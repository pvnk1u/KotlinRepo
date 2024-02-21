package chapter4.dataClassAndClassDelegation

class CountingSet<T>(val innerSet : MutableCollection<T> = HashSet<T>()
                    ):MutableCollection<T> by innerSet  { // 将MutableCollection的实现委托给innerSet
    var objectAdded = 0;


    // 不使用委托，提高一个不同的实现
    override fun add(element: T): Boolean {
        objectAdded ++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectAdded += elements.size
        return innerSet.addAll(elements)
    }
}

fun main(args: Array<String>){
    val cset = CountingSet<Int>()
    cset.addAll(listOf(1,1,2))
    println("${cset.objectAdded} objects were added,${cset.size} remain")
    // 3 objects were added,2 remain
}