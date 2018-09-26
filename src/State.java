import Tokens.Token;

/**
 * This Enum lists all the valid states the FSM can enter
 * @author Stefan Brand
 */

public enum State {
    //complex types
    COMMENT(true, 1), ID(true, 2), INT(true, 3), FLOAT(true, 4), STRING(true, 5),

    //Delimiters
    COMMA(true, 6), SEMI(true, 7),
    
    //Unaccepting States
    START(false, 99), MAYBEFLOAT(false, 99), MAYBEOPNE(false, 99),
    
    ANGLE1(true, 31), ANGLE2(true, 32), BRACE1(true, 33), BRACE2(true, 34),
    BRACKET1(true, 35), BRACKET2(true, 36), PARENS1(true, 37), PARENS2(true, 38),
    
    ASTER(true, 41), CARET(true, 42), COLON(true, 43), DOT(true, 44), EQUAL(true, 45), MINUS(true,46),
    PLUS(true, 47), SLASH(true, 48),
    
    OPARROW(true, 51), OPEQ(true, 52), OPNE(true, 53), OPLE(true, 54),
    OPGE(true, 55), OPSHL(true, 56), OPSHR(true, 57), ERROR(true, 99), EOF(true, 0);

    private boolean accepting;
    private int stateID;

    State(boolean accepting, int stateID) {
        
        this.accepting = accepting;
        this.stateID = stateID;
    }

    public boolean isAccepting() {
        return accepting;
    }
    
    public int getStateID() {return stateID;}

}
