package chapter4

class Button : Clickable{
    override fun click() = println("I was clicked")
}


fun main(args: Array<String>){
    Button().click()
    // I was clicked
}