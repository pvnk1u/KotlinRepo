package chapter4.innerClass;


public class JavaButton implements View{

    @Override
    public State getCurrentState() {
        return new ButtonState();
    }

    @Override
    public void restoreState(State state) {

    }

    public class ButtonState implements State{

    }
}
