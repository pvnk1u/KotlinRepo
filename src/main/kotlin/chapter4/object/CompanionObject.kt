package chapter4.`object`

class CompanionObject {

    companion object{
        fun bar(){
            println("Companion object called")
        }
    }
}

fun main(args: Array<String>){
    CompanionObject.bar()
}