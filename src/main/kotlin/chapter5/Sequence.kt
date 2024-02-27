package chapter5

fun main(args:Array<String>){
    listOf(1,2,3,4).asSequence()
        .map { println("map($it)");it* it }
        .filter { print("filter($it)");it % 2 == 0 }
    /**
     * 不会打印任何结果，因为map和filter变换被延期了，它们只有在获取结果的时候
     * 才会被应用（即末端操作被调用的时候）
     */
    listOf(1,2,3,4).asSequence()
        .map { println("map($it)");it* it }
        .filter { print("filter($it)");it % 2 == 0 }
        .toList()
    /*
    map(1)
    filter(1)map(2)
    filter(4)map(3)
    filter(9)map(4)
    filter(16)
    */
}