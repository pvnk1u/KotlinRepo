package chapter4.defaultInterfaceFunc

interface Clickable {
    // 普通的方法声明
    fun click()
    // 带默认实现的方法
    fun showOff() = println("I`m clickable!")
}