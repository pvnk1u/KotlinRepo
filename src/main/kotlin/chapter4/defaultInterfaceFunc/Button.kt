package chapter4.defaultInterfaceFunc

class Button : Clickable,Focusable {

    override fun click() = println("I was clicked")

    override fun showOff(){
        // 调用某个指定父类中的某个默认实现方法
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}