
import java.util.Arrays;
import java.util.Optional;

/**
 * This Enum lists all the valid states the FSM can enter
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 * @author Richard Salmeron <richard.salmeron@student.csulb.edu>
 */

public enum State {
    //complex types
    COMMENT(true, 1), ID(true, 2), INT(true, 3), FLOAT(true, 4), STRING(true, 5),

    //Delimiters
    COMMA(true, 6), SEMI(true, 7),
    
    //Unaccepting States
    START(false, 99), MAYBEFLOAT(false, 99), MAYBEOPNE(false, 99), ERROR(true, 99),
    
    //Keywords
    KPROG(true,10), KMAIN(true, 11), KFCN(true, 12), KCLASS(true, 13), KFLOAT(true, 15), KINT(true, 16),
    KSTRING(true, 17), KIF(true, 18), KELSEIF(true, 19), KELSE(true, 20), KWHILE(true, 21), KINPUT(true, 22), 
    KPRINT(true, 23), KNEW(true, 24), KRETURN(true, 25), KVAR(true, 26),
    
    //Paired Delimeters
    ANGLE1(true, 31), ANGLE2(true, 32), BRACE1(true, 33), BRACE2(true, 34),
    BRACKET1(true, 35), BRACKET2(true, 36), PARENS1(true, 37), PARENS2(true, 38),
    
    //Other Punctuation tokens
    ASTER(true, 41), CARET(true, 42), COLON(true, 43), DOT(true, 44), EQUAL(true, 45), MINUS(true,46),
    PLUS(true, 47), SLASH(true, 48),
    
    //Multi-Char Operators
    OPARROW(true, 51), OPEQ(true, 52), OPNE(true, 53), OPLE(true, 54),
    OPGE(true, 55), OPSHL(true, 56), OPSHR(true, 57),

    //EOF
    EOF(true, 0);

    /**
     * Is it a accepting state or not
     */
    private boolean accepting;

    /**
     * The ID of the state, according to the A4 language specifications
     */
    private int stateID;

    State(boolean accepting, int stateID) {
        
        this.accepting = accepting;
        this.stateID = stateID;
    }

    public boolean isAccepting() {
        return accepting;
    }
    
    public int getStateID() {return stateID;}
    
    // This method is used to look up state from state id
    public static Optional<State> valueOf(int stateID) {
        return Arrays.stream(values()).filter(State -> State.stateID == stateID).findFirst();
    }

}
