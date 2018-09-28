public class CurrentSituation {
    private State currentState;
    private char nextChar;

    public CurrentSituation(State currentState, char nextChar) {
        this.currentState = currentState;
        this.nextChar = nextChar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CurrentSituation) {
            return (((CurrentSituation) obj).currentState.equals(currentState) && ((CurrentSituation) obj).nextChar == nextChar);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (currentState.toString() + nextChar).hashCode();
    }
}
