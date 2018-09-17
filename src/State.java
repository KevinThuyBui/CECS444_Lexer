import Tokens.Token;

/**
 * This Enum lists all the valid states the FSM can enter
 * @author Stefan Brand
 */

public enum State {
    //TODO Move the positon of enums around so we can use State.ordinal() to get token-type number.
    //complex types
    COMMENT(true), ID(true), INT(true), FLOAT(true), STRING(true),

    //Delimiters
    COMMA(true), SEMI(true),
    
    LU(false), LUD(false),

    //Keywords
    KPROG(true), KMAIN(true), KFCN(true), KCLASS(true),
    
    SIGN(false),
    
    KFLOAT(true),
    KINT(true), KSTRING(true), KIF(true), KELSEIF(true), KELSE(true),
    KWHILE(true), KINPUT(true), KPRINT(true), KNEW(true), KRETURN(true),
    KVAR(true),
    
    DIGITS(false),
    
    
    //Unaccepting States
    START(false), MAYBEFLOAT(false), MAYBEPROG(false),
    
    
    ANGLE1(true), ANGLE2(true), BRACE1(true), BRACE2(true),
    BRACKET1(true), BRACKET2(true), PARENS1(true), PARENS2(true),
    
    MAYBEMAIN(false), MAYBEFCN(false),
    
    ASTER(true), CARET(true), COLON(true), DOT(true), EQUAL(true), MINUS(true),
    PLUS(true), SLASH(true),
    
    MAYBECLASS(false), MAYBEFLOATWORD(false),
    
    OPARROW(true), OPEQ(true), OPNE(true), OPLE(true),
    OPGE(true), OPSHL(true), OPSHR(true), ERROR(true), EOF(true);

    private boolean accepting;
    private Token token;

    State(boolean accepting) {
        this.accepting = accepting;
    }

    State(boolean accepting, Token token) {
        this(accepting);
        this.token = token;
    }

    public boolean isAccepting() {
        return accepting;
    }

    public Token getToken() {
        return token;
    }
}
