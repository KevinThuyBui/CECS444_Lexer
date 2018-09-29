/**
 * This class represents a transition in a Finite State Machine
 */
public class Transition {
    /**
     * The state the FSM is currently in
     */
    private State currentState;

    /**
     * The char-based event that triggers the transition
     */
    private char nextChar;

    /**
     * Should never be called, hides the empty default constructor
     */
    private Transition() {}

    /**
     * Creates a new FSM transition
     * @param currentState the current (old) state of the FSM
     * @param nextChar the char that triggers the change of state
     */
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
