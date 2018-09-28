public class Transition {
    private State currentState;
    private char nextChar;

    public Transition(State currentState, char nextChar) {
        this.currentState = currentState;
        this.nextChar = nextChar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transition) {
            return (((Transition) obj).currentState.equals(currentState) && ((Transition) obj).nextChar == nextChar);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (currentState.toString() + nextChar).hashCode();
    }
}
