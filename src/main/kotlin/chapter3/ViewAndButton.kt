package chapter3

open class View{
    open fun click() = println("View clicked")
}

// Button继承View
class Button:View(){
    override fun click() = println("Button clicked")
}

fun main(args: Array<String>){
    val view:View = Button()
    view.click()
    // Button clicked
    // 调用扩展函数
    view.showOff()
    // I`m a view!
}

fun View.showOff() = println("I`m a view!")
fun Button.showOff() = println("I`m a Button!")