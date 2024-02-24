package chapter5

fun printProblemCounts(responses: Collection<String>){
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach{
        if(it.startsWith("4")){
            clientErrors++
        }else if(it.startsWith("5")){
            serverErrors++
        }
    }
    println("$clientErrors client errors,$serverErrors server errors")
}

fun main(args:Array<String>){
    val responses = listOf("200 OK","418 I`m a teapot",
                            "500 Internal Server Error")
    printProblemCounts(responses)
    // 1 client errors,1 server errors
}