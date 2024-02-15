package chapter4.innerClass

class Button : View {

    override fun getCurrentState(): State = ButtonState()

    override fun restoreState(state: State) {
        super.restoreState(state)
    }

    // 与Java中的静态嵌套类类似
    class ButtonState: State{

    }
}