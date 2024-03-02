package chapter6

interface Processor<T>{
    fun process(): T
}


class NoResultProcessor: Processor<Unit>{

    // 返回Unit，但可以省略类型说明
    override fun process(){
        // do stuff
        // 这里不需要显式的return
    }
}