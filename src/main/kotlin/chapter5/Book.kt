package chapter5

class Book(val title:String,val authors: List<String>) {
}

fun main(args:Array<String>){
    val books = listOf(Book("Thursday Next", listOf("Jasper Fforde")),
                        Book("Mort", listOf("Terry Pratchett")),
                        Book("Good omens", listOf("Terry Pratchett",
                                                        "Neil Gaiman")))

    // 统计出所有的作者
    println(books.flatMap { it.authors }.toSet())
    // [Jasper Fforde, Terry Pratchett, Neil Gaiman]

    val strings = listOf("abc","def")
    println(strings.flatMap { it.toList() })
    // [a,b,c,d,e,f]
}