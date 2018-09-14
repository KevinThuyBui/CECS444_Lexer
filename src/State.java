import Tokens.Token;

/**
 * This Enum lists all the valid states the FSM can enter
 * @author Stefan Brand
 */

public enum State {
    //complex types
    COMMENT(true), ID(true), LU(false), LUD(false), INT(true), FLOAT(true),
    STRING(true), SIGN(false), DIGITS(false),

    //Delimiters
    COMMA(true), SEMI(true),

    //Keywords
    KPROG(true), KMAIN(true), KFCN(true), KCLASS(true), KFLOAT(true),
    KINT(true), KSTRING(true), KIF(true), KELSEIF(true), KELSE(true),
    KWHILE(true), KINPUT(true), KPRINT(true), KNEW(true), KRETURN(true),
    KVAR(true), ANGLE1(true), ANGLE2(true), BRACE1(true), BRACE2(true),
    BRACKET1(true), BRACKET2(true), PARENS1(true), PARENS2(true),
    ASTER(true), CARET(true), COLON(true), DOT(true), EQUAL(true), MINUS(true),
    PLUS(true), SLASH(true), OPARROW(true), OPEQ(true), OPNE(true), OPLE(true),
    OPGE(true), OPSHL(true), OPSHR(true), ERROR(true), EOF(true),

    //Unaccepting States
    START(false), MAYBEFLOAT(false);

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
