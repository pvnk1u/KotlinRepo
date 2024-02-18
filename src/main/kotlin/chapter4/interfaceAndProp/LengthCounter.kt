package chapter4.interfaceAndProp

class LengthCounter {

    var counter: Int = 0
        // 不能在类外部修改这个属性
        private set

    fun addWord(word:String){
        counter += word.length
    }
}

fun main(args:Array<String>){
    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi!")
    println(lengthCounter.counter)
    // 3
}